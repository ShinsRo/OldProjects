import React, { Component } from "react";
import HeaderContainer from "./containers/HeaderContainer";
import Member from "./components/Admin/Member"
import AddDept from "./components/Admin/AddDept"
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux'
import * as userActions from '../../stores/modules/userState'
import axios from 'axios';
import { BASE_URL } from '../../supports/API_CONSTANT';
import AddPosi from "./components/Admin/AddPosi";
import Modal from 'react-awesome-modal'
import UserTable from './components/Admin/UserTable'
import Register from './components/Admin/RegisterPage';
import { MDBBtn, MDBIcon } from 'mdbreact'
import DelDept from "./components/Admin/DelDept";
import DelPosi from "./components/Admin/DelPosi";
class AdminPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      viewLevel: 'USERS',
      selectUser: '',
      visible: false,        //신규 사원 등록 모달
      visible1: false,      //부서 및 직책 관리 모달
      visible2: false,      //커리어 변경 모달

    };
  }
  toggle = () => {
    this.setState({
      modal: !this.state.modal
    });
  }
  handleLogin = (userInfo) => {
    const { userActions } = this.props
    userActions.select(userInfo)
  }
  getDeptPosiAPI() {
    return axios.post(`${BASE_URL}/api/career/getdeptposi`).then(
      (response) => {
        this.setState({ list: response.data })
      }
    )
  }
  openModal(target, opt) {
    this.setState({
      [target]: true,
      select: opt
    });
  }

  closeModal(target) {
    this.setState({
      [target]: false
    });
  }

  componentDidMount() {
    this.getDeptPosiAPI().then(res => {
      // console.log("did", this.state)
    })
  }

  render() {
    const { handleLogin } = this;
    const { userState } = this.props;
    // const { juniorState } = this.props;
    let deptList = [];
    let posiList = [];
    if (this.state.list) {
      deptList = this.state.list.deptList
      posiList = this.state.list.posiList
    }

    let content;

    if (this.state.viewLevel === "USERS") {
      content = (
        <div>
          {/* 사원등록 메뉴 */}
          <div className="row mb-3">
            {/* <input type="button" className="btn btn-primary btn-icon-split ml-4" value="  신규 사원 등록   " onClick={() => this.openModal('visible')} /> */}
            <MDBBtn outline rounded size="sm" className="ml-4" color="primary" onClick={() => this.openModal('visible')}><MDBIcon icon="user-plus" className="mr-2" onClick={() => this.openModal('visible')} />신규 사원 등록</MDBBtn>
            <Modal visible={this.state.visible} width="420px" height="450px" effect="fadeInLeft" onClickAway={() => this.closeModal('visible')}>
              <div className="">
                <Register deptList={deptList} posiList={posiList} />
                <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
              </div>
            </Modal>

            {/* 부서 및 관리 드롭다운 메뉴 */}
            <div className="dropdown">
              <MDBBtn className="btn dropdown-toggle ml-3" outline rounded size="sm" color="primary" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><MDBIcon icon="landmark" className="mr-2" />부서 및 직책 관리</MDBBtn>
              {/* 목록 추가 */}
              <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <div className="dropdown-item" href="#" onClick={() => this.openModal('visible1', 1)}>부서 추가</div>
                <div className="dropdown-divider"></div>
                <div className="dropdown-item" href="#" onClick={() => this.openModal('visible1', 2)}>부서 제거</div>
                <div className="dropdown-divider"></div>
                <div className="dropdown-item" href="#" onClick={() => this.openModal('visible1', 3)}>직책 추가</div>
                <div className="dropdown-divider"></div>
                <div className="dropdown-item" href="#" onClick={() => this.openModal('visible1', 4)}>직책 제거</div>
              </div>
            </div>

            {/* 부서 및 관리 모달 메뉴 */}
            <Modal visible={this.state.visible1} width="400" height="190" effect="fadeInLeft" onClickAway={() => this.closeModal('visible1')}>
              <div >
                <h4 align="center" className="card-header text-primary"><MDBIcon icon="angle-down" className="mr-2" />부서 및 직책 관리</h4>
                {
                  this.state.select === 1 && <AddDept deptList={deptList}></AddDept>
                }
                {
                  this.state.select === 2 && <DelDept deptList={deptList}></DelDept>
                }
                {
                  this.state.select === 3 && <AddPosi posiList={posiList}></AddPosi>
                }
                {
                  this.state.select === 4 && <DelPosi posiList={posiList}></DelPosi>
                }
              </div>
              <MDBBtn rounded size="sm" className="mt-4" color="primary" onClick={() => this.closeModal('visible1')}><MDBIcon icon="times" className="mr-2" />close</MDBBtn>
            </Modal>
          </div>
          <div className="row" >
            <div className="col-8">
              <div className="card shadow font-weight-bold">
                <div className="card-header font-weight-bold text-black">사원 목록</div>
                <div className="card-body">
                  <UserTable select={handleLogin}
                    tyle={{ height: '100%' }}></UserTable>
                </div>
              </div>
            </div>
            <div className="col">
              <Member selectUser={userState.selectedUser.name ? userState.selectedUser : userState.userInfo.memberInfo}
                deptList={deptList}
                posiList={posiList}
                mode={true}
                style={{ height: '100%' }}
              />

            </div>
          </div>
        </div>
      );
    }   //ViewLevel  => USERS content 

    return (
      <div id="wrapper">
        {/* <SidebarContainer *}*/}
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">
          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history} />
            {/* Page Content  */}
            <div className="container-fluid mt-5">
              {content}
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