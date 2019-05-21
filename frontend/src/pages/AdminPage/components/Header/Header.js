import React, { Component } from "react";
import Modal from "react-awesome-modal"
import Member from "../Admin/Member"
import { MDBBtn, MDBIcon } from "mdbreact"

class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,        //프로필 모달
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

    render() {
        const { handleLogout, userInfo } = this.props;

        return (
            <nav className="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                {/* Sidebar Toggle (Topbar) */}
                <button id="sidebarToggleTop" className="btn btn-link rounded-circle mr-3">
                    <i className="fa fa-bars"></i>
                </button>
                <div style={{ width: '100%', textAlign: 'center', position: 'absolute' }}>
                    <img src={process.env.PUBLIC_URL + '/resources/img/nastech.png'} alt="main_logo" style={{
                        width: 100, height: 47,
                    }}
                        onClick={(e) => { window.location.href = "/"; }}
                    />
                </div>
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
                            {
                                userInfo.memberInfo.name === "관리자" && <a className="dropdown-item" href="/adminpage">
                                    <i className="fas fa-user-cog fa-sm fa-fw mr-2 text-gray-400"></i>
                                    AdminPage
                    </a>

                            }

                            <div className="dropdown-divider"></div>

                            <div className="">
                                <a onClick={handleLogout} href="/" className="dropdown-item">

                                    <i className="fas fa-sign-out-alt mr-2 text-gray-400"></i>
                                    Logout
                                </a>
                            </div>
                            {}


                        </div>
                    </li>
                </ul>
            </nav>
        );
    }

};
export default Header;