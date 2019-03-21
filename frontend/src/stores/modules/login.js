import { createAction ,handleActions} from 'redux-actions';
import {Map,List} from 'immutable';
const CHANGE_ID = 'login/CHANGE_ID';
const CHANGE_PASS = 'login/CHANGE_PASS';
const LOGIN_BTN = 'login/LOGIN_BTN';

export const changeId = createAction(CHANGE_ID, value=>value);
export const changePass = createAction(CHANGE_PASS, value=>value);
export const loginBtn = createAction(LOGIN_BTN);

const initialState = Map({
    inputId:'',
    inputPass:'',
    user:{
        id:'id',
        pass:'pass'
    }
});
export default handleActions({
    [CHANGE_ID] : (state,action) => state.set('inputId',action.payload),
    [CHANGE_PASS] : (state,action) => state.set('inputPass',action.payload),
    [LOGIN_BTN] : (state,action) => { 
        console.log(action.payload);
        return state.set('user',action.payload) ;    
    },
},initialState);