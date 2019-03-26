import Sidebar from '../components/Sidebar/Sidebar'
import React, { Component } from 'react';
import {List,Map} from 'immutable';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as juniorsAction from '../stores/modules/juniorList'
import store from '../stores'
class SidebarContainer extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            users:this.props.users
        };
    }
    componentWillMount(){
        const {userState} = store.getState()
        const {userInfo} = userState
        const {juniorsAction} = this.props;
        console.log("사이드바 api",userInfo);
        juniorsAction.getJuniors(userInfo).then(res=> {
            const {juniorList} =store.getState()
            this.setState({
               users: juniorList.get('users')
           })
        });
    }
    render() {
        console.log('사이드바컨테이너 확인',this.state.users)
        const users=this.state.users
        return(
            <Sidebar
                users={users}
            />
        );
    }
}
export default connect(
    //state를 비구조화
    (state) => ({
        users: state.users
    }),
    //함수들
    (dispatch) => ({
        juniorsAction: bindActionCreators(juniorsAction, dispatch)
    })
)(SidebarContainer);