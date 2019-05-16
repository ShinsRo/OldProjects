import React, { Component } from "react";
import HeaderContainer from "./containers/HeaderContainer";
import SidebarContainer from "./containers/SidebarContainer"
import Member from "./components/Admin/Member"
import Career from "./components/Admin/Career"
import AddDept from "./components/Admin/AddDept"
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux'
import * as userActions from '../../stores/modules/userState'
import axios from 'axios';
import { BASE_URL } from '../../supports/API_CONSTANT';
import AddPosi from "./components/Admin/AddPosi";
import Modal from 'react-awesome-modal'
import UserTable from './components/Admin/UserTable'
import Register from '../RegisterPage/index'
import { MDBBtn, MDBIcon } from 'mdbreact'
import { MDBContainer, MDBModal, MDBModalBody, MDBModalFooter, MDBModalHeader } from 'mdbreact'
import DelDept from "./components/Admin/DelDept";
import DelPosi from "./components/Admin/DelPosi";
class AdminPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
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
  // retireAPI() {   Member 로 이동
  //   const { userState } = this.props;
  //   const memberDto = userState.selectedUser
  //   //console.log("보낸다 가라아아앗", memberDto)
  //   if (memberDto.memberDto === '') return alert("오류 입니다")
  //   return axios.post(`${BASE_URL}/api/users/retire`, memberDto)
  //     .then(
  //       (response) => {
  //         //js 는 빈 문자열 빈오브젝트 false 
  //         if (!response.data) alert("에러 입니다")
  //         else {
  //           alert(memberDto.name + " 퇴사 처리 되었습니다")
  //           window.location.href = "/adminpage";
  //         }
  //       }
  //     )
  // }

  openModal(target,opt) {
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
    const { juniorState } = this.props;
    let deptList = [];
    let posiList = []
    const members = juniorState.get('users')
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
    // console.log("리스트들", deptList)
    // const {userState} = store.getState();
    //const this.setState = userState.selectedUser.memberInfo || userState.selectedUser
    return (
      <div id="wrapper">
        {/* <SidebarContainer
          select={handleLogin}
          deptList={this.state.list && deptList}
          posiList={this.state.list && posiList}
        /> */}
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">
          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history} />
            {/* 사원등록 메뉴 */}
            <div className="row">
              {/* <input type="button" className="btn btn-primary btn-icon-split ml-4" value="  신규 사원 등록   " onClick={() => this.openModal('visible')} /> */}
              <MDBBtn outline rounded size="sm" className="ml-4" color="primary" onClick={() => this.openModal('visible')}><MDBIcon icon="user-plus" className="mr-2" onClick={() => this.openModal('visible')} />신규 사원 등록</MDBBtn>
              <Modal visible={this.state.visible} width="420px" height="450px" effect="fadeInLeft" onClickAway={() => this.closeModal('visible')}>
                <div className="">
                  <Register deptList={deptList} posiList={posiList} />
                  <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
                  {/* <input align="right" type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible')}></input> */}
                </div>
              </Modal>
              
              {/* 부서 및 관리 드롭다운 메뉴 */}
              <div className="dropdown">
                <MDBBtn className="btn dropdown-toggle ml-3" outline rounded size="sm" color="primary" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><MDBIcon icon="landmark" className="mr-2" />부서 및 직책 관리</MDBBtn>
                {/* <button className="btn dropdown-toggle text-dark-1" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  목록 추가
                </button> */}
                <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                  <a className="dropdown-item" href="#" onClick={() => this.openModal('visible1',1)}>부서 추가</a>
                  <div className="dropdown-divider"></div>
                  <a className="dropdown-item" href="#" onClick={() => this.openModal('visible1',2)}>부서 제거</a>
                  <div className="dropdown-divider"></div>
                  <a className="dropdown-item" href="#" onClick={() => this.openModal('visible1',3)}>직책 추가</a>
                  <div className="dropdown-divider"></div>
                  <a className="dropdown-item" href="#" onClick={() => this.openModal('visible1',4)}>직책 제거</a>
                </div>
              </div>

              {/* 부서 및 관리 모달 메뉴 */}
              {/* <input type="button" className="btn btn-primary btn-icon-split ml-3" value="   부서 및 직책 관리   " onClick={() => this.openModal('visible1')} /> */}
              {/* <MDBBtn outline rounded size="sm" className="ml-3" color="primary" onClick={() => this.openModal('visible1')}><MDBIcon icon="landmark" className="mr-2" />부서 및 직책 관리</MDBBtn> */}
              <Modal visible={this.state.visible1} width="400" height="190" effect="fadeInLeft" onClickAway={() => this.closeModal('visible1')}>
                <div >
                  <h4 align="center" className="card-header text-primary"><MDBIcon icon="landmark" className="mr-2" />부서 및 직책 관리</h4>
                  {
                    this.state.select==1 && <AddDept deptList={deptList}></AddDept>
                  }
                  {
                    this.state.select==2 && <DelDept deptList={deptList}></DelDept>
                  }
                  {
                    this.state.select==3 && <AddPosi posiList={posiList}></AddPosi>
                  }
                  {
                    this.state.select==4 && <DelPosi posiList={posiList}></DelPosi>
                  }
                </div>
                {/* <input type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible1')}></input> */}
                <MDBBtn rounded size="sm" className="mt-4" color="primary" onClick={() => this.closeModal('visible1')}><MDBIcon icon="times" className="mr-2" />close</MDBBtn>
              </Modal>




            </div>
            {/* Page Content  */}
            <div className="container-fluid mt-5" >
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
                  <div className="row">
                    {/* 커리어 변경 */}
                    {/* <div className="">
                      <MDBBtn className="ml-3" outline rounded size="sm" color="primary" onClick={() => this.openModal('visible2')}><MDBIcon icon="user-cog" className="mr-1" onClick={() => this.openModal('visible2')} />커리어 변경</MDBBtn>
                      <Modal visible={this.state.visible2} width="" height="" effect="fadeInLeft" onClickAway={() => this.closeModal('visible2')}>
                        <div>
                          <Career selectUser={userState.selectedUser}
                            deptList={deptList}
                            posiList={posiList} />
                          <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible2')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
                        </div>
                      </Modal>
                    </div> */}
                    {/* 퇴사 메뉴 모달 */}
                    {/* <div className="">
                      <MDBContainer>
                        <MDBBtn outline rounded size="sm" color="danger" onClick={this.toggle}><MDBIcon icon="user-slash" className="" /> 퇴사</MDBBtn>
                        <MDBModal isOpen={this.state.modal} toggle={this.toggle}>
                          <MDBModalHeader toggle={this.toggle}> 퇴사 </MDBModalHeader>
                          <MDBModalBody>
                            {userState.selectedUser.name} 님이 퇴사하셨습니까?
                      </MDBModalBody>
                          <MDBModalFooter>
                            <MDBBtn color="secondary" onClick={this.toggle}>아니요 </MDBBtn>
                            <MDBBtn color="primary" onClick={() => this.retireAPI()}> 네, 그렇습니다 </MDBBtn>
                          </MDBModalFooter>
                        </MDBModal>
                      </MDBContainer>
                    </div> */}
                  </div>

                </div>
                {/* <div className="col-xl-4">
                  <Career selectUser={userState.selectedUser}
                    deptList={deptList}
                    posiList={posiList}
                  />              
                </div> */}

              </div>
            </div>


          </div>
        </div>
        {/* <Modal visible={this.state.visible2} width="400" height="305" effect="fadeInLeft" onClickAway={() => this.closeModal('visible2')}>
          <div >
            <Member selectUser={userState.selectedUser}
              deptList={deptList}
              posiList={posiList}
              mode={true}
            />
          </div>
          <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible2')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
        </Modal> */}
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