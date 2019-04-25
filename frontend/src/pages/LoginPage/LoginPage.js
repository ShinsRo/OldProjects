import React, { Component } from 'react';
import LoginContainer from './containers/LoginContainer';

class LoginPage extends Component {

    render() {

        return (
            <LoginContainer history={this.props.history}/>
        );
    }
}

export default LoginPage;