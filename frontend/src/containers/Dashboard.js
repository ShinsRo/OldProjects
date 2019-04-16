import React, { Component } from "react";
import ProjPanelContainer from "./ProjPanelContainer";
import HeaderContainer from "./HeaderContainer";
import Pfile from "../components/Pfile/Pfile";

class Dashboard extends Component {
  
  render() {
    return (
      <div id="wrapper">
        <ProjPanelContainer/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history}/>
            {/* Page Content  */}
            <div className="container-fluid">
            <div className="row">
              <div className="col-xl-6">
              </div>
              <div className="col-xl-6">
                  <Pfile />
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