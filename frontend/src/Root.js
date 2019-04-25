import React from 'react';
import { Route, BrowserRouter, withRouter, Switch } from 'react-router-dom';
// import { MainPage, NotFound, LoginPage, AdminPage } from "./containers";
import { MainPage, NotFound, LoginPage, AdminPage, RegisterPage, ProfilePage } from "./pages";
import { Provider } from 'react-redux';
import store from './stores';

// 상태가 바뀔때마다 기록합니다.
store.subscribe(() =>
  console.log('상태 변동 감지 >> ', store.getState())
);

const RouteAsUserInfo = withRouter(({ match, location, history }) => {
    const { userState } = store.getState();
    
    let routes = [];
    if ( !(userState && userState.hasOwnProperty('userInfo') && userState.userInfo)) {
        routes = [
            { path: '/', component: LoginPage },
            { path: '/register', component : RegisterPage},
        ]
    } else {
        routes = [
            { path: '/', component: MainPage },
            { path: '/main', component: MainPage },
            { path: '/adminpage', component: AdminPage },
            { path: '/profile', component: ProfilePage },
        ]
    }
    return (
        <Switch>
            {routes.map((route, idx) => {
                return <Route exact path={route.path} component={route.component} key={idx++}></Route>
            })}
            <Route component={NotFound}/>
        </Switch>
    );
}) 

export default class Root extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            routes: [
                { path: '/', component: LoginPage },
            ]
        };
    }

    componentDidMount() {
        
    }

    render() {
        return (
            <Provider store={store}>
                <BrowserRouter>
                    <div>
                        <RouteAsUserInfo/>
                    </div>
                </BrowserRouter>
            </Provider>
        );
    }
}