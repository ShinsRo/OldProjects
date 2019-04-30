import React, { Component } from "react";
import HeaderContainer from "../AdminPage/containers/HeaderContainer";
import Member from "../AdminPage/components/Admin/Member"
import ProfileCareer from "./components/Profile/ProfileCareer"
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as userActions from '../../stores/modules/userState'
import PasswordModify from "./components/Profile/PasswordModify"
import Sidebar from "./Sidebar"

class ProFilePage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectUser:'', 
      };
  }

  handleLogin = (userInfo) => {
    const {userActions} = this.props
    userActions.select(userInfo)
    //const {userState} = store.getState();
    //const {selectedUser} = userState;
  }
  
  render() {
    const {userState} = this.props;
    const deptList=[];
 
    // const {userState} = store.getState();
    const myInfo=userState.userInfo.memberInfo
    //const this.setState = userState.selectedUser.memberInfo || userState.selectedUser
    return (
      <div id="wrapper">
      <Sidebar></Sidebar>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">
          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history}/>
            {/* Page Content  */}
            임시 { userState.selectedUser.name }
            <div className="container-fluid">
            <div className="row">
              <div className="col-xl-2">
              <Member selectUser={myInfo}/> 
              </div>
              <div className="col-xl-2">
              <ProfileCareer selectUser={myInfo}
                      deptList={deptList}
              />
                  
                </div>
                </div>
            </div>
            <PasswordModify selectUser={myInfo} />
          </div>
          <div>하 단 </div>

        </div>
      </div>
    );
  }
};
export default connect(     
  //state를 비구조화
  (state) => ({
    ////스마트 컨테이너에게 userState라는 props로 store의 userState를 전달
    juniorState: state.juniorState,
    userState: state.userState,
  }),
  //함수들
  (dispatch) => ({
      userActions: bindActionCreators(userActions, dispatch),
  })
)(ProFilePage);