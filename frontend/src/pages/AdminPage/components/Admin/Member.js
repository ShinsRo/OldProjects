import React, { Component } from 'react';



class Member extends Component {

  render() {
    return (
      <div class="card border-left-success shadow h-100 py-2">
        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xl font-weight-bold text-primary text-uppercase mb-1">이름</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.name}</div>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-2">
              <div className="text-xl font-weight-bold text-primary text-uppercase mb-1">사번</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.eid}</div>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-3">
              <div className="text-xl font-weight-bold text-primary text-uppercase mb-1">입사일</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.joinDate}</div>
            </div>
          </div>
        </div>

        <div className="card-body">
          <div className="row no-gutters align-items-center">
            <div className="col mr-3">
              <div className="text-xl font-weight-bold text-primary text-uppercase mb-1">생일</div>
              <div className="h5 mb-0 font-weight-bold text-gray-800">{this.props.selectUser.birth}</div>
            </div>
            
          </div>
        </div>

      </div>

    );
  }
}

export default Member;