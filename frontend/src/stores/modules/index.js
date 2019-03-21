import {combineReducers} from 'redux';
import login from './login';
import loginPost from './loginPost'
// reducer 합치는곳 
export default combineReducers({
    login,loginPost,
});