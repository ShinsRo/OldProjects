import axios from 'axios';
import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';

//Actions
export const REQUEST = 'project/REQUEST';   // ndPoint
export const RECEIVE = 'project/RECEIVE';   // { endPoint, items }

export const request = createAction(REQUEST);
export const receive = createAction(RECEIVE);

//init state
const initialState = Map({
    projectState: Map({
        isFetching: false,
        projects: List([]),
        lastUpdated: '',
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

        let projectState = state.get('projectState');

        projectState = projectState.set('isFetching', true);
        
        switch (endPoint) {
            case _.PROJ_LIST:
                projectState = projectState.set('projects', List([]));
                break;
            case _.PROJ_CREATE:
            case _.PROJ_CORRECT:
                break;
            default:
        }

        return state.set('projectState', projectState);
    },
    /**
     * 
     */
    [RECEIVE]: (state, action) => {
        
        const _ = END_POINT;
        const endPoint = action.payload.endPoint;
        const items = action.payload.items;

        let projectState = state.get('projectState');

        switch (endPoint) {
            case _.PROJ_LIST: 
                projectState = projectState.set('projects', items);
                break;
            case _.PROJ_CREATE:
            case _.PROJ_CORRECT:
                break;
            default:
        }   

        projectState = projectState.set('isFetching', false);
        projectState = projectState.set('receivedAt', Date.now());

        return state.set('projectState', projectState);
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
                }, error => {
                    console.info(`프로젝트 Fetch axios 통신 중 에러, 사유 >> ${error}`);
                    dispatch(receive({ endPoint, items: ['통신이 원활하지 않습니다.'] }));
                });
                break;
            case _.PROJ_CREATE:
                promise = '';
                break;
            case _.PROJ_CORRECT:
                promise = '';
                break;
            default:
        }
        return promise;
    };
    return chains;
}

// 썽크 미들웨어 상 POST
export function axiosPostAsync(endPoint) {
    
    const chains = (dispatch) => {
        dispatch(request(endPoint));
    
        return _project_api.get_for('api/projects/getAll')
          .then(response => {
            const projects = response.data;
            dispatch(receive(projects))

          }, reason => {
            console.info(`프로젝트 Fetch axios 통신 중 에러, 사유 >> ${reason}`);
            dispatch(receive([{projName:'통신에 장애가 있습니다. 프로젝트 목록을 불러오지 못했습니다.'}]));
          });
    };
    return chains;
}

const BASE_URL = 'http://localhost:8080/upmureport/'

export const END_POINT = {
    DEFAULT: '',

    PROJ_LIST: 'api/projects/list',
    PROJ_CREATE: 'api/projects/create',
    PROJ_CORRECT: 'api/projects/correct',
    
    PROJ_DIRS: '',
};

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
         * 모든 프로젝트 목록을 가져옵니다.
         * @param {String} userId 유저 사원번호
         * @return {Promise} 프로미스의 res: { httpStatus, { [{projName: str, projState: str, userName: str, progress: str}] } } 
         */
        console.log(`${BASE_URL}${END_POINT.PROJ_LIST}?userId=${userId}`);
        
        return axios.get(`${BASE_URL}${END_POINT.PROJ_LIST}?userId=${userId}`);
    },
    post_proj: (proj) => {
        /**
         * 유저에 관련한 모든 프로젝트 목록을 가져옵니다.
         * @param proj : {}
         * @return {Promise} 
         */
        return axios.get(`${BASE_URL}${END_POINT.PROJ_LIST}?userId=${proj}`);
    },
};