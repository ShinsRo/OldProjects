import React, { Component } from 'react';



class Member extends Component {

  render() {
    return (
      <div className="card shadow mb-4">
        <nav className="navbar navbar-expand navbar-light bg-light mb-4">
          <a className="navbar-brand" href="/">정보</a>
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
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">Name</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.name}</div>
            </div>
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>
        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">사번</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.eid}</div>
            </div>
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">입사일</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.joinDate}</div>
            </div>
            <div className="col-auto">
              <i className="fas fa-calendar fa-2x text-gray-300"></i>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">생일</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.birth}</div>
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

export default Member;