import React, { Component } from "react";
import Modal from "react-awesome-modal"
import Member from "../../../AdminPage/components/Admin/Member"
import { MDBBtn, MDBIcon } from "mdbreact"

class Header extends Component {
    constructor(props) {
        super(props);
        this.state = { 
        };
    }
    
    render() {
        const { handleLogout, userInfo ,openModal} = this.props;


        return (
            <nav className="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                {/* Sidebar Toggle (Topbar) */}
                <button id="sidebarToggleTop" className="btn btn-link btn-dark-2 rounded-circle mr-3" style={ {zIndex: 2} }>
                    <i className="fa fa-bars"></i>
                </button>
                {/* <div style={{ width: '100%', textAlign: 'center', position: 'absolute'}}>
                <img 
                    alt="main_logo" 
                    src={process.env.PUBLIC_URL + '/resources/img/nastech.png'} 
                    style={{ width: 100, height: 47, cursor: 'pointer', }} 
                    onClick={(e) => {  window.location.href="/"; }}
                ></img>
            </div> */}
                {/* Topbar Navbar */}
                <ul className="navbar-nav ml-auto">
                    {/* Nav Item - User Information */}
                    <li className="nav-item dropdown no-arrow">
                        <a className="nav-link dropdown-toggle" href="/" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span className="mr-2 d-none d-lg-inline text-gray-800">안녕하세요, {userInfo.memberInfo.name}님</span>
                        </a>

                        {/* Dropdown - User Information */}
                        <div className="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                            <a className="dropdown-item" href="/">
                                <i className="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                HOME
                        </a>
                            {/* 
                            <a className="dropdown-item" href="/profile">
                                <i className="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                Profile
                            </a> */}

                            {/* 프로필 메뉴 */}
                            {/* <input type="button" className="btn btn-primary btn-icon-split ml-3" value="   부서 및 직책 관리   " onClick={() => this.openModal('visible1')} /> */}
                            
                            <MDBBtn size="sm" className="ml-3 text-dark-2" color="" onClick={() => openModal('visible')}><MDBIcon icon="user" className="mr-2 text-gray-400" />Profile</MDBBtn>
                            
                            
                            {
                                userInfo.memberInfo.name === "관리자" && <a className="dropdown-item" href="/adminpage">
                                    <i className="fas fa-user-cog fa-sm fa-fw mr-2 text-gray-400"></i>
                                    AdminPage
                                </a>
                            }

                            <div className="dropdown-divider"></div>

                            <div className="text-center">
                                <a onClick={handleLogout} href="/" className="btn btn-danger btn-icon-split ml-1 mr-1">
                                    <span className="icon text-white-50">
                                        <i className="fas fa-sign-out-alt"></i>
                                    </span>
                                    <span className="text">로그아웃</span>
                                </a>
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        );
    }
};
export default Header;