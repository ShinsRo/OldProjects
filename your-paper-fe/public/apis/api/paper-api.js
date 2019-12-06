/* eslint-disable */
import axios from 'axios'

export const SORT_P_ENUM = {
    TITLE       : 'title',
    TIMES_CITED : 'timesCited',
    UPDATED     : 'lastUpdates'
}

export const FIELD = {
    TITLE       : 'paper.title',
    DOI         : 'paper.doi',
    AUTHORS     : 'paper.authorListJson',
    TIMES_CITED : 'paper.timesCited',
    UPDATED     : 'paper.lastUpdates',
    YEAR        : 'paper.sourceInfo.publishedYear',
    AUTHOR_TYPE : 'authorType'
}

export const CRITERIA = {
    LIKE    : 'LIKE',
    IGNORE  : 'IGNORE',
    MATCH   : 'MATCH'
}

export class PaperRecordContainer {
    constructor(username, authorization, SERVER_URL) {
        if (/.*\/$/.test(SERVER_URL))  this.SERVER_URL = SERVER_URL;
        else                           this.SERVER_URL = SERVER_URL + '/';
        this.username = username;
        this.requestHeaders = {
            'Content-Type': 'application/json',
            'Authorization': authorization
            // 'Authorization': `Basic ${btoa(`${username}:${password}`)}`
        };
        this.sortBy = {};

        this.pageState = {
            pageSize        : 10,
            currentPage     : 0,
            recordsFound    : 0,
            firstRecord     : 1,
            endPage         : 0,
        };

        this.records = [];
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

        this.rawResponse = {};
        this.currentParams = {};
    }

    setPageSize(size) {
        this.pageState['pageSize'] = size;
        this.currentParams['count'] = size;
    }

    getPageState() {
        return {...this.pageState};
    }

    setPageState(rawResponse) {
        this.pageState = {
            ...this.pageState,
            firstRecord     : rawResponse.pageable.offset + 1,
            currentPage     : rawResponse.pageable.pageNumber + 1,
            endPage         : rawResponse.totalPages,
            recordsFound    : rawResponse.totalElements,
        }
    }

    listByPage(page, count, sortBy, isAsc, criteria) {
        if (!criteria) criteria = [];

        this.currentParams = {page, count, sortBy, isAsc, criteria};

        const data = {
            username: this.username,
            criteria: criteria,
            sortOption: {
                sortBy: sortBy,
                isAsc: isAsc
            },
            pageOption: {
                page: page - 1,
                count: count
            }
        }

        return axios.post(
                `${this.SERVER_URL}myPaper/listByPage`, data,
                { headers: this.requestHeaders }).then(response => {

            this.rawResponse = response.data;
            this.records = [];

            this.records = this.rawResponse.content.map((raw, idx) => {
                return this.transForm(raw, idx);
            });
            this.setPageState(this.rawResponse);
            return response.data;
        });
    }

    retrive(page) {
        const {count, sortBy, isAsc, criteria} = this.currentParams;
        return this.listByPage(page, count, sortBy, isAsc, criteria);
    }

    search(page, count, sortBy, isAsc, category, query) {
        const data = {
            // username: this.username,
            fieldName: category,
            value: query,
            page: page,
            isAsc: isAsc,
            sortBy: sortBy,
            count: count
        }
        return axios.post(
                `${this.SERVER_URL}paper/search`, data,
                { headers: this.requestHeaders }).then(response => {

                this.records = [];
                this.rawResponse = response.data;

                this.records = this.rawResponse.content.map((raw, idx) => {
                    return this.transForm(raw, idx, true);
                });

                return this.rawResponse;
        });
    }

    addOrUpdateOne(uid, authorType) {
        if ((typeof uid) !== 'string') return alert('uid가 문자열이 아닙니다.');

        const data = {
            username: this.username,
            uids: [
                { uid: uid, authorType: authorType }
            ]
        }
        return axios.post(
                `${this.SERVER_URL}myPaper/addOrUpdate`, data,
                { headers: this.requestHeaders }).then(response => {
             return response.data;
        });
    }

    addOrUpdateAll(uids, authorType) {
        const data = {
            username: this.username,
            uids: []
        }

        for (let idx = 0; idx < uids.length; idx++) {
            data.uids.push({ uid: uids[idx], authorType: authorType[idx] })
        }

        return axios.post(
                `${this.SERVER_URL}myPaper/addOrUpdate`, data,
                { headers: this.requestHeaders }).then(response => {
             return response.data;
        });
    }

    deleteOne(uid) {
        const data = {
            username: this.username,
            uids: [
                { uid: uid }
            ]
        }
        return axios.post(
                `${this.SERVER_URL}myPaper/delete`, data,
                { headers: this.requestHeaders }).then(response => {
             return response.data;
        });
    }

    deleteAll(uids) {
        const data = {
            username: this.username,
            uids: []
        }

        for (let idx = 0; idx < uids.length; idx++) {
            data.uids.push({ uid: uids[idx] })
        }

        return axios.post(
                `${this.SERVER_URL}myPaper/delete`, data,
                { headers: this.requestHeaders }).then(response => {
             return response.data;
        });
    }

    getRawResponse() {
        return this.rawResponse;
    }

    // ex. [1, 2, 3, 6, 10]
    getHeaders(filter) {
        const filtered = [];
        filter.forEach(colNo => {
            filtered.push(this.ColEnum.header[colNo]);
        });
        return filtered;
    }
    getRecords(filter) {
        return this.records.map(record => {
            const filtered = [];
            filter.forEach(colNo => {
                filtered.push(record[colNo]);
            });
            return filtered;
        });
    }
    transForm(raw, idx, isOnlyPaper) {
        if (isOnlyPaper) {
            const transFormedPaper  = this.transFormPaper(raw, idx);
            return transFormedPaper;
        }

        const transFormedPaper  = this.transFormPaper(raw['paper'], idx);
        transFormedPaper[6] = raw.authorType;
        return transFormedPaper;
    }
    transFormPaper(raw, idx) {
        const parsedData    = raw.parsedData;
        const sourceInfo    = raw.sourceInfo;
        const paperUrls     = raw.paperUrls;
        const identifier = raw.identifier;

        const result = [
    //          0    1      2      3     4
    //         '행', 'UID', 'DOI', '제목', '링크',
    //          5        6          7      8
    //         '교신저자', '저자 상태', '저자', '인용수',
    //          9        10      11   12    13
    //         '발행년월', '저녈명', '권', '호', '페이지',
    //          14        15     16    17       18
    //         '월별피인용', '등급', 'IF', '백분율', '파싱 상태',
            idx,    raw.uid,    raw.doi,            raw.title, '',
            '',     '',         raw.authorListJson.join('; '), raw.timesCited,
            '',     '',         '',                 '', '',
            '',     '',         '',                 '', raw.recordState,
            '',     '',         ''
        ];
        if (paperUrls) {
            result[4]   = `${paperUrls.sourceUrl}`;
        }
        if (sourceInfo) {
            result[9]   = `${sourceInfo['publishedYear'] || ''} ${sourceInfo['publishedDate'] || ''}`.trim();
            result[10]  = `${sourceInfo['sourceTitle'] || ''}`;
            result[11]  = `${sourceInfo['volume'] || ''}`;
            result[12]  = `${sourceInfo['issue'] || ''}`;
            result[13]  = `${sourceInfo['pages'] || ''}`;

            result[19]  = `${sourceInfo['issn']    || ''}`;
            result[20]  = `${sourceInfo['isbn']    || ''}`;
            result[21]  = `${sourceInfo['eissn']   || ''}`;
        }
        if (parsedData) {
            const journalImpactJson = parsedData.journalImpactJson;

            result[5]  = parsedData.reprint;
            result[14] = JSON.stringify(parsedData.tcDataJson);
            result[15] = JSON.stringify(parsedData.grades);
            result[16] = journalImpactJson && JSON.stringify(journalImpactJson.impactFactorByYear);
            result[17] = journalImpactJson && JSON.stringify(journalImpactJson.jcrDataByYear);
        }
        return result;
    }
}
