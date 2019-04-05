import React, { Component } from "react";
import ProjPanelContainer from "./ProjPanelContainer";
import HeaderContainer from "./HeaderContainer";
import SidebarContainer from "./SidebarContainer"
import Upmu from "../components/UpmuContent/Upmu";

class Dashboard extends Component {
  
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
            <div className="container-fluid">
            <div className="row">
              <div className="col-xl-6">
                <ProjPanelContainer></ProjPanelContainer>
              </div>
              <div className="col-xl-6">
                  <Upmu />
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