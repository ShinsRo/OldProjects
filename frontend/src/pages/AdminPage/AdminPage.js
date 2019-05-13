import React, { Component } from "react";
import HeaderContainer from "./containers/HeaderContainer";
import SidebarContainer from "./containers/SidebarContainer"
import Member from "./components/Admin/Member"
import Career from "./components/Admin/Career"
import Dept from "./components/Admin/Dept"
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux'
import * as userActions from '../../stores/modules/userState'
import axios from 'axios';
import { BASE_URL } from '../../supports/API_CONSTANT';
import Posi from "./components/Admin/Posi";
import Modal from 'react-awesome-modal'
import UserTable from './components/Admin/UserTable'
import Register from '../RegisterPage/index'
import { MDBBtn, MDBIcon } from 'mdbreact'
import { MDBContainer, MDBModal, MDBModalBody, MDBModalFooter, MDBModalHeader } from 'mdbreact'

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
  retireAPI() {
    const { userState } = this.props;
    const memberDto = userState.selectedUser
    //console.log("보낸다 가라아아앗", memberDto)
    if (memberDto.memberDto === '') return alert("오류 입니다")
    return axios.post(`${BASE_URL}/api/users/retire`, memberDto)
    .then(
        (response) => {
            //js 는 빈 문자열 빈오브젝트 false 
            if(!response.data) alert("에러 입니다")
            else {
                alert(memberDto.name+" 퇴사 처리 되었습니다")
                window.location.href="/adminpage";
            }
        }
    )
}

  openModal(target) {
    this.setState({
      [target]: true
    });
  }

  closeModal(target) {
    this.setState({
      [target]: false
    });
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
            {/* 사원등록 메뉴 */}
            <div className="row">
              {/* <input type="button" className="btn btn-primary btn-icon-split ml-4" value="  신규 사원 등록   " onClick={() => this.openModal('visible')} /> */}
              <MDBBtn outline rounded size="sm" className="ml-4" color="primary" onClick={() => this.openModal('visible')}><MDBIcon icon="user-plus" className="mr-2" onClick={() => this.openModal('visible')} />신규 사원 등록</MDBBtn>
              <Modal visible={this.state.visible} width="420px" height="450px" effect="fadeInLeft" onClickAway={() => this.closeModal('visible')}>
                <div className="">
                  <Register />
                  <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
                  {/* <input align="right" type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible')}></input> */}
                </div>
              </Modal>
              {/* 부서 및 관리 메뉴 */}
              {/* <input type="button" className="btn btn-primary btn-icon-split ml-3" value="   부서 및 직책 관리   " onClick={() => this.openModal('visible1')} /> */}
              <MDBBtn outline rounded size="sm" className="ml-3" color="primary" onClick={() => this.openModal('visible1')}><MDBIcon icon="landmark" className="mr-2" />부서 및 직책 관리</MDBBtn>
              <Modal visible={this.state.visible1} width="400" height="290" effect="fadeInLeft" onClickAway={() => this.closeModal('visible1')}>
                <div >
                  <Dept deptList={deptList}></Dept>
                  <Posi posiList={posiList}></Posi>
                </div>
                {/* <input type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible1')}></input> */}
                <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible1')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
              </Modal>
            </div>
            {/* Page Content  */}
            <div className="container-fluid mt-5" >
              <div className="row" >
                <div className="col-xl-7 card">
                  <UserTable select={handleLogin} style={{ height: '100%'}}></UserTable>
                </div>
                <div className="col-xl-5">
                  <Member selectUser={userState.selectedUser} />
                  <div className="row">
                  {/* 커리어 변경 */}
                  <div className="">
                  <MDBBtn className="ml-3" outline rounded size="sm" color="primary" onClick={() => this.openModal('visible2')}><MDBIcon icon="user-cog" className="mr-1" onClick={() => this.openModal('visible2')} />커리어 변경</MDBBtn>
                  {/* <input type="button" className="btn btn-primary btn-icon-split ml-3" value=" 커리어 변경  " onClick={() => this.openModal('visible2')} /> */}
                  <Modal visible={this.state.visible2} width="" height="" effect="fadeInLeft" onClickAway={() => this.closeModal('visible2')}>
                    <div>
                      <Career selectUser={userState.selectedUser}
                        deptList={deptList}
                        posiList={posiList} />
                      {/* <input type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible2')}></input> */}
                      <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible2')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
                    </div>
                  </Modal>
                  </div>
                  {/* 퇴사 메뉴 모달 */}
                  <div className="">
                  <MDBContainer>
                  <MDBBtn outline rounded size="sm" color="danger" onClick={this.toggle}><MDBIcon icon="user-cog" className=""/>퇴사</MDBBtn>
                    <MDBModal isOpen={this.state.modal} toggle={this.toggle}>
                      <MDBModalHeader toggle={this.toggle}> 퇴사 </MDBModalHeader>
                      <MDBModalBody>
                        {userState.selectedUser.name} 님이 퇴사하셨습니까?
                      </MDBModalBody>
                      <MDBModalFooter>
                        <MDBBtn color="secondary" onClick={this.toggle}>아니요 </MDBBtn>
                        <MDBBtn color="primary" onClick={()=>this.retireAPI()}> 네, 그렇습니다 </MDBBtn>
                      </MDBModalFooter>
                    </MDBModal>
                  </MDBContainer>
                  </div>
                  </div>


                  {/* <div className="col-xl">
                    <input type="button" className="btn btn-danger btn-icon-split" value="   퇴사   " onClick={() => this.openModal()} />
                  </div> */}
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