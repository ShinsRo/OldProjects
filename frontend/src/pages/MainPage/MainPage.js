/** 
 * 프로젝트/디렉토리/업무 관리 페이지 정의
 * 
 * 2019.05.22
 * @file MainPage 정의
 * @author 김승신, 김윤상
 */

import React, { Component } from "react";
import ProjectSideBarContainer from "./containers/ProjectSideBarContainer";
import HeaderContainer from "./containers/HeaderContainer";
import DetailContanier from './containers/DetailContanier';
import LogContainer from './containers/LogContainer';
import Pfile from "./components/Pfile/Pfile";
import MainLanding from './components/MainLanding';
import { ProfileModal } from './components/ProfileModal';
import Toast from "./components/Toast";

class MainPage extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isSynced: false,
      reload: 0,
      mainContentViewLevel: 'default',
      visible: false,        //프로필 모달
    }

    
  }
  openModal(target) {
    this.setState({
      [target]: true
    });
  }

  closeModal(target) {
    this.setState({
      [target]: false
    });
  }

  setMainContent(to) {
    this.setState({ mainContentViewLevel: to });
  }

  reloadPage() {
    this.setState({ reload: ~this.state.reload });
  }

  syncIt() {
    this.setState({ isSynced: true });
    window.location.href = '/';
  }

  render() {
    if (!this.state.isSynced) { }
    const { mainContentViewLevel } = this.state;
    let mainContent;

    if (mainContentViewLevel === 'detail') {
      mainContent = (
        <div className="row"  >
          <div className="col-6">
            <div className="card shadow mb-4" style={{ height: '840px' }}>
              <Pfile />
            </div>
          </div>
          <div className="col-6">
            <div className="row" style={{ height: '480px' }}>
              <DetailContanier reloadPage={this.reloadPage.bind(this)} />
            </div>
            <div className="row" style={{ height: '20px' }}></div>
            <div className="row" style={{ height: '340px' }}>
              <LogContainer />
            </div>
          </div>
        </div>
      );
    } else if (mainContentViewLevel === 'dashboard') {
      mainContent = (<></>);
    } else if (mainContentViewLevel === 'default') {
      mainContent = (<MainLanding />);
    } else {
      mainContent = (<></>);
    }

    return (
      <div id="wrapper">
        <Toast/>
        <ProjectSideBarContainer 
          setMainContent={ this.setMainContent.bind(this) } 
          mainContentViewLevel={ this.state.mainContentViewLevel }/>
        {/* Content Wrapper */}
        <div id="content-wrapper" className="d-flex flex-column">

          {/* Main Content */}
          <div id="content">
            {/* Page Content  */}
            <HeaderContainer history={this.props.history}
              openModal={this.openModal.bind(this)}
              closeModal={this.closeModal.bind(this)}
              visible={this.state.visible}
            />
            <div className="container-fluid pb-0" style={{ height: '90%', maxHeight: '840px', overflow: 'hidden' }}>
              {mainContent}
            </div>
          </div>
        </div>
        <ProfileModal visible={this.state.visible} closeModal={this.closeModal.bind(this)}/>
      </div>
    );
  }
};
export default MainPage;