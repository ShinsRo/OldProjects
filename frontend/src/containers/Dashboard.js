import React, { Component } from "react";
import { Header, Sidebar } from "../components";

class Dashboard extends Component {
  render() {
    
    return (
      <div id="wrapper">
        <Sidebar/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <Header/>
            {/* Page Content  */}
            <div className="container-fluid">
            </div>

          </div>

        </div>
      </div>
    );
  }
};
export default Dashboard;