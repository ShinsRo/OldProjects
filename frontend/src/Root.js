/** 
 * Route 컴포넌트 정의
 * 
 * 2019.05.22
 * @file 라우팅을 전담하는 Route 컴포넌트를 정의.
 * @author 김승신
 */
import React from 'react';
import { Route, BrowserRouter, withRouter, Switch } from 'react-router-dom';
import { MainPage, NotFound, LoginPage, AdminPage, ProjectInfoPage } from "./pages";
import { Provider } from 'react-redux';
import store from './stores';

// 상태가 바뀔때마다 기록합니다.
// store.subscribe(() =>
//   console.log('상태 변동 감지 >> ', store.getState())
// );

const RouteAsUserInfo = withRouter(({ match, location, history }) => {
    const { userState } = store.getState();
    const {userInfo} = userState;
    let authToken;
    if(userInfo){
        authToken = userInfo.authToken;
    }

    // console.log("로그인",userInfo)
    let routes = [];
    if ( !(userState && userState.hasOwnProperty('userInfo') && userState.userInfo)) {
        
        routes = [
            { path: '/', component: LoginPage },
        ]
    } else {
        routes = [
            // { path: '/', component: MainPage },
            { path: '/main', component: MainPage },
        ];
        
        if(authToken.authorities[0].authority==="ROLE_ADMIN") { 
            routes.push({ path: '/adminpage', component: AdminPage })
            routes.push({ path: '/projectInfo', component: ProjectInfoPage })
            routes.push({ path: '/', component: AdminPage })
        }
        else{
            routes.push({ path: '/', component: MainPage })
        }

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