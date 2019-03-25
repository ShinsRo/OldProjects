import axios from 'axios';
import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';

//Actions
export const REQUEST = 'project/REQUEST';   // endPoint
export const RECEIVE = 'project/RECEIVE';   // { endPoint, items }
export const PUSHERR = 'project/PUSHERR';   // { err }

export const request = createAction(REQUEST);
export const receive = createAction(RECEIVE);
export const pusherr = createAction(PUSHERR);

//init state
const initialState = Map({
    isFetching: false,
    projects: List([]),
    lastUpdated: '',
    errObj: Map({
        msg: '',
        err: '',
        type: '',
        isHandled: true
    }),
});

//액션 핸들링
export default handleActions({
    /**
     * 
     */
    [REQUEST]: (state, action) => { 
        
        const _ = END_POINT;
        const endPoint = action.payload.endPoint;

        let projectState = state;
        projectState = projectState.set('isFetching', true);
        
        switch (endPoint) {
            case _.PROJ_LIST:
            case _.PROJ_REGISTER:
            case _.PROJ_CORRECT:
            case _.PROJ_DIRS:
            default:
        }

        return projectState;
    },
    /**
     * 
     */
    [RECEIVE]: (state, action) => {
        const _ = END_POINT;
        const endPoint = action.payload.endPoint;
        const items = action.payload.items;

        let projectState = state;
        
        projectState = projectState.set('receivedAt', Date.now());

        switch (endPoint) {
            case _.PROJ_LIST: 
                projectState = projectState.set('projects', items);
                projectState = projectState.set('lastUpdated', Date.now());
                break;
            case _.PROJ_REGISTER:
            case _.PROJ_CORRECT:
                break;
            case _.PROJ_DIRS:
                const projId = action.payload.projId;
                projectState = projectState.has('dirs') ? projectState : projectState.set('dirs', Map()); 
                projectState = projectState.setIn(['dirs', `${projId}`], items);
                projectState = projectState.set('lastUpdated', Date.now());

                break;
            default:
        }   

        projectState = projectState.set('isFetching', false);

        return projectState;
    },
    [PUSHERR] : (state, action) => {
        const err = action.payload;
        console.info(`프로젝트 Fetch axios 통신 중 에러, 사유 >> ${err}`);

        let projectState = state;
        projectState = projectState.set('errObj', Map({
            msg: '프로젝트 관련 정보를 받는 중 통신 오류가 발생했습니다.',
            err: 'conn',
            type: err,
            isHandled: false
        }));

        return projectState;
    },
}, initialState);

// 썽크 미들웨어 상 GET
export function axiosGetAsync(endPoint, params) {
    const _ = END_POINT;

    let promise;
    const chains = (dispatch) => {
        dispatch(request(endPoint));
        
        switch (endPoint) {
            case _.PROJ_LIST:
                promise = _project_api.get_proj_list(params.userId).then(res => {
                    const items = res.data;
                    dispatch(receive( { endPoint, items } ))
                }, error => { dispatch(pusherr(error)); });
                break;
            case _.PROJ_REGISTER:
                break;
            case _.PROJ_CORRECT:
                break;
            default:
        }
        return promise;
    };
    return chains;
}

// 썽크 미들웨어 상 POST
export function axiosPostAsync(endPoint, params) {
    const _ = END_POINT;

    let promise;
    const chains = (dispatch) => {
        dispatch(request(endPoint));
        
        switch (endPoint) {
            case _.PROJ_DIRS:
                const projId = params.projId;
                promise = _project_api.post_dir_list(projId, params.userId).then(res => {
                    const items = res.data;
                    dispatch(receive({ endPoint, items, projId }) );
                }, error => { dispatch(pusherr(error)); })
                break;
            default:
        }
        return promise;
    };
    return chains;
}

const BASE_URL = 'http://localhost:8080/upmureport/'

export const END_POINT = {
    DEFAULT: '',

    PROJ_LIST: 'api/projects/list',
    PROJ_REGISTER: 'api/projects/register',
    PROJ_CORRECT: 'api/projects/correct',

    PROJ_DIRS: 'api/projects/dirs',
};

const defaultHeaders = {
    'Access-Control-Allow-Origin': 'dev',
    'Access-Control-Allow-Methods': ['POST', 'GET', 'OPTIONS', 'DELETE'],
}

// api
const _project_api = {
    /**
     * 
     */
    get_for: (endPoint) => {
        return axios.get(BASE_URL + endPoint);
    },
    get_proj_list: (userId) => {
        /**
         * 유저에 관련한 모든 프로젝트 목록을 가져옵니다.
         * @param {String} userId 유저 사원번호
         * @return {Promise} 프로미스의 res: { httpStatus, { [{projName: str, projState: str, userName: str, progress: str}] } } 
         */
        console.log(`${BASE_URL}${END_POINT.PROJ_LIST}?userId=${userId}`);
        
        return axios.get(`${BASE_URL}${END_POINT.PROJ_LIST}?userId=${userId}`);
    },

    post_register: (projDto) => {
        return axios.post(`${BASE_URL}${END_POINT.PROJ_REGISTER}`, {
            projDto
        }, {
            headers: {
                ...defaultHeaders,
                'Authorization': projDto.userId,
            }
        });
    },

    post_dir_list: (projId, userId) => {
        /**
         * 유저에 관련한 모든 프로젝트 목록을 가져옵니다.
         * @param projId : {}
         * @return {Promise} 
         */
        return axios.post(`${BASE_URL}${END_POINT.PROJ_DIRS}`, {
            projId: projId,
            userId: userId
        }, {
            headers: {
                ...defaultHeaders,
                'Authorization': userId,
            }
        });
    },
};