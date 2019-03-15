import React, { Component } from "react";
import { Header } from "../components/Header";
import { Sidebar } from "../components/Sidebar";

import * as projectApi from '../api/project-api';

class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projects: []
    }
  }

  componentDidMount() {
    projectApi.getAll().then((response) => {
      this.setState({ projects: response.data });
    });
  }

  render() {
    const { projects } = this.state;
    console.log(projects);
    
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
              {
                this.state.projects.map((project) => {
                  return (
                    <div key={project.projId}>
                      {project.projName} : {project.projCaleGubun} <br/>
                      
                      {project.projStartDate.year}. 
                      {project.projStartDate.monthValue}.
                      {project.projStartDate.dayOfMonth}
                      <hr/>
                    </div>
                  );
                })
              }

            </div>

          </div>

        </div>
      </div>
    );
  }
};
export default Dashboard;