import React, { Component } from "react";
import ProjectSideBarContainer from "./ProjectSideBarContainer";
import HeaderContainer from "./HeaderContainer";
import SidebarContainer from "./SidebarContainer"
import Pfile from "../components/Pfile/Pfile";
import Member from "../components/Admin/Member"
import Profile_Career from "../components/Profile/Profile_Career"
import store from '../stores'
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as userActions from '../stores/modules/userState'
import PasswordModify from "../components/Profile/PasswordModify"

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
    const {handleLogin} = this;
    const {userState} = this.props;
    const {juniorList} = this.props;
    const deptList=[];
 
    // const {userState} = store.getState();
    console.log(">>>>>>>>>", userState.userInfo.memberInfo);
    const myInfo=userState.userInfo.memberInfo
    //const this.setState = userState.selectedUser.memberInfo || userState.selectedUser
    return (
      <div id="wrapper">
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history}/>
            {/* Page Content  */}
            임시 테스트 { userState.selectedUser.name }
            <div className="container-fluid">
            <div className="row">
              <div className="col-xl-6">
              <Member selectUser={myInfo}/>
                왼쪽
              </div>
              <div className="col-xl-6">
              <Profile_Career selectUser={myInfo}
                      deptList={deptList}
              />
                  오른쪽 
                </div>
                </div>
            </div>

          </div>
          <PasswordModify selectUser={myInfo} />

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
    juniorList: state.juniorList,
    userState: state.userState,
  }),
  //함수들
  (dispatch) => ({
      userActions: bindActionCreators(userActions, dispatch),
  })
)(ProFilePage);