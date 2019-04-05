import { createAction, handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import * as upmuService from '../../api/upmuService'

// 액션 타입
const CHANGE_TITLE_INPUT = 'CHANGE_TITLE_INPUT';
const CHANGE_CONTENT_INPUT = 'CHANGE_CONTENT_INPUT';
const INSERT_UPMU = 'INSERT_UPMU';
const SEND_POST_PENDING = 'SEND_POST_PENDING';
//const SEND_POST_SUCCESS = 'SEND_POST_SUCCESS';
const SAVE_UPMU_SUCCESS = 'SAVE_UPMU_SUCCESS';
const GET_UPMU_SECCESS = 'GET_UPMU_SECCESS';
const SEND_POST_FAILURE = 'SEND_POST_FAILURE';

const initialState = Map({
    titleInput: '',
    contentInput: '',
    isFetching: false,
    error: false,
    upmus: List(),
})

// 액션 생성 함수
export const changeTitleInput = createAction(CHANGE_TITLE_INPUT);
export const changeContentInput = createAction(CHANGE_CONTENT_INPUT);
export const insertUpmu = createAction(INSERT_UPMU);

export const saveUpmu = (upmu) => dispatch => {
    dispatch({ type: SEND_POST_PENDING });

    //console.log(this.initialState.titleInput);
    return upmuService.saveUpmu(upmu).then(
        (response) => {
            // 요청이 성공했을경우, 서버 응답내용을 payload 로 설정하여 SEND_POST_SUCCESS 액션을 디스패치합니다.
            dispatch({
                type: SAVE_UPMU_SUCCESS,
                payload: response.data                
            })
        }
    ).catch(error => {
        // 에러가 발생했을 경우, 에로 내용을 payload 로 설정하여 SEND_POST_FAILURE 액션을 디스패치합니다.
        dispatch({
            type: SEND_POST_FAILURE,
            payload: error
        });
    })
}

export const updateUpmu = (upmu) => dispatch => {
    dispatch({ type: SEND_POST_PENDING });

    //console.log(this.initialState.titleInput);
    return upmuService.updateUpmu(upmu).then(
        (response) => {
            // 요청이 성공했을경우, 서버 응답내용을 payload 로 설정하여 SEND_POST_SUCCESS 액션을 디스패치합니다.
            dispatch({
                type: GET_UPMU_SECCESS,
                payload: response.data                
            })
        }
    ).catch(error => {
        // 에러가 발생했을 경우, 에로 내용을 payload 로 설정하여 SEND_POST_FAILURE 액션을 디스패치합니다.
        dispatch({
            type: SEND_POST_FAILURE,
            payload: error
        });
    })
}

export const getUpmu = (dirId) => dispatch => {
    dispatch({ type: SEND_POST_PENDING });

    return upmuService.getUpmu(dirId).then((response) => {
        // 요청이 성공했을경우, 서버 응답내용을 payload 로 설정하여 SEND_POST_SUCCESS 액션을 디스패치합니다.
        dispatch({
            type: GET_UPMU_SECCESS,
            payload: response.data
        })
    }
    
    ).catch(error => {
        // 에러가 발생했을 경우, 에로 내용을 payload 로 설정하여 SEND_POST_FAILURE 액션을 디스패치합니다.
        dispatch({
            type: SEND_POST_FAILURE,
            payload: error
        });
    })
}

export const deleteUpmu = (upmuId) => dispatch => {
    dispatch({ type: SEND_POST_PENDING });

    //console.log(this.initialState.titleInput);
    return upmuService.deleteUpmu(upmuId).then(
        (response) => {
            // 요청이 성공했을경우, 서버 응답내용을 payload 로 설정하여 SEND_POST_SUCCESS 액션을 디스패치합니다.
            dispatch({
                type: GET_UPMU_SECCESS,
                payload: response.data                
            })
        }
    ).catch(error => {
        // 에러가 발생했을 경우, 에로 내용을 payload 로 설정하여 SEND_POST_FAILURE 액션을 디스패치합니다.
        dispatch({
            type: SEND_POST_FAILURE,
            payload: error
        });
    })
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
    // 전송 초기화
    [SEND_POST_PENDING]: (state, action) => {        
        return state.set('isFetching', true).set('error', false);
    },
    // 업무 저장 전송 성공 
    [SAVE_UPMU_SUCCESS]: (state, action) => {
        console.log(action.payload);
        const upmulist = state.get('upmus');
        upmulist.push(action.payload);
        //return state.set('isFetching', false).set('upmu', action.payload);
        return state.set('isFetching', false).set('upmu', action.payload).set('upmus', upmulist);
        //return state.set('isFetching', false).set('upmu.name', action.payload.titleInput).set('upmu.contents', action.payload.contents);
    },
    // 겟 업무 성공
    [GET_UPMU_SECCESS] : (state, action) => {
        console.log('get--',action.payload)
        return state.set('upmus', action.payload).set('isFetching', false);
    },
    // 전송 실패
    [SEND_POST_FAILURE]: (state, action) => {

        return state.set('isFetching', false).set('error', true);
    }
}, initialState);