import {combineReducers} from 'redux';
import userState from './userState'
import projectState from "./projectState";
import juniorList from './juniorList';
// reducer 합치는곳 
export default combineReducers({
    userState, projectState,juniorList
});
