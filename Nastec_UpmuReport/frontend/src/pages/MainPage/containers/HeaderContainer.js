/** 
 * 헤더 컨테이너 정의
 * 
 * 2019.05.22
 * @file HeaderContainer 정의
 * @author 김승신, 마규석
 */

import React, { Component } from 'react';

import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as userActions from '../../../stores/modules/userState'
import store from '../../../stores';

import { Header } from '../components/Header';

class HeaderContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirect: false,
        }
        this.handleLogout = this.handleLogout.bind(this);
    }

    handleLogout() {
        const { UserActions } = this.props;
        this.setState({ redirect: true });
        
        UserActions.logout();
    }

    renderRedirect() {
        if (this.state.redirect) { 
            return (<Redirect to='/'></Redirect>)
        } 
    }

    render() {
        const { userState } = store.getState();
        const {openModal,closeModal,visible} = this.props;
        
        return (
            <div>
                {this.renderRedirect()}
                <Header handleLogout={this.handleLogout} userInfo={userState.userInfo}
                        openModal={openModal}
                        closeModal={closeModal}
                        visible={visible}/>
            </div>
        );
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
    }),
    (dispatch) => ({
        UserActions: bindActionCreators(userActions, dispatch)
    })
) (HeaderContainer);