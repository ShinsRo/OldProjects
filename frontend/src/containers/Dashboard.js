import React, { Component } from "react";
import { Header } from "../components/Header";
import { Sidebar } from "../components/Sidebar";

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