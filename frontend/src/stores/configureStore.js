import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import modules from './modules';

const createStoreWithMiddleware = applyMiddleware(
  thunkMiddleware,
)(createStore);

export default function configureStore(initialState) {
  return createStoreWithMiddleware(modules, initialState);
}