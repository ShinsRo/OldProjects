import React, { Component } from 'react';
import Login from '../components/Login'
import {Map,List} from 'immutable';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as loginActions from '../stores/modules/login'
import * as loginPostActions from '../stores/modules/loginPost'

class LoginContainer extends Component {
    handleChangeId = (e) => {
        //id 인풋 값 변경
        const {LoginActions}=this.props;
        LoginActions.changeId(e.target.value);
        console.log(loginActions);
    }
    handleChangePass = (e) => {
        //Pass 인풋 값 변경
        const {LoginActions}=this.props;
        LoginActions.changePass(e.target.value);
    }
    handleLogin = () => {
        //로그인정보 넘기기
        const {inputId, inputPass, LoginActions,LoginPostActions} = this.props;
        const user = Map({
            userId:inputId,
            userPass:inputPass});
        LoginActions.loginBtn(user);
        LoginPostActions.getPost(user);
        LoginActions.changeId("");
        LoginActions.changePass("");
        console.log(user);
    } 

    constructor(props) {
        super(props);
    }
    render() {
        const {handleChangeId, handleChangePass, handleLogin} = this;
        const {inputId, inputPass, user} = this.props;

        return(
            <Login
                inputId={inputId}
                inputPass={inputPass}
                onChangeId={handleChangeId}
                onChangePass={handleChangePass}
                onLogin={handleLogin}
            />
        );
    }
}
export default connect(
    //state를 비구조화
    ({login}) => ({
        inputId: login.get('inputId'),
        inputPass: login.get('inputPass'),
        user: login.get('user')
    }),
    //함수들
    (dispatch) => ({
        LoginActions: bindActionCreators(loginActions, dispatch),
        LoginPostActions: bindActionCreators(loginPostActions, dispatch)
    })
)(LoginContainer);