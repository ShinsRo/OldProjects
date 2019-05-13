import { createAction, handleActions } from 'redux-actions';
import { Map } from 'immutable';
import * as plogService from '../../api/PLogService';

const PLOG_SEND_PENDING = 'PLOG_SEND_PENDING';
const PLOG_SEND_SUCCESS = 'PLOG_SEND_SUCCESS';
const PLOG_SEND_ERROR = 'PLOG_SEND_ERROR';
// const SET_PFILE_LOG = 'SET_PFILE_LOG';
// const SET_ATTACHMENT_LOG = 'SET_ATTACHMENT_LOG';


const SET_LOG = 'SET_LOG';
const SET_LOG_VIEW_LEVEL = 'SET_LOG_VIEW_LEVEL';

export const plogPending = createAction(PLOG_SEND_PENDING);
export const plogSendSuccess = createAction(PLOG_SEND_SUCCESS);
export const plogSenderr = createAction(PLOG_SEND_ERROR);
// export const setPfileLog = createAction(SET_PFILE_LOG);
// export const setAttachmentLog = createAction(SET_ATTACHMENT_LOG);

export const setLog = createAction(SET_LOG);
export const setLogViewLevel = createAction(SET_LOG_VIEW_LEVEL);


const initialState = Map({
    isFetching: false,
    error: false,
    // pfileLogs: [],
    // attachmentLogs: [],
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
    // [SET_PFILE_LOG]: (state, action) => {
    //     return state.set('pfileLog', action.payload);
    // },
    // [SET_ATTACHMENT_LOG]: (state, action) => {
    //     return state.set('attachmentLog', action.payload);
    // },
    [SET_LOG]: (state, action) => {
            return state.set('pLog', action.payload);
        },
    [SET_LOG_VIEW_LEVEL]: (state, action) => {
        return state.set('logViewLevel', action.payload);
    },

}, initialState);
