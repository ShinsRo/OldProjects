import React, { Component } from "react";
import ProjectSideBarContainer from "./containers/ProjectSideBarContainer";
import HeaderContainer from "./containers/HeaderContainer";
import DetailContanier from './containers/DetailContanier';
import LogContainer from './containers/LogContainer';
import Pfile from "./components/Pfile/Pfile";
import MainLanding from './components/MainLanding';

class MainPage extends Component {
  
  constructor(props) {
    super(props);
    this.state = {
      isSynced: false,
      mainContentViewLevel: 'default',
    }
  }

  setMainContent(to) {
    this.setState({ mainContentViewLevel: to });
  }

  syncIt() {
    this.setState({ isSynced: true });
    window.location.href = '/';
  }

  render() {
    if (!this.state.isSynced) { }
    const {mainContentViewLevel} = this.state;
    let mainContent;

    if (mainContentViewLevel === 'detail') {
      mainContent = (
        <div className="row"                  style={{ height: '100%' }}>
          <div className="col-6">
            <div className="card shadow mb-4" style={{ height: '100%' }}>      
              <Pfile />
            </div>
          </div>
          <div className="col-6">
            <div className="row"  style={{ height: '61.5%' }}>
              <DetailContanier />
            </div>
            <div className="row"  style={{ height: '2%' }}></div>
            <div className="row"  style={{ height: '36.5%' }}>
              <LogContainer />
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