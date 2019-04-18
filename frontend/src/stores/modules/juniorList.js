import { handleActions } from 'redux-actions';
import { Map, List } from 'immutable';
import axios from 'axios';
import store from '../../stores'

function getjuniorAPI(loginUser) {

    const {userState} = store.getState();
    const {userInfo} = userState
    const {token} = userInfo
    const config = {
        headers: 
        {
                  'X-Auth-Token': token
        }
    }
    console.log("토큰",token)

    return axios.post('http://localhost:8080/upmureport/api/users/userlist', loginUser,config);
}

const GET_JUNIOR_PENDING = 'GET_JUNIOR_PENDING';
const GET_JUNIOR_SUCCESS = 'GET_JUNIOR_SUCCESS';
const GET_JUNIOR_FAILURE = 'GET_JUNIOR_FAILURE';

export const getJuniors = (loginUser) => dispatch => {
    // 먼저, 요청이 시작했다는것을 알립니다
    dispatch({type: GET_JUNIOR_PENDING});

    // 요청을 시작합니다
    // 여기서 만든 promise 를 return 해줘야, 나중에 컴포넌트에서 호출 할 때 getJUNIOR().then(...) 을 할 수 있습니다
    return getjuniorAPI(loginUser).then(
        (response) => {
            // console.log("요청성공", response)
            // 요청이 성공했을경우, 서버 응답내용을 payload 로 설정하여 GET_JUNIOR_SUCCESS 액션을 디스패치합니다.
            dispatch({
                type: GET_JUNIOR_SUCCESS,
                payload: response.data
            })
        }
    ).catch(error => {
        // console.log("요청실패")
        // 에러가 발생했을 경우, 에로 내용을 payload 로 설정하여 GET_JUNIOR_FAILURE 액션을 디스패치합니다.
        dispatch({
            type: GET_JUNIOR_FAILURE,
            payload: error
        });
    })

}

const initialState = Map({
    pending: false,
    error: false,
    users: List([
        {
            userId: '1',
            userName: '1',
            userPass:'',
            dept:'',
            posi:'',
            deleteFlag:'',
        },
    ])
})

export default handleActions({
    [GET_JUNIOR_PENDING]: (state, action) => {
        state = state.set('pending', true);
        state = state.set('error', false);
        /*
        const str = 'hihi';
        const condition = 'aa';
        const isthere = condition && str;
        console.log(isthere);
        */

        return state;
    },
    [GET_JUNIOR_SUCCESS]: (state, action) => {
        //const { userId,userName,userPass,dept,posi,deleteFlag } = action.payload.users;
        
        return state.set('users', action.payload);
    },
    [GET_JUNIOR_FAILURE]: (state, action) => {
        state = state.set('pending', false);
        state = state.set('error', true);

        return state;
    }
}, initialState);