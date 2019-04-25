import React, { Component } from 'react';
import axios from 'axios';

class Career extends Component {
  constructor(props) {
    super(props);
    
    this.state = { value: 'select', posi:''};
  }

  onChange(e) {
    this.setState({
      value: e.target.value
    })
  }
  handleChangeInput(e, target) {
    //인풋 값 변경
    console.log(e.target.value)
    this.setState({ [target]: e.target.value });
}
  
  changeCareerAPI() {
    const careerInfo={
      dept: this.state.value,
      posi: this.state.posi
    }

    return axios.post('http://localhost:8080/upmureport/api/career/modify',
    {
      mem:this.props.selectUser,
      newCar:careerInfo
    }
    )
  }

  

  render() {
    const {deptList}=this.props
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

      <div className="card shadow mb-4">

        <nav className="navbar navbar-expand navbar-light bg-light mb-4">
          <a className="navbar-brand" href="/">커리어</a>
          <ul className="navbar-nav ml-auto">
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle" href="/" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
                        </a>
              <div className="dropdown-menu dropdown-menu-right animated--fade-in" aria-labelledby="navbarDropdown">
                <a className="dropdown-item" href="/">Action</a>
                <a className="dropdown-item" href="/">Another action</a>
                <div className="dropdown-divider"></div>
                <a className="dropdown-item" href="/">Something else here</a>
              </div>
            </li>
          </ul>
        </nav>


        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">시작일</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{currentCarrer && currentCarrer.startDate}</div>
            </div>
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">부서</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800"> {currentCarrer && currentCarrer.dept}</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">
                        
                            <form action="">
                            <select value={this.state.value} onChange={this.onChange.bind(this)}>
                            {
                              deptList && deptList.map(dept => {
                                return (
                                <option value={dept}>{dept}</option>
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
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">직책</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800"> {currentCarrer && currentCarrer.posi}</div>
              <input type="text" value={this.state.posi} name="posi" onChange={e => this.handleChangeInput(e, 'posi')} ></input>
            </div>
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>

        <input type="button" value=" Change " name="dept" onClick={this.changeCareerAPI.bind(this)} class="btn btn-success btn-icon-split"></input>



      </div>

    );
  }
}

export default Career;