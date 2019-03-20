import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { Route } from 'react-router-dom';
import { Dashboard, App, DashboardTest } from "./containers";

import configureStore from './stores/configureStore';
import { Provider } from 'react-redux';

const store = configureStore();

// 상태가 바뀔때마다 기록합니다.
let unsubscribe = store.subscribe(() =>
  console.log(store.getState())
);


export default class Root extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <BrowserRouter>
                    <div>
                        <Route exact path="/" component={App}></Route>
                        <Route exact path="/dashboard" component={Dashboard}></Route>
                        <Route exact path="/dashboardtest" component={DashboardTest}></Route>
                    </div>
                </BrowserRouter>
            </Provider>
        );
    }
}