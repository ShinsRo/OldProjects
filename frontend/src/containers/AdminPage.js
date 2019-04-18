import React, { Component } from "react";
import ProjectSideBarContainer from "./ProjectSideBarContainer";
import HeaderContainer from "./HeaderContainer";
import SidebarContainer from "./SidebarContainer"
import Pfile from "../components/Pfile/Pfile";

class AdminPage extends Component {
    
  render() {
    return (
      <div id="wrapper">
        <SidebarContainer/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history}/>
            {/* Page Content  */}
            임시 테스트 
            <div className="container-fluid">
            <div className="row">
              <div className="col-xl-6">
                왼쪽
              </div>
              <div className="col-xl-6">
                  오른쪽 
                </div>
                </div>
            </div>

          </div>

        </div>
      </div>
    );
  }
};
export default AdminPage;