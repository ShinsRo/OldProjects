import React from 'react';
import { Route, BrowserRouter, withRouter, Switch } from 'react-router-dom';
import { Dashboard, NotFound, LoginContainer } from "./containers";
import Register from "./components/Register"
import { Provider } from 'react-redux';
import store from './stores'

// 상태가 바뀔때마다 기록합니다.
store.subscribe(() =>
  console.log('상태 변동 감지 >> ', store.getState())
);

const RouteAsUserInfo = withRouter(({ match, location, history }) => {
    const { userState } = store.getState();
    
    let routes = [];
    if ( !(userState && userState.hasOwnProperty('userInfo') && userState.userInfo)) {
        routes = [
            { path: '/', component: LoginContainer },
            { path: '/register', component : Register},
        ]
    } else {
        routes = [
            { path: '/', component: Dashboard },
            { path: '/dashboard', component: Dashboard }
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
                { path: '/', component: LoginContainer },
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