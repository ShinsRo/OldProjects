import React, { Component, PropTypes } from 'react';



class Career extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    let currentCarrer;
    let pastCareer = [];
    console.log("멤버쪽", this.props.selectUser)
    const selectUser1 = this.props.selectUser
    console.log("체크해", selectUser1.career)
    const careers = selectUser1.career
    console.log("ㅌ,르루루", careers)
    // 있으면 //
    if (careers) {
      careers.forEach(car => {
        if (car.active == true) {
          console.log("현재 커리어", car)
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
          <a className="navbar-brand" href="#">커리어</a>
          <ul className="navbar-nav ml-auto">
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
                        </a>
              <div className="dropdown-menu dropdown-menu-right animated--fade-in" aria-labelledby="navbarDropdown">
                <a className="dropdown-item" href="#">Action</a>
                <a className="dropdown-item" href="#">Another action</a>
                <div className="dropdown-divider"></div>
                <a className="dropdown-item" href="#">Something else here</a>
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
            </div>
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>


      </div>

    );
  }
}

export default Career;