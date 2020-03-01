import { createAction, handleActions } from 'redux-actions';
import { Map } from 'immutable';
import * as plogService from '../../api/PLogService';

/**
 * @author : 김윤상		2019.05.22. 
 * 
 * @description
 *  - 로그에 대한 데이터, state, 함수 관리
 */
const PLOG_SEND_PENDING = 'PLOG_SEND_PENDING';
const PLOG_SEND_SUCCESS = 'PLOG_SEND_SUCCESS';
const PLOG_SEND_ERROR = 'PLOG_SEND_ERROR';
const SET_LOG = 'SET_LOG';
const SET_LOG_VIEW_LEVEL = 'SET_LOG_VIEW_LEVEL';
const SET_PROJECT = 'SET_PROJECT';

export const plogPending = createAction(PLOG_SEND_PENDING);
export const plogSendSuccess = createAction(PLOG_SEND_SUCCESS);
export const plogSenderr = createAction(PLOG_SEND_ERROR);

export const setLog = createAction(SET_LOG);
export const setLogViewLevel = createAction(SET_LOG_VIEW_LEVEL);
export const setProject = createAction(SET_PROJECT);

const initialState = Map({
    isFetching: false,
    error: false,
    logViewLevel : 'logList',
})

export const getPLog = (pid) => dispatch => {
    dispatch(plogPending());

    return plogService.getPLog(pid)
    .then((response) => {
        dispatch(plogSendSuccess(response.data))
    }).catch(error => {dispatch(plogSenderr(error));});
}

export default handleActions({
    // 통신 초기화
    [PLOG_SEND_PENDING]: (state, action) => {        
        return state.set('isFetching', true).set('error', false);
    },
    // 통신 성공
    [PLOG_SEND_SUCCESS] : (state, action) => {
        return state.set('pLogs', action.payload).set('isFetching', false);
    },
    // 통신 실패
    [PLOG_SEND_ERROR]: (state, action) => {
        return state.set('isFetching', false).set('error', true);
    },
    [SET_LOG]: (state, action) => {
            return state.set('pLog', action.payload);
    },
    [SET_LOG_VIEW_LEVEL]: (state, action) => {
        return state.set('logViewLevel', action.payload);
    },
    [SET_PROJECT] : (state, action) => {
        return state.set('selectedProject', action.payload);
    }

}, initialState);
