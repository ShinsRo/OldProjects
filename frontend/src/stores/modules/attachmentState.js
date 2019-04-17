import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import * as attachmentService from '../../api/attachmentService'

const SEND_PENDING = 'SEND_PENDING';
const SAVE_SUCCESS = 'SAVE_SUCCESS';
const SEND_ERROR = 'SEND_ERROR';

export const pending = createAction(SEND_PENDING);
export const attachmentSaveSuccess = createAction(SAVE_SUCCESS);
export const senderr = createAction(SEND_ERROR);

const initialState = Map({
    isFetching: false,
    error: false,
    attachments: List(),
})

export const saveAttachment = (attachment) => dispatch => {
    dispatch(pending());

    return attachmentService.saveAttachment(attachment).then( (response) => { dispatch(attachmentSaveSuccess(response.data))}).catch(error => { dispatch(senderr(error)); })
}

export default handleActions({
    // 통신 초기화
    [SEND_PENDING]: (state, action) => {        
        return state.set('isFetching', true).set('error', false);
    },
    [SAVE_SUCCESS]: (state, action) => {
        const attachmentList = state.get('attachments');
        attachments.push(action.payload);

        return state.set('isFetching', false).set('attachments', pfileList);
    },
    // 통신 성공
    [SEND_SUCCESS] : (state, action) => {
        return state.set('attachments', action.payload).set('isFetching', false);
    },
    // 통신 실패
    [SEND_ERROR]: (state, action) => {
        return state.set('isFetching', false).set('error', true);
    }
}, initialState);

