import React, { Component } from 'react';
import axios from 'axios';
import { BASE_URL } from '../../../../supports/API_CONSTANT';
import { MDBBtn, MDBIcon } from 'mdbreact'

class Career extends Component {
  constructor(props) {
    super(props);

    this.state = { value: '', posi: '' };
  }

  onChange(e, target) {
    this.setState({
      [target]: e.target.value
    })
  }
  handleChangeInput(e, target) {
    //인풋 값 변경
    // console.log(e.target.value)
    this.setState({ [target]: e.target.value });
  }

  changeCareerAPI() {
    const careerInfo = {
      dept: this.state.value,
      posi: this.state.posi
    }
    if (careerInfo.dept === "" | careerInfo.posi === "") {
      return alert("변경할 커리어를 모두 선택하세요")
    }

    return axios.post(`${BASE_URL}/api/career/modify`,
      {
        mem: this.props.selectUser,
        newCar: careerInfo
      }
    ).then(
      (response) => {
        if (!response.data) alert("잘못 된 요청입니다")
        else {
          alert("부서:" + response.data.dept + "\n직책:" + response.data.posi + "\n변경 되었습니다")
          window.location.href = "/adminpage";
        }
      }

    )
  }



  render() {
    const { deptList } = this.props
    const { posiList } = this.props
    let currentCarrer;
    let pastCareer = [];
    const selectUser1 = this.props.selectUser;
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

      <div className="card" align="">
        <h className="card-header font-weight-bold text-black">{selectUser1.name}님 커리어 </h>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">시작일</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{currentCarrer && currentCarrer.startDate}</div>

              <div className="text-xs font-weight-bold text-primary text-uppercase mt-3 mb-1">부서</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800"> {currentCarrer && currentCarrer.dept}</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">
                <form action="">
                  <select value={this.state.value} onChange={e => this.onChange(e, 'value')}>
                    {
                      deptList && deptList.map(dept => {
                        return (
                          <option value={dept.deptName}>{dept.deptName}</option>
                        )
                      })
                    }
                  </select>
                </form>
              </div>

              <div className="text-xs font-weight-bold text-primary text-uppercase mt-3 mb-1">직책</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800"> {currentCarrer && currentCarrer.posi}</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">

                <form action="">
                  <select value={this.state.posi} onChange={e => this.onChange(e,'posi')}>
                    {
                      posiList && posiList.map(posi => {
                        return (
                          <option value={posi.posiName}>{posi.posiName}</option>
                        )
                      })
                    }
                    {/* <option value="grapefruit">{currentCarrer&& currentCarrer.dept}</option>
                                <option value="lime">Lime</option>
                                <option value="coconut">Coconut</option>
                                <option value="mango">Mango</option> */}
                  </select>
                </form>
              </div>
              
            </div>
          </div>
        </div>
        {/* <input type="button" value=" Change " name="dept" onClick={this.changeCareerAPI.bind(this)} class="btn btn-primary btn-icon-split"></input> */}
        <MDBBtn outline rounded size="sm" className="" color="primary" onClick={this.changeCareerAPI.bind(this)}><MDBIcon icon="check" className="" />  변경  </MDBBtn>
        {/* <div className="card-body">  직접 입력 버전
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">직책</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800"> {currentCarrer && currentCarrer.posi}</div>
              <input type="text" value={this.state.posi} name="posi" onChange={e => this.handleChangeInput(e, 'posi')} ></input>
            </div>
          </div>
        </div> */}
      </div>

    );
  }
}

export default Career;