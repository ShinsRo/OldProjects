import React, { Component } from "react";
import HeaderContainer from "./containers/HeaderContainer";
import SidebarContainer from "./containers/SidebarContainer"
import Member from "./components/Admin/Member"
import Career from "./components/Admin/Career"
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux'
import * as userActions from '../../stores/modules/userState'


class AdminPage extends Component {
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
    const {juniorState} = this.props;
    const deptList=[];
    const members=juniorState.get('users')
    if (members) {
      members.forEach(member => {
        let Careers = member.career
        if (Careers) {
          Careers.forEach(car => {
            //!deptName.includes(user.dept) && deptName.push(user.dept)
            !deptList.includes(car.dept) && deptList.push(car.dept)
          });
        }
      });
    }

    // const {userState} = store.getState();
    //const this.setState = userState.selectedUser.memberInfo || userState.selectedUser
    return (
      <div id="wrapper">
        <SidebarContainer 
          select={handleLogin}
        />
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
              <Member selectUser={userState.selectedUser}/>
                왼쪽
              </div>
              <div className="col-xl-6">
              <Career selectUser={userState.selectedUser}
                      deptList={deptList}
              />
                  오른쪽 
                </div>
                </div>
            </div>

          </div>

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
)(AdminPage);