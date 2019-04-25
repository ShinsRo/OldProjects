import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import * as pfileService from '../../api/PfileService'

// 액션 타입
const CHANGE_TITLE_INPUT = 'CHANGE_TITLE_INPUT';
const CHANGE_CONTENT_INPUT = 'CHANGE_CONTENT_INPUT';
const SEND_PENDING = 'SEND_PENDING';
const SEND_SUCCESS = 'SEND_SUCCESS';
const SAVE_SUCCESS = 'SAVE_SUCCESS';
const SEND_ERROR = 'SEND_ERROR';
const SET_PFILE = 'SET_PFILE';

// 액션 생성 함수
export const changeTitleInput = createAction(CHANGE_TITLE_INPUT);
export const changeContentInput = createAction(CHANGE_CONTENT_INPUT);
export const pending = createAction(SEND_PENDING);
export const senderr = createAction(SEND_ERROR);
export const pfileSaveSuccess = createAction(SAVE_SUCCESS);
export const sendSuccess = createAction(SEND_SUCCESS);
export const setPfile = createAction(SET_PFILE);

const initialState = Map({
    titleInput: '',
    contentInput: '',
    isFetching: false,
    error: false,
    pfiles: List(),
})

export const savePfile = (pfile) => dispatch => {
    dispatch(pending());   

    return pfileService.savePfile(pfile).then( (response) => { 
        dispatch(pfileSaveSuccess(response.data))
    }).catch(error => { dispatch(senderr(error)); })
}

export const updatePfile = (pfile) => dispatch => {
    dispatch(pending());    

    return pfileService.updatePfile(pfile).then((response) => { dispatch(sendSuccess(response.data))}).catch(error => { dispatch(senderr(error)); })
}

export const getPfile = (dirId) => dispatch => {
    dispatch(pending());

    return pfileService.getPfile(dirId).then((response) => 
    { 
        dispatch(sendSuccess(response.data))

    }).catch(error => { dispatch(senderr(error)); })
}

export const deletePfile = (pfileId) => dispatch => {
    dispatch(pending());
    
    return pfileService.deletePfile(pfileId).then((response) => { dispatch(sendSuccess(response.data))}).catch(error => { dispatch(senderr(error)); })
}

export default handleActions({
    // 타이틀 입력
    [CHANGE_TITLE_INPUT]: (state, action) => {
        return state.set('titleInput', action.payload)
    },
    // 내용 입력
    [CHANGE_CONTENT_INPUT]: (state, action) => {
        return state.set('contentInput', action.payload)
    },
    [SET_PFILE]: (state, action) => {
        console.log(action.payload);
        return state.set('pfile', action.payload)
    },
    // 통신 초기화
    [SEND_PENDING]: (state, action) => {        
        return state.set('isFetching', true).set('error', false);
    },
    // 업무 저장 전송 성공 
    [SAVE_SUCCESS]: (state, action) => {
        const pfileList = state.get('pfiles');
        pfileList.push(action.payload);

        return state.set('isFetching', false).set('pfile', action.payload).set('pfiles', pfileList);
    },
    // 통신 성공
    [SEND_SUCCESS] : (state, action) => {
        const pfiles = action.payload;
        console.log("-------------------pfiles ", pfiles);

        return state.set('pfiles', pfiles).set('isFetching', false);
    },
    // 통신 실패
    [SEND_ERROR]: (state, action) => {
        return state.set('isFetching', false).set('error', true);
    }
}, initialState);