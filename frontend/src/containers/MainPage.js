import React, { Component } from "react";
import ProjectSideBarContainer from "./ProjectSideBarContainer";
import HeaderContainer from "./HeaderContainer";
import DetailContanier from './DetailContanier';
import Pfile from "../components/Pfile/Pfile";
import MainLanding from './MainLanding';

class MainPage extends Component {
  
  constructor(props) {
    super(props);
    this.state = {
      mainContentViewLevel: 'default',
    }
  }

  setMainContent(to) {
    this.setState({ mainContentViewLevel: to });
  }

  render() {
    const {mainContentViewLevel} = this.state;
    let mainContent;

    if (mainContentViewLevel === 'detail') {
      mainContent = (
        <div className="row"                  style={{ height: '100%' }}>
          <div className="col-xl-6">
            <div className="card shadow mb-4" style={{ height: '100%' }}>      
              <Pfile />
            </div>
          </div>
          <div className="col-xl-6">
            <div className="card shadow mb-4" style={{ height: '100%' }}>
              <DetailContanier />
            </div>
          </div>
        </div>
      );
    } else if (mainContentViewLevel === 'dashboard') {
      mainContent = (<></>);
    } else if (mainContentViewLevel === 'default') {
      mainContent = (<MainLanding/>);
    } else {
      mainContent = (<></>);
    }

    return (
      <div id="wrapper">
        <ProjectSideBarContainer 
          setMainContent={ this.setMainContent.bind(this) } 
          mainContentViewLevel={ this.state.mainContentViewLevel }/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            {/* Page Content  */}
            <HeaderContainer history={this.props.history}/>
            <div className="container-fluid pb-4"   style={{ height: '90%' }}>
              {mainContent}
            </div>
          </div>
        </div>
      </div>
    );
  }
};
export default MainPage;