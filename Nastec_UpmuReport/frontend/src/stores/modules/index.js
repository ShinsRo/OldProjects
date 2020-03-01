import {combineReducers} from 'redux';
import userState from './userState'
import projectState from "./projectState";
import juniorState from './juniorState';
import pfileState from "./pfileState";
import attachmentState from "./attachmentState";
import plogState from "./plogState";

// reducer 합치는곳 
export default combineReducers({
    userState, projectState, pfileState, juniorState, attachmentState,plogState,    
});