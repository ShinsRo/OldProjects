import React from 'react';
import ReactDOM from 'react-dom';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import Root from "./Root";
import appReducers from './reducers';
import * as serviceWorker from './serviceWorker';

let store = createStore(appReducers);

ReactDOM.render(
    <Provider store={store}><Root /></Provider>, document.getElementById('root'));
serviceWorker.unregister();
