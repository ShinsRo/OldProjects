import React, { Component } from "react";
import ProjectSideBarContainer from "./ProjectSideBarContainer";
import HeaderContainer from "./HeaderContainer";
import DetailContanier from './DetailContanier';
import Pfile from "../components/Pfile/Pfile";

class Dashboard extends Component {
  
  render() {
    return (
      <div id="wrapper">
        <ProjectSideBarContainer/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history}/>
            {/* Page Content  */}
            <div className="container-fluid">
            <div className="row">
              <div className="col-xl-6">
                  <Pfile />
              </div>
              <div className="col-xl-6">
                <div className="card shadow mb-4">
                  <DetailContanier />
                </div>
                </div>
                </div>
            </div>

          </div>

        </div>
      </div>
    );
  }
};
export default Dashboard;