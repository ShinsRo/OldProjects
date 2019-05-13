import React, { Component } from "react";
import ProjectSideBarContainer from "./containers/ProjectSideBarContainer";
import HeaderContainer from "./containers/HeaderContainer";
import DetailContanier from './containers/DetailContanier';
import LogContainer from './containers/LogContainer';
import Pfile from "./components/Pfile/Pfile";
import MainLanding from './components/MainLanding';
import Modal from 'react-awesome-modal';
import { MDBBtn, MDBIcon } from 'mdbreact';
import Member from '../AdminPage/components/Admin/Member'
import store from '../../stores'
import ProfileModify from '../ProfilePage/components/Profile/ProfileModify'

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
    const { userState } = store.getState();
    const userInfo = userState.userInfo;

    if (mainContentViewLevel === 'detail') {
      mainContent = (
        <div className="row"  >
          <div className="col-6">
            <div className="card shadow mb-4" style={{ height: '850px' }}>
              <Pfile />
            </div>
          </div>
          <div className="col-6">
            <div className="row" style={{ height: '480px' }}>
              <DetailContanier reloadPage={this.reloadPage.bind(this)} />
            </div>
            <div className="row" style={{ height: '20px' }}></div>
            <div className="row" style={{ height: '350px' }}>
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
        <ProjectSideBarContainer
          setMainContent={this.setMainContent.bind(this)}
          mainContentViewLevel={this.state.mainContentViewLevel} />
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
            <Modal visible={this.state.visible} width="" height="" effect="fadeInLeft" onClickAway={() => this.closeModal('visible')}>
              <div >
                <Member selectUser={userInfo.memberInfo}></Member>
                <ProfileModify selectUser={userInfo.memberInfo} />
              </div>
              {/* <input type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible1')}></input> */}
              <MDBBtn rounded size="sm" className="" color="primary" onClick={() => this.closeModal('visible')}><MDBIcon icon="times" className="mr-2 justify-content-end" />close</MDBBtn>
            </Modal>
            <div className="container-fluid pb-0" style={{ height: '90%', maxHeight: '880px', overflow: 'hidden' }}>
              {mainContent}
            </div>
          </div>
        </div>
      </div>
    );
  }
};
export default MainPage;