import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import * as reducers from './modules';
import * as temp from './modules/project_module';

console.log('asd',reducers, temp);

const createStoreWithMiddleware = applyMiddleware(
  thunkMiddleware,
)(createStore);



// export default function configureStore(initialState) {
//   return applyMiddleware(thunkMiddleware,) (createStore);
//   return createStoreWithMiddleware(reducers, initialState);
// }

const configureStore = (initialState) => {
    const store = createStore(modules, initialState);