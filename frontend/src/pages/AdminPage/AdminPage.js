import React, { Component } from "react";
import HeaderContainer from "./containers/HeaderContainer";
import SidebarContainer from "./containers/SidebarContainer"
import Member from "./components/Admin/Member"
import Career from "./components/Admin/Career"
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux'
import * as userActions from '../../stores/modules/userState'
import axios from 'axios';
import { BASE_URL } from '../../supports/API_CONSTANT';
import UserTable from './components/Admin/UserTable'

class AdminPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectUser: '',
      visible: false
    };

  }
  componentWillMount() {
    this.getDeptPosiAPI();
  }

  handleLogin = (userInfo) => {
    const { userActions } = this.props
    userActions.select(userInfo)
    //const {userState} = store.getState();
    //const {selectedUser} = userState;
  }
  getDeptPosiAPI() {
    // console.log("요청한다 List 가져와라")
    return axios.post(`${BASE_URL}/api/career/getdeptposi`).then(
      (response) => {
        // console.log("요청성공 List 가져와라",response.data)
        this.setState({ list: response.data })
      }
    )
  }
  openModal() {
    this.setState({
      visible: true
    });
  }

  closeModal() {
    this.setState({
      visible: false
    });
  }



  render() {
    const { handleLogin } = this;
    const { userState } = this.props;
    const { juniorState } = this.props;
    let deptList = [];
    let posiList = []
    // if (members) {
    //   members.forEach(member => {
    //     let Careers = member.career
    //     if (Careers) {
    //       Careers.forEach(car => {
    //         //!deptName.includes(user.dept) && deptName.push(user.dept)
    //         !deptList.includes(car.dept) && deptList.push(car.dept)
    //       });
    //     }
    //   });
    // }
    if (this.state.list) {
      deptList = this.state.list.deptList
      posiList = this.state.list.posiList
    }
    // const {userState} = store.getState();
    //const this.setState = userState.selectedUser.memberInfo || userState.selectedUser
    return (
      <div id="wrapper">
        <SidebarContainer
          select={handleLogin}
          deptList={this.state.list && deptList}
          posiList={this.state.list && posiList}
        />
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history} />
            {/* Page Content  */}
            {/* 임시 테스트 { userState.selectedUser.name } */}
            <div className="container-fluid" >
              <div className="row" >
                <div className="col-xl-5">
                  <Member selectUser={userState.selectedUser} style={{ height : '100px' }}/>
                </div>
                <div className="col-xl-4">
                  <Career selectUser={userState.selectedUser}
                    deptList={deptList}
                    posiList={posiList}
                  />
                                    
                  {/* <section>
                    <div className="text-xl font-weight-bold text-primary">부서 및 직책 관리</div>
                    <input type="button" className="btn btn-primary btn-icon-split" value="   Open   " onClick={() => this.openModal()} />
                    <Modal visible={this.state.visible} width="400" height="300" effect="fadeInUp" onClickAway={() => this.closeModal()}>
                      <div>
                        <Dept deptList={deptList}></Dept>
                        <Posi posiList={posiList}></Posi>
                        <a href="javascript:void(0);" onClick={() => this.closeModal()}>Close</a>
                      </div>
                    </Modal>
                  </section> */}

                  {/* 오른쪽  */}
                </div>
                <div className="col-xl">
                <input type="button" className="btn btn-danger btn-icon-split" value="   퇴사   " onClick={() => this.openModal()} />
                </div>
                {/* <Dept></Dept>
                <Posi></Posi> */}
              </div>


            </div>
            <UserTable select={handleLogin}></UserTable>

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