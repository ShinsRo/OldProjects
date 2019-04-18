import {combineReducers} from 'redux';
import userState from './userState'
import projectState from "./projectState";
import juniorList from './juniorList';
import pfileState from "./pfileState";
import attachmentState from "./attachmentState";

// reducer 합치는곳 
export default combineReducers({
    userState, projectState, pfileState, juniorList,attachmentState,
    
});
