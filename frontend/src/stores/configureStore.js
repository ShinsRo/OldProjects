import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import appReducers from '../reducers/proj-panel-reducer';

const createStoreWithMiddleware = applyMiddleware(
  thunkMiddleware,
)(createStore);

export default function configureStore(initialState) {
  return createStoreWithMiddleware(appReducers, initialState);
}