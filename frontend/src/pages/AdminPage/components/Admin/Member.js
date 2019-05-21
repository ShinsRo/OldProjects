import React, { Component } from 'react';
import { select } from 'glamor';
import store from '../../../../stores'
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT'
import { MDBBtn, MDBIcon } from 'mdbreact'
import { MDBContainer, MDBModal, MDBModalBody, MDBModalFooter, MDBModalHeader } from 'mdbreact'


class Member extends Component {
  constructor(props) {
    super(props);


    this.state = {
      selectUser: '',
      visible: false,        //신규 사원 등록 모달
      visible1: false,      //부서 및 직책 관리 모달
      visible2: false,      //커리어 변경 모달
      mode: true,           //읽기모드 // disabled=mode
      dept: '',
      posi: '',
      phoneNum: '',
    };

  }
  changeCareerAPI(currentDept, currentPosi) {
    const careerInfo = {
      dept: this.state.dept,
      posi: this.state.posi
    }
    // if ( (careerInfo.dept == currentDept) && (careerInfo.posi == currentPosi) ) {
    //   return alert("변경된 사항이 없습니다.")
    // }

    return axios.post(`${BASE_URL}/api/career/modify`,
      {
        mem: this.props.selectUser,
        newCar: careerInfo
      }
    ).then(
      (response) => {
        if (!response.data) alert("잘못 된 요청입니다")
        else {
          // alert("부서:" + response.data.dept + "\n직책:" + response.data.posi + "\n변경 되었습니다")
          // window.location.href = "/adminpage";
        }
      }

    )
  }
  modifyPhoneNumAPI() {
    const mem = {
      mid: this.props.selectUser.mid,
      phoneNum: this.state.phoneNum
    }
    return axios.put(`${BASE_URL}/api/users/modify/phone`, mem
    ).then(
      (response) => {
        if (!response.data) alert("잘못 된 요청입니다")
        else {
          // alert("부서:" + response.data.dept + "\n직책:" + response.data.posi + "\n변경 되었습니다")
          // window.location.href = "/adminpage";
        }
      }
    )
  }

  validatePhoneNumber = phoneNumberInput => {
    const phoneNumberRegExp = /^\d{3}-\d{3,4}-\d{4}$/;

    if (phoneNumberInput.match(phoneNumberRegExp)) {
      this.setState({
        isPhoneNumberValid: true,
        phoneNum: phoneNumberInput
      });
    } else {
      this.setState({
        isPhoneNumberValid: false,
        phoneNum: phoneNumberInput
      });
    }
  }

  onChange(e, target) {
    this.setState({
      [target]: e.target.value
    })


    // alert(e.target.value + "로 변경되었습니다.")
    // window.location.href = "/adminpage";
  }

  toggle = (target, dept, posi) => {
    const { selectUser } = this.props
    let changed = false
    if (target === 'modal') {
      this.setState({
        modal: !this.state.modal
      });
    }
    else if (target === 'mode') {      //읽기모드
      if (this.state.mode === false) {  // 읽기모드 false = > 쓰기모드
        if ((this.state.dept === dept) && (this.state.posi === posi) && (this.state.phoneNum === selectUser.phoneNum)) {
          alert("변경 된 사항이 없습니다.")
        }

        if ((this.state.dept === dept) && (this.state.posi === posi)) {
          // return alert("커리어가 변경 된 사항이 없습니다.")
        } else {
          this.changeCareerAPI(dept, posi)
          changed = true
        }

        if (this.state.phoneNum === selectUser.phoneNum) {
          //핸드폰 변경 없음
        }
        else {
          if (! this.state.isPhoneNumberValid) return alert("핸드폰 양식을 올바르게 기입하세요")
          this.modifyPhoneNumAPI()
          changed = true
        }
        if (changed === true) {  //한가지라도 변경이 되었다면
          window.location.href = "/adminpage";
          alert("변경 되었습니다.");
        }
      }
      // console.log("mode par",dept,"\npar2",posi)
      this.setState({
        mode: !this.state.mode,
        dept: dept,
        posi: posi,
        phoneNum: selectUser.phoneNum
      });

    }
    else if (target === 'cancel') {
      this.setState({
        mode: !this.state.mode,
        dept: dept,
        posi: posi,
        phoneNum: selectUser.phoneNum
      });
    }
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

  retireAPI() {
    // const { userState } = this.props;
    // const memberDto = userState.selectedUser
    const memberDto = this.props.selectUser
    // console.log("보낸다 가라아아앗", memberDto)
    if (memberDto.memberDto === '') return alert("오류 입니다")
    return axios.post(`${BASE_URL}/api/users/retire`, memberDto)
      .then(
        (response) => {
          //js 는 빈 문자열 빈오브젝트 false 
          if (!response.data) alert("에러 입니다")
          else {
            alert(memberDto.name + " 퇴사 처리 되었습니다")
            window.location.href = "/adminpage";
          }
        }
      )
  }
  componentWillReceiveProps(nextProps) {    
    // this.props 는 아직 바뀌지 않은 상태
    // console.log("prev",this.props,"\n",nextProps)
    if (this.props.selectUser.name !== nextProps.selectUser.name) { // 선택 된 유저가 바뀌었을때만 바꿔준다
      this.setState({
        mode: true
      })
    }
  }

  render() {
    // console.log("확인",this.props.selectUser)
    const { selectUser } = this.props;
    const { userState } = store.getState();
    const { userInfo } = userState;
    const { authToken } = userInfo;
    const { deptList, posiList } = this.props;

    // console.log("auth 체크",authToken.authorities[0].authority=="ROLE_ADMIN")
    // if(authToken.authorities[0].authority=="ROLE_ADMIN") alert("맞어")
    let i = 0
    let j = 0
    let currentCarrer;
    let pastCareer = [];
    const selectUser1 = selectUser;
    const careers = selectUser1.career;
    // 있으면 //
    if (careers) {
      careers.forEach(car => {
        if (car.active === true) {
          currentCarrer = car;
        }
        else {
          pastCareer.concat = car;
        }
      })
    }

    return (
      // <div className="form-group row">
      //       <div className="col-xl-12">
      //           <input name="pname" id="pname" type="text" className="form-control" placeholder="프로젝트 명" required/>
      //       </div>
      //   </div>
      <div className="card shadow font-weight-bold ">
        <div className="card-header font-weight-bold text-black">
          인원 상세 정보
        </div>
        {/* <div align="center">
          <i className="fas fa-id-card" style={{ fontSize: "150px" }} ></i>
        </div> */}
        <div className="card-body m-4">
          <div className="col">
            <div className="row">
              이름 : {selectUser.name}
            </div>
            <hr></hr>
            <div className="row">
              사번 : {selectUser.eid}
            </div>
            <hr></hr>
            <div className="row">
              입사일 : {selectUser.joinDate}
            </div>
            <hr></hr>
            <div className="row">
              생년월일 : {selectUser.birth}
            </div>
            <hr></hr>

            {this.state.mode &&
              <div className="row">
                핸드폰 : {selectUser.phoneNum}
                {/* 핸드폰 :<input className="ml-2 col-5 form-control form-control-user" disabled={false} value={selectUser.phoneNum} /> */}
              </div>
            }
            {!this.state.mode &&
              <div className="row">
                {/* 핸드폰 :<input className="ml-2 col-5 form-control form-control-user" onChange={e => this.onChange(e, 'phoneNum')} value={this.state.phoneNum} /> */}
                핸드폰 :<input value={this.state.phoneNum} onChange={e => this.validatePhoneNumber(e.target.value)} type="text" className="ml-2 col-5 form-control form-control-user" name="phoneNum" placeholder="핸드폰" />
                {this.state.isPhoneNumberValid &&
                  <label className="ml-3">올바른 형식입니다</label>
                }
              </div>
            }


            <hr></hr>

            <div className="row">
              {
                this.state.mode &&
                <div>
                  부서 : {currentCarrer && currentCarrer.dept}
                </div>
              }
              {!this.state.mode &&
                <div className="row">부서
                <select className="ml-2 col" value={this.state.dept} onChange={e => this.onChange(e, 'dept')}>
                    {
                      deptList && deptList.map(dept => {
                        return (
                          currentCarrer && currentCarrer.dept === dept.deptName ?
                            <option key={++i} value={currentCarrer && currentCarrer.dept}> {currentCarrer && currentCarrer.dept}</option> : <option key={++i} value={dept.deptName}>{dept.deptName}</option>
                        )
                      })
                    }
                  </select>
                </div>
              }
            </div>
            <hr></hr>

            <div className="row">
              {
                this.state.mode &&
                <div>
                  직책 : {currentCarrer && currentCarrer.posi}
                </div>
              }
              {!this.state.mode &&
                <div className="row">직책
                <select className="ml-2 col" value={this.state.posi} onChange={e => this.onChange(e, 'posi')}>
                    {
                      posiList && posiList.map(posi => {
                        return (
                          currentCarrer && currentCarrer.posi === posi.posiName ?
                            <option key={++j} value={currentCarrer && currentCarrer.posi}> {currentCarrer && currentCarrer.posi}</option> : <option key={++j} value={posi.posiName}>{posi.posiName}</option>
                        )
                      })
                    }
                  </select>
                </div>
              }
            </div>

            <hr></hr>
            {/* <div className="row">
              직책 : {currentCarrer && currentCarrer.posi}
            </div> */}
          </div>
        </div>
        <div>
          <MDBContainer className="row">
            {
              !this.state.mode ?
                <MDBBtn outline rounded size="sm" color="primary" onClick={() => this.toggle('mode', currentCarrer && currentCarrer.dept, currentCarrer && currentCarrer.posi)}><MDBIcon icon="user-cog" className="" />완료</MDBBtn>
                :
                <MDBBtn outline rounded size="sm" color="primary" onClick={() => this.toggle('mode', currentCarrer && currentCarrer.dept, currentCarrer && currentCarrer.posi)}><MDBIcon icon="user-cog" className="" />변경</MDBBtn>
            }
            {
              !this.state.mode &&
              <MDBBtn outline rounded size="sm" color="primary" onClick={() => this.toggle('cancel', currentCarrer && currentCarrer.dept, currentCarrer && currentCarrer.posi)}><MDBIcon icon="ban" className="" />취소</MDBBtn>
            }
            {/* 퇴사 메뉴 모달 */}
            {
              (authToken.authorities[0].authority === "ROLE_ADMIN" && selectUser.eid !== "0000") &&
              <div className="">
                {/* <MDBContainer> */}
                <MDBBtn outline rounded size="sm" color="danger" onClick={() => this.toggle('modal')}><MDBIcon icon="user-slash" className="" /> 퇴사</MDBBtn>
                {/* retireAPI */}
                {/* <MDBBtn outline rounded size="sm" color="danger" onClick={() => {
                if(!window.confirm("해당 사원을 삭제합니까?")) return;

                this.retireAPI();
              }}>
              <MDBIcon icon="user-slash" className="" /> 퇴사2</MDBBtn> */}
                <MDBModal isOpen={this.state.modal} toggle={this.toggle}>
                  <MDBModalHeader toggle={() => this.toggle('modal')}> 퇴사 </MDBModalHeader>
                  <MDBModalBody>
                    {userState.selectedUser.name} - 해당 사원을 삭제합니까?
                      </MDBModalBody>
                  <MDBModalFooter>
                    <MDBBtn color="secondary" onClick={() => this.toggle('modal')}>아니요 </MDBBtn>
                    <MDBBtn color="primary" onClick={() => this.retireAPI()}> 네, 그렇습니다 </MDBBtn>
                  </MDBModalFooter>
                </MDBModal>
                {/* </MDBContainer> */}
              </div>
            }

          </MDBContainer>
        </div>

        <div className="col"></div>
      </div>

      // <div class="card border-light  h-100 py-2" align="center" style={{background: '#F8F9FC'}}>
      //   <div className="card-body" style={{background: '#F8F9FC'}} >
      //     <div className="row no-gutters align-items-center">
      //       <div className="col mr-2">
      //         <div className="text-xl font-weight-bold    text-uppercase mb-1">이름</div>
      //         <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.name}</div>
      //       </div>
      //     </div>
      //   </div>

      //   <div className="card-body" style={{background: '#EBECEF'}}>
      //     <div className="row no-gutters align-items-center">
      //       <div className="col mr-2">
      //         <div className="text-xl font-weight-bold    text-uppercase mb-1">사번</div>
      //         <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.eid}</div>
      //       </div>
      //     </div>
      //   </div>

      //   <div className="card-body" style={{background: '#F8F9FC'}}>
      //     <div className="row no-gutters align-items-center" >
      //       <div className="col mr-3">
      //         <div className="text-xl font-weight-bold    text-uppercase mb-1">입사일</div>
      //         <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.joinDate}</div>
      //       </div>
      //     </div>
      //   </div>

      //   <div className="card-body" style={{background: '#EBECEF'}}>
      //     <div className="row no-gutters align-items-center">
      //       <div className="col mr-3">
      //         <div className="text-xl font-weight-bold    text-uppercase mb-1">생일</div>
      //         <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.birth}</div>
      //       </div>
      //     </div>
      //   </div>
      //   <div className="card-body" style={{background: '#F8F9FC'}}>
      //     <div className="row no-gutters align-items-center">
      //       <div className="col mr-3">
      //         <div className="text-xl font-weight-bold    text-uppercase mb-1">핸드폰</div>
      //         <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.phoneNum}</div>
      //       </div>
      //     </div>
      //   </div>



      // </div>

    );
  }
}

export default Member;