import * as projectApi from '../../api/project-api';
import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';

//Actions
export const REQUEST = 'project/REQUEST';   // requestUrl
export const RECEIVE = 'project/RECEIVE';   // projects 

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
// 썽크 미들웨어
export function getAllAsync(requestUrl) {
    const requestUrl = requestUrl;

    const chains = (dispatch) => {
        dispatch(request(requestUrl));
    
        return projectApi.getAll()
          .then(response => {
            const projects = response.data;
            dispatch(receive(projects))
          }, reason => {
            console.info(`프로젝트 Fetch axios 통신 중 에러, 사유 >> ${reason}`);
            dispatch(receive({ data: [{projName:'통신에 장애가 있습니다. 프로젝트 목록을 불러오지 못했습니다.'}] }))
          });
    };
    return chains;
}

export default handleActions({
    /**
     * 
     */
    [REQUEST]: (state, action) => { 
        const projectState = state.get('projectState');
        
        projectState.set('isFetching', true);
        projectState.set('projects', List([]));

        return state.set('projectState', projectState);
    },
    /**
     * 
     */
    [RECEIVE]: (state, action) => {
        const projectState = state.get('projectState');
        const projects = action.payload;

        projectState.set('projects', projectState.projects.concat(List(projects)));
        projectState.set('isFetching', false);
        projectState.set('receivedAt', Date.now());

        return state.set('projectState', projectState);
    },
}, initialState);

