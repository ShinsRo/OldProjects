import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import * as attachmentService from '../../api/attachmentService'
import { BASE_URL } from '../../supports/API_CONSTANT';
import axios from 'axios';

/**
 * @author : 김윤상		2019.05.22. 
 * 
 * @description
 *  - 첨부 파일에 대한 데이터, state, 함수 관리
 */

const ATTACH_SEND_PENDING = 'ATTACH_SEND_PENDING';
const ATTACH_SAVE_SUCCESS = 'ATTACH_SAVE_SUCCESS';
const ATTACH_SEND_ERROR = 'ATTACH_SEND_ERROR';
const ATTACH_SEND_SUCCESS = 'ATTACH_SEND_SUCCESS';
const SET_ATTACHMENT = 'SET_ATTACHMENT';
const DOWNLOAD_ATTACHMENT = 'DOWNLOAD_ATTACHMENT';
const ADD_GROUP = 'ADD_GROUP';
const DELETE_GROUP = 'DELETE_GROUP';
const CLEAR_GROUP = 'CLEAR_GROUP';
const DOWNLOAD_GROUP = 'DOWNLOAD_GROUP';
const DOWNLOAD_GROUP_SETTING = 'DOWNLOAD_GROUP_SETTING';
const SEND_ATT_SUCCESS = 'SEND_ATT_SUCCESS';

export const attPending = createAction(ATTACH_SEND_PENDING);
export const attachmentSaveSuccess = createAction(ATTACH_SAVE_SUCCESS);
export const attSendSuccess = createAction(ATTACH_SEND_SUCCESS);
export const attSenderr = createAction(ATTACH_SEND_ERROR);
export const setAttachment = createAction(SET_ATTACHMENT);
export const downAttachment = createAction(DOWNLOAD_ATTACHMENT);
export const addGroup = createAction(ADD_GROUP);
export const deleteGroup = createAction(DELETE_GROUP);
export const clearGroup = createAction(CLEAR_GROUP);
export const downloadGroupSetting = createAction(DOWNLOAD_GROUP_SETTING);
export const downloadGroup = createAction(DOWNLOAD_GROUP);
export const sendSuccess = createAction(SEND_ATT_SUCCESS);

const initialState = Map({
    isFetching: false,
    error: false,
    attachments: List(),
    attachmentGroup: [],
    isDownload: false,
})

export const saveAttachment = (attachment, config) => dispatch => {
    dispatch(attPending());

    return attachmentService.saveAttachment(attachment, config).then( (response) => {
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

export const downloadAttachment = (attachmentId) => dispatch => {
    dispatch(attPending());
    const header = { 'responseType': 'blob' }
    return attachmentService.downAttachment(attachmentId, header).then( (response) => { 

        dispatch(downAttachment(response.data));

    }).catch(error => { dispatch(attSenderr(error)); })
}

export const deleteAttachment = (attachmentId) => dispatch => {
    dispatch(attPending());
    
    return attachmentService.deleteAttachment(attachmentId).then( (response) => {

        dispatch(attSendSuccess(response.data))
    }).catch(error => { dispatch(attSenderr(error)); })
}

export const downloadAttachmentGroup = (attachmentIds) => dispatch => {
    dispatch(attPending());
    
    const header = { 'responseType': 'blob' }

    return axios.post(`${BASE_URL}/attachment/download/group`, attachmentIds , header).then(response => {
        dispatch(downloadGroup(response.data));
      }).catch(error => { dispatch(attSenderr(error)); })
}

export const moveAttachment = (attachmentId, pdirId) => dispatch =>{
    dispatch(attPending());

    return attachmentService.moveAttachment(attachmentId, pdirId).then((response) => {
        dispatch(attSendSuccess(response.data))
    }).catch(error => { dispatch(attSenderr(error)); })
}

export const copyAttachment = (attachmentId, pdirId) => dispatch =>{
    dispatch(attPending());

    return attachmentService.copyAttachment(attachmentId, pdirId).then((response) => {
        dispatch(sendSuccess(response.data))
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

        return state.set('isFetching', false).set('attachments', attachments).set('attachment', action.payload);
    },
    // 첨부파일 가져오기 성공
    [ATTACH_SEND_SUCCESS] : (state, action) => {
        
        return state.set('attachments', action.payload).set('isFetching', false);
    },
    //  통신 성공
    [SEND_ATT_SUCCESS] : (state, action) => {        
        return state.set('isFetching', false);
    },
    // 통신 실패
    [ATTACH_SEND_ERROR]: (state, action) => {
        return state.set('isFetching', false).set('error', true);
    },
    [SET_ATTACHMENT]: (state, action) => {
        return state.set('attachment', action.payload);
    },
    [DOWNLOAD_ATTACHMENT] : (state, action) => {

        var blob=new Blob([action.payload], {type: "application/octet-stream"});
        var link=document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download=state.get('attachment').attachmentName;
        link.click();

        return state;
    },      
    [ADD_GROUP] :  (state, action) => {
        
        return state.set('attachmentGroup', state.get('attachmentGroup').concat(action.payload));
    },   
    [DELETE_GROUP] :  (state, action) => {
        return state.set('attachmentGroup', state.get('attachmentGroup').filter(attachment => attachment.attachmentId !== action.payload.attachmentId));
    },
    [CLEAR_GROUP] :  (state, action) => {
        return state.set('attachmentGroup', []);
    },   
    [DOWNLOAD_GROUP] : (state, action) => {
        
        var blob=new Blob([action.payload], {type: "application/octet-stream"});
        var link=document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download="download.zip";
        link.click();

    },
}, initialState);
