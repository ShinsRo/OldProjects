import Login from '../components/Login'
import React, { Component } from 'react';
import {Map} from 'immutable';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as userActions from '../stores/modules/userState'
import store from '../stores'

class LoginContainer extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            inputId: '',
            inputPass: '',
        };
    }

    handleChangeInput = (e, target) => {
        //인풋 값 변경
        this.setState({ [target]: e.target.value });
    }
    handleLogin = () => {
        //로그인정보 넘기기
        const { inputId, inputPass } = this.state;
        const { UserActions } = this.props;

        const loginInfo = Map({
            userId:inputId,
            userPass:inputPass});

        UserActions.getPost(loginInfo).then(res => {

            const { userState } = store.getState();
            const { userInfo } = userState;
            console.log('너는 누구냐', this);
            
            const { history } = this.props;
            
            if ( userInfo ) {
                history.push('/dashboard')
            } else {
                alert('아니, 비밀번호도 기억못함? 아니면 설마 아이디?????')
            }

        });
    }

    render() {
        const {handleChangeInput, handleLogin} = this;
        const {inputId, inputPass} = this.state;

        return(
            <Login
                inputId={inputId}
                inputPass={inputPass}
                onChangeInput={handleChangeInput}
                onLogin={handleLogin}
            />
        );
    }
}
export default connect(
    //state를 비구조화
    (state) => ({
        userState: state.userState
    }),
    //함수들
    (dispatch) => ({
        UserActions: bindActionCreators(userActions, dispatch)
    })
)(LoginContainer);