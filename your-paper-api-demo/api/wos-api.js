class WokSearchClient {
    constructor(SERVER_URL) {
        this.SERVER_URL     = SERVER_URL;
        this.clientId       = '';
        this.loading        = false;
        this.step           = 0;
        this.totalStep      = 1;
        this.timeSpanRegex  = /^\d{4}-\d{2}-\d{2}$/;
        this.records        = [];
        this.pageState = {
            pageSize        : 10,
            currentPage     : 0,
            recordsFound    : 0,
            firstRecord     : 1,
            pageList        : [],
            endPage         : 0,
        };
        this.sortField = [];
        this.currentQueryId = 0;
        this.currentSid = 0;
    }

    getPageState() {
        return {...this.pageState};
    }
    
    getPageList() {
        return this.pageState.pageList.slice(0);
    }

    setLoading(loading) {
        this.loading = loading;
    }

    initQueryState(sid, queryId, recordsFound) {
        const endPage       = Math.ceil(recordsFound / this.pageState.pageSize);
        const pageList      = [];
        const firstRecord   = 1;
        const currentPage   = 1;
        
        let i = 1;
        while (i <= endPage) { pageList.push(i++); }

        this.records    = [];
        this.currentSid = sid;
        this.currentQueryId = queryId;
        this.sortField  = [];
        this.pageState = {...this.pageState, recordsFound, firstRecord, currentPage, pageList, endPage};
    }

    setRecords(origin) {
        origin.forEach((record, idx) => {
            this.records[idx] = record;
        });
    }

    updateRecords(linksData) {
        linksData.forEach((record, idx) => {
            this.records[idx]['lamrData'] = record;
        })
    }

    setPageSize(pageSize) {
        this.pageState = {...this.pageState, pageSize};
    }
    
    setPage(page) {
        const currentPage   = page;
        const count         = this.pageState.pageSize;
        const firstRecord   = count * (page - 1) + 1;
        this.pageState = {...this.pageState, currentPage, firstRecord, count};
    }

    buildUserQuery(category, query, organizations) {
        if (typeof(category) != 'string' || typeof(query) != 'string') {
            console.error("BuildUserQuery의 인자가 유효하지 않습니다.");
            return;
        } else {
            console.log("BuildUserQuery 인자 유효성 확인 완료");
        }
        
        organizations = (Array.isArray(organizations) && organizations.length != 0 && organizations) || '';

        const organizationQuery = organizations && `AD=(${organizations.map(item => `(${item})`).join(" OR ")}) AND `;
        const userQuery = `${organizationQuery}${category}=(${query})`;
        console.log(userQuery);
        
        return userQuery;
    }
    setSortField(sortBy, isAsc) {
        if (sortBy === 'TC') isAsc = false;
        if (!sortBy) {
            this.sortField = [];
            return;
        }
        const sortField = [{ 'name': sortBy, 'sort': (isAsc)? 'A':'D' }];
        this.sortField = sortField;
    }

    search(userQuery, begin, end, symbolicTimeSpan, noLAMR) {
        this.step       = 0;
        this.loading    = true;
        this.noLAMR   = noLAMR;

        if (!symbolicTimeSpan && !begin.match(this.timeSpanRegex) || !end.match(this.timeSpanRegex)) {
            console.error("WokSearchClient search의 인자가 유효하지 않습니다.");
            return;
        } else {
            console.log("WokSearchClient search 인자 유효성 확인 완료");
        };

        this.setPage(1);
        const queryParameters = {
            'databaseId': 'WOS',
            'userQuery' : userQuery,
            'queryLanguage': 'en'
        };

        if (symbolicTimeSpan) {
            queryParameters['symbolicTimeSpan'] = symbolicTimeSpan;
        } else {
            queryParameters['timeSpan'] = { 'begin': begin, 'end': end };
        }
        
        const retrieveParameters = {
            'firstRecord'   : this.pageState.firstRecord, 
            'count'         : this.pageState.pageSize
        };
        if (this.sortField) retrieveParameters['sortField'] = this.sortField;

        const data = {queryParameters, retrieveParameters};
        console.log(data);
        
        return axios.post(this.SERVER_URL + 'WokSearchService/search', data).then((response) => {
            this.step       = 1;
            const searchResult = response.data;
            console.log(searchResult);
            
            return searchResult;
        }).then(async (searchResult) => {
            this.step       = 2;
            /* 3. 최초 검색 후 처리해야할 것들 */
            console.log('검색 내용에 대한 클라이언트 내부 수행 [단계 2/3] :');

            // 3-1. 특정 쿼리에 대한 최초 검색 시, initQueryState를 통해 반드시 클라이언트를 초기화해야한다.
            const recordsFound  = searchResult['recordsFound'];
            const queryId       = searchResult['queryId'];
            const sid           = searchResult['sid'];

            this.initQueryState(sid, queryId, recordsFound);
            this.setRecords(searchResult['records']);

            // 검색 결과에 대해 페이지가 몇개인지 확인할 수 있음
            // console.log(this.getPageState());
            
            // 3-2. 링크를 받아올 논문들의 아이디들 추출

            // 링크 데이터 요청
            if (noLAMR) {
                this.step = 3;
                const uids = this.extractUidsFromRecords(searchResult['records']);

                await this.getLinksAndTimeCited(uids).then(response => {
                    this.step = 4;
                    console.log('링크 데이터 요청과 처리 [단계 3/3] :');
                    const linksData = response.data;
                    this.updateRecords(linksData);
                });
            }
            this.step = 5;
            this.setLoading(false);
            return searchResult;
        });
    }

    retreive(page) {
        this.step = 0;
        this.setLoading(true);
        if (isNaN(page)) {
            console.error("WokSearchClient retrieve 인자가 유효하지 않습니다.");
            return;
        } else {
            console.log("WokSearchClient retrieve 인자 유효성 확인 완료");
        };
        this.setPage(page);
        const retrieveParameters = {
            'queryId'       : this.currentQueryId,
            'firstRecord'   : this.pageState.firstRecord, 
            'count'         : this.pageState.pageSize
        };
        if (this.sortField) retrieveParameters['sortField'] = this.sortField;

        const data = { 
            'sid': this.currentSid, 
            'queryId': this.currentQueryId, 
            retrieveParameters 
        };

        return axios.post(this.SERVER_URL + 'WokSearchService/retrieve', data).then(response => {
            this.step = 1;
            const retreiveResult = response.data;
            return retreiveResult;
        }).then(async retreiveResult => {
            this.step = 2;
            this.setRecords(retreiveResult['records']);
            
            // 링크 데이터 요청
            if (this.noLAMR) {
                this.step = 3;
                const uids = this.extractUidsFromRecords(retreiveResult['records']);

                await this.getLinksAndTimeCited(uids).then(response => {
                    this.step = 4;
                    console.log('링크 데이터 요청과 처리 [단계 3/3] :');
                    const linksData = response.data;
                    this.updateRecords(linksData);
                    this.setLoading(false);
                });
            }
            this.step = 5;
            return retreiveResult;
        });
    }

    extractUidsFromRecords(records) {
        return records.map(item => {
            return item['uid'];
        })
    }

    getLinksAndTimeCited(uids) {
        this.loading = true;
        if (!Array.isArray(uids)) {
            console.error("getLinksAndTimeCited 인자가 유효하지 않습니다.");
            return;
        } else {
            console.log("getLinksAndTimeCited 인자 유효성 확인 완료");
        };
        const data = {uids};
        return axios.post(this.SERVER_URL + 'LamrService/lamrCorCollectionData', data);
    }
}