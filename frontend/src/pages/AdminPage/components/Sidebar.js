import React, { Component } from "react";
import Modal from 'react-awesome-modal'
import Register from '../../RegisterPage/index'
import Dept from '../components/Admin/Dept'
import Posi from '../components/Admin/Posi'
const defaultProps = {
    users: ['default']
};

class Sidebar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false
        };
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
    //const Sidebar = ({depts,select,userInfo}) => {
    render() {
        const { depts } = this.props;
        const { select } = this.props;
        const { userInfo } = this.props;

        return (
            <ul className="navbar-nav bg-gradient-darkblue sidebar bg-dark-1 accordion" id="accordionSidebar">
                {/* rotate-n-15 */}
                {/* Sidebar - Brand */}
                {/* <a className="sidebar-brand d-flex align-items-center justify-content-center" href="/">
                <div className="sidebar-brand-icon">
                </div>
                
                <div className="sidebar-brand-text mx-3">
                </div>
            </a> */}
                {/* Divider */}
                <hr className="sidebar-divider" />

                {/* Heading */}
                <li className="nav-item active text-white">
                    <div className="nav-link" href="index.html">
                        <i className="fas fa-fw fa-building"></i>
                        <span>부서명</span></div>
                </li>

                <li className="nav-item active">
                    <i className="fas fa-user"></i>
                    <span className="collapse-item" onClick={select(userInfo)} value={userInfo}> My: {userInfo.name}</span>
                </li>
                {/* Divider */}


                {/* Nav Item - Pages Collapse Menu */}
                {
                    Object.keys(depts).map((key, idx) => {
                        const usersInDept = depts[key];
                        return (
                            <li className="nav-item" key={idx}>
                                <a className="nav-link collapsed text-white" href="/" data-toggle="collapse" data-target={"#collapse" + key} aria-expanded="true" aria-controls="collapsePages">
                                    <i className="fas fa-fw fa-landmark"></i>
                                    <span>{key}</span>
                                </a>
                                <div id={"collapse" + key} className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                                    <div className="bg-white py-2 collapse-inner rounded">
                                        <h6 className="collapse-header">구성 인원:</h6>
                                        {
                                            usersInDept.map((user, idx) => {
                                                //현재 커리어가져오기위함
                                                let currentCareer = user.career.filter(car => car.active === true)
                                                //console.log("현재 컬어",currentCareer[0].posi)
                                                return <div key={idx} className="collapse-item" onClick={() => select(user)} value={user}> {currentCareer[0].posi} {user.name}</div>
                                            })
                                        }
                                    </div>
                                </div>
                            </li>
                        );
                    })
                }

                {/* <input type="button" className="btn btn-primary btn-icon-split mb-3 mt-3" value="  신규 사원 등록   " onClick={() => this.openModal('visible')} />
                <Modal visible={this.state.visible} width="900px" height="700px" effect="fadeInLeft" onClickAway={() => this.closeModal('visible')}>
                  <div >
                    <Register/>
                    <input type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible')}></input>
                  </div>
                </Modal>
                
                <input type="button" className="btn btn-primary btn-icon-split mb-3" value="   부서 및 직책 관리   " onClick={() => this.openModal('visible1')} />
                <Modal visible={this.state.visible1} width="400" height="300" effect="fadeInLeft" onClickAway={() => this.closeModal('visible1')}>
                  <div>
                  <Dept deptList={deptList}></Dept>
                    <Posi posiList={posiList}></Posi>
                  </div>
                  <input type="button" value=" Close " className="btn btn-primary btn-icon-split" onClick={() => this.closeModal('visible1')}></input>
                </Modal> */}
                        
                {/* Divider */}
                <hr className="sidebar-divider d-none d-md-block" />

                {/* Sidebar Toggler (Sidebar) */}
                <div className="text-center d-none d-md-inline">
                    <button className="rounded-circle border-0" id="sidebarToggle"></button>
                </div>

            </ul>
        );
    }
};
Sidebar.defaultProps = defaultProps;
export default Sidebar;