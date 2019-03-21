import { createStore, applyMiddleware, combineReducers } from 'redux';
import thunkMiddleware from 'redux-thunk';
import modules from './modules/project_module';

const createStoreWithMiddleware = applyMiddleware(
  thunkMiddleware,
)(createStore);


export default function configureStore(initialState) {
  return createStoreWithMiddleware(modules, initialState);
}