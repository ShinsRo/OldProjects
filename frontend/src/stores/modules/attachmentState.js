import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import * as attachmentService from '../../api/attachmentService'

const ATTACH_SEND_PENDING = 'ATTACH_SEND_PENDING';
const ATTACH_SAVE_SUCCESS = 'ATTACH_SAVE_SUCCESS';
const ATTACH_SEND_ERROR = 'ATTACH_SEND_ERROR';
const ATTACH_SEND_SUCCESS = 'ATTACH_SEND_SUCCESS';

export const attPending = createAction(ATTACH_SEND_PENDING);
export const attachmentSaveSuccess = createAction(ATTACH_SAVE_SUCCESS);
export const attSendSuccess = createAction(ATTACH_SEND_SUCCESS);
export const attSenderr = createAction(ATTACH_SEND_ERROR);

const initialState = Map({
    isFetching: false,
    error: false,
    attachments: List(),
})

export const saveAttachment = (attachment) => dispatch => {
    dispatch(attPending());

    return attachmentService.saveAttachment(attachment).then( (response) => {
         dispatch(attachmentSaveSuccess(response.data))
        })
         .catch(error => { dispatch(attSenderr(error)); })
}

export const getAttachment = (dirId) => dispatch => {
    dispatch(attPending());

    return attachmentService.getAttachment(dirId).then( (response) => { 
        
        dispatch(attSendSuccess(response.data))
    }).catch(error => { dispatch(attSenderr(error)); })
}

export default handleActions({
    // 통신 초기화
    [ATTACH_SEND_PENDING]: (state, action) => {        
        return state.set('isFetching', true).set('error', false);
    },
    [ATTACH_SAVE_SUCCESS]: (state, action) => {
        const attachments = state.get('attachments');
        attachments.push(action.payload);

        return state.set('isFetching', false).set('attachments', attachments);
    },
    // 통신 성공
    [ATTACH_SEND_SUCCESS] : (state, action) => {
        console.log("-------------------", action.payload);
        return state.set('attachments', action.payload).set('isFetching', false);
    },
    // 통신 실패
    [ATTACH_SEND_ERROR]: (state, action) => {
        return state.set('isFetching', false).set('error', true);
    }
}, initialState);

