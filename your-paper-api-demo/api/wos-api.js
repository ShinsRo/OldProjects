const SERVER_URL = 'http://127.0.0.1:9400/';

class WokSearchClient {
    constructor() {
        this.clientId       = '';
        this.loading        = false;
        this.timeSpanRegex  = /^\d{4}-\d{2}-\d{2}$/;
        this.records        = [];
        this.pageState = {
            pageSize        : 50,
            currentPage     : 0,
            recordsFound    : 0,
            firstRecord     : 1,
            endPage         : 0,
        };
        this.currentQueryId = 0;
        this.currentSid = 0;
    }

    getPageState() {
        return {...this.pageState};
    }

    setLoading(loading) {
        this.loading = loading;
    }

    initQueryState(sid, queryId, recordsFound) {
        const endPage = Math.ceil(recordsFound / this.pageState.pageSize);
        const firstRecord = 1;
        const currentPage = 1;

        this.records    = Array(recordsFound + 1);
        this.currentSid = sid;
        this.currentQueryId = queryId;
        this.pageState = {...this.pageState, recordsFound, firstRecord, currentPage, endPage};
    }

    setRecords(firstRecord, origin) {
        origin.forEach((record, idx) => {
            this.records[firstRecord + idx] = record;
        });
    }

    updateRecords(firstRecord, linksData) {
        linksData.forEach((record, idx) => {
            this.records[firstRecord + idx]['lamrData'] = record;
        })
    }

    setPageSize(pageSize) {
        this.pageState = {...this.pageState, pageSize};
    }
    
    setPage(page) {
        const currentPage   = page;
        const count         = this.pageState.pageSize;
        const firstRecord   = count * page + 1;
        this.pageState = {...this.pageState, currentPage, firstRecord, count};
    }

    buildUserQuery(category, query, organizations) {
        if (typeof(category) != 'string' || typeof(query) != 'string') {
            console.error("BuildUserQuery의 인자가 유효하지 않습니다.");
            return;
        } else {
            console.log("BuildUserQuery 인자 유효성 확인 완료");
        }
        
        organizations = (organizations && Array.isArray(organizations) && organizations.length != 0) || '';
        const organizationQuery = organizations && `AD=(${organizations.map(item => `(${item})`).join(" OR ")}) AND `;
        const userQuery = `${organizationQuery}${category}=(${query})`;
        console.log(userQuery);
        
        return userQuery;
    }

    search(userQuery, begin, end) {
        this.loading = true;
        if (!begin.match(this.timeSpanRegex) || !end.match(this.timeSpanRegex)) {
            console.error("WokSearchClient search의 인자가 유효하지 않습니다.");
            return;
        } else {
            console.log("WokSearchClient search 인자 유효성 확인 완료");
        };

        this.setPage(1);
        const queryParameters = {
            'databaseId': 'WOS',
            'userQuery' : userQuery,
            'timeSpan'  : {begin, end},
            'queryLanguage': 'en'
        };
        const retrieveParameters = {
            'firstRecord'   : this.pageState.firstRecord, 
            'count'         : this.pageState.pageSize
        };
        const data = {queryParameters, retrieveParameters};

        return axios.post(SERVER_URL + 'WokSearchService/search', data).then((response) => {
            const searchResult = response.data;
            console.log(searchResult);
            
            return searchResult;
        }).then(async (searchResult) => {
            /* 3. 최초 검색 후 처리해야할 것들 */
            console.log('검색 내용에 대한 클라이언트 내부 수행 [단계 2/3] :');

            // 3-1. 특정 쿼리에 대한 최초 검색 시, initQueryState를 통해 반드시 클라이언트를 초기화해야한다.
            const recordsFound  = searchResult['recordsFound'];
            const queryId       = searchResult['queryId'];
            const sid           = searchResult['sid'];

            this.initQueryState(sid, queryId, recordsFound);
            this.setRecords(1, searchResult['records']);

            // 검색 결과에 대해 페이지가 몇개인지 확인할 수 있음
            // console.log(this.getPageState());
            
            // 3-2. 링크를 받아올 논문들의 아이디들 추출
            const uids = this.extractUidsFromRecords(searchResult['records']);

            // 링크 데이터 요청
            await this.getLinksAndTimeCited(uids).then(response => {
                console.log('링크 데이터 요청과 처리 [단계 3/3] :');
                const linksData = response.data;
                this.updateRecords(this.pageState.firstRecord, linksData);
                this.setLoading(false);
            });
            
            return this.records.slice(this.pageState.firstRecord, this.pageState.firstRecord + this.pageState.pageSize);
        });
    }

    retreive(page) {
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

        const data = { 
            'sid': this.currentSid, 
            'queryId': this.currentQueryId, 
            retrieveParameters 
        };

        return axios.post(SERVER_URL + 'WokSearchService/retrieve', data).then(response => {
            const retreiveResult = response.data;
            return retreiveResult;
        }).then(async retreiveResult => {
            const firstRecord = this.pageState.firstRecord;
            this.setRecords(firstRecord, retreiveResult['records']);
            const uids = this.extractUidsFromRecords(retreiveResult['records']);

            // 링크 데이터 요청
            await this.getLinksAndTimeCited(uids).then(response => {
                console.log('링크 데이터 요청과 처리 [단계 3/3] :');
                const linksData = response.data;
                this.updateRecords(firstRecord, linksData);
                this.setLoading(false);
            });
            return this.records.slice(this.pageState.firstRecord, this.pageState.firstRecord + this.pageState.pageSize);
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
        return axios.post(SERVER_URL + 'LamrService/lamrCorCollectionData', data);
    }
}