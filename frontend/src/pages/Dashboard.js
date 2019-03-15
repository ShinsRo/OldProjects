import React from "react";
import { Header } from "../components/Header";
import { Sidebar } from "../components/Sidebar";
const Dashboard = () => {
    return (
      <div id="wrapper">
        <Sidebar/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            <Header/>
          </div>

        </div>
      </div>
    );
};
export default Dashboard;