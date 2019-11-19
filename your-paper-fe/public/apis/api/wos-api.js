/* eslint-disable */
import axios from 'axios'

export class WokSearchClient {
    constructor(SERVER_URL) {
        this.SERVER_URL     = SERVER_URL;
        this.clientId       = '';
        this.loading        = false;
        this.step           = 0;
        this.totalStep      = 1;
        this.timeSpanRegex  = /^\d{4}-\d{2}-\d{2}$/;
        this.records        = [];
        this.rawRecords     = [];
        this.ColEnum = {
            header: [
        //      0    1      2      3     4
                '행', 'UID', 'DOI', '제목', '링크',
        //      5        6          7      8
                '교신저자', '저자 상태', '저자', '인용수',
        //      9        10      11   12    13
                '발행년월', '저녈명', '권', '호', '페이지',
        //      14        15     16    17       18
                '월별피인용', '등급', 'IF', '백분율', '파싱 상태',
        //      19      20      21
                'issn', 'isbn', 'eissn'
            ]
        };
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
    // ex. [1, 2, 3, 6, 10]

    getHeaders(filter) {
        const filterSet = new Set(filter);
        return this.ColEnum.header.filter((e, idx) => {
            if (filterSet.has(idx)) return true;
            else                    return false;
        });
    }

    getRecords(filter) {
        const filterSet = new Set(filter);
        return this.records.map(record => {
            return record.filter((e, idx) => {
                if (filterSet.has(idx)) return true;
                else                    return false;
            });
        });
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
        this.rawRecords = [];
        this.currentSid = sid;
        this.currentQueryId = queryId;
        this.sortField  = [];
        this.pageState = {...this.pageState, recordsFound, firstRecord, currentPage, pageList, endPage};
    }

    transform() {
        this.records = this.rawRecords.map((raw, idx) => {
            return this.transformRecord(raw, idx);
        });
    }

    transformRecord(raw, idx) {
    [
    //      0    1      2      3     4
            '행', 'UID', 'DOI', '제목', '링크',
    //      5        6          7      8
            '교신저자', '저자 상태', '저자', '인용수',
    //      9        10      11   12    13
            '발행년월', '저녈명', '권', '호', '페이지',
    //      14        15     16    17       18
            '월별피인용', '등급', 'IF', '백분율', '파싱 상태',
    //      19      20      21
            'issn', 'isbn', 'eissn'
    ]
        const rowNo = this.pageState.firstRecord + idx;
        const source = raw.source;
        const lamrData = raw.lamrData;
        const identifier = raw.identifier;

        const result = [
            rowNo,  raw.uid,    raw.doi,    raw.title,  '',
            '',     '',         raw.authors, '',
            '',     '',         '',         '',         '',
            '',     '',         '',         '',         '',
            '',     '',         ''
        ];


        if (lamrData) {
            result[4]   = `${lamrData.sourceURL}`;
            result[8]   = `${lamrData.timesCited}`;
        }
        if (source) {
            result[9]   = `${source['publishedYear'] || 0} ${source['publishedDate'] || 0}`.trim();
            result[10]  = `${source['sourceTitle'] || ''}`;
            result[11]  = `${source['volume']   || ''}`;
            result[12]  = `${source['issue']    || ''}`;
            result[13]  = `${source['pages']    || ''}`;
        }
        if (identifier) {
            result[19]  = `${identifier['issn']    || ''}`;
            result[20]  = `${identifier['isbn']    || ''}`;
            result[21]  = `${identifier['eissn']   || ''}`;
        }
        return result;
    }

    setRecords(origin) {
        this.rawRecords = [];
        origin.forEach((record, idx) => {
            this.rawRecords[idx] = record;
        });
    }

    updateRecords(linksData) {
        linksData.forEach((record, idx) => {
            this.rawRecords[idx]['lamrData'] = record;
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
        
        return axios.post(this.SERVER_URL + 'WokSearchService/search', data).then((response) => {
            this.step       = 1;
            const searchResult = response.data;
            
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
                    this.transform();
                });
            } else {
                this.transform();
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
                    this.transform();
                });
            } else {
                this.transform();
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