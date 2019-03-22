import React, { Component } from "react";
import { Sidebar } from "../components";
import ProjPanelContainer from "./ProjPanelContainer";
import HeaderContainer from "./HeaderContainer";

class Dashboard extends Component {
  render() {
    return (
      <div id="wrapper">
        <Sidebar/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <HeaderContainer history={this.props.history}/>
            {/* Page Content  */}
            <div className="container-fluid">
              <div className="col-lg-6">
                <ProjPanelContainer></ProjPanelContainer>
              </div>
            </div>

          </div>

        </div>
      </div>
    );
  }
};
export default Dashboard;