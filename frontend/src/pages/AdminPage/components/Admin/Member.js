import React, { Component } from 'react';
import { select } from 'glamor';



class Member extends Component {

  render() {
    const { selectUser } = this.props;
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
      <div className="card font-weight-bold ">
        <div className="card-header font-weight-bold text-black">
          인원 상세 정보
        </div>
        <div align="center">
          <i className="fas fa-id-card" style={{ fontSize: "150px" }} ></i>
        </div>
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
            <div className="row">
              핸드폰 : {selectUser.phoneNum}
            </div>
            <hr></hr>
            <div className="row">
              부서 : {currentCarrer && currentCarrer.dept}
            </div>
            <hr></hr>
            <div className="row">
              직책 : {currentCarrer && currentCarrer.posi}
            </div>
          </div>
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