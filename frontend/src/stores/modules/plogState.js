import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import * as plogService from '../../api/PLogService';

const PLOG_SEND_PENDING = 'PLOG_SEND_PENDING';
const PLOG_SEND_SUCCESS = 'PLOG_SEND_SUCCESS';
const PLOG_SEND_ERROR = 'PLOG_SEND_ERROR';
const SET_PFILE_LOG = 'SET_PFILE_LOG';
const SET_ATTACHMENT_LOG = 'SET_ATTACHMENT_LOG';

export const plogPending = createAction(PLOG_SEND_PENDING);
export const plogSendSuccess = createAction(PLOG_SEND_SUCCESS);
export const plogSenderr = createAction(PLOG_SEND_ERROR);
export const setPfileLog = createAction(SET_PFILE_LOG);
export const setAttachmentLog = createAction(SET_ATTACHMENT_LOG);


const initialState = Map({
    isFetching: false,
    error: false,
    pfileLogs: List(),
    attachmentLogs: List(),
})

export const getPLog = (pdirId) => dispatch => {
    dispatch(plogPending());

    return plogService.getPLog(pdirId)
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
        console.log("-------------------", action.payload);
        return state.set('pfileLogs', action.payload.pfileLogs).set('attachmentLogs',action.payload.attachmentLogs ).set('isFetching', false);
    },
    // 통신 실패
    [PLOG_SEND_ERROR]: (state, action) => {
        return state.set('isFetching', false).set('error', true);
    },
    [SET_PFILE_LOG]: (state, action) => {
        return state.set('pfileLog', action.payload);
    },
    [SET_ATTACHMENT_LOG]: (state, action) => {
        return state.set('attachmentLog', action.payload);
    },

}, initialState);
