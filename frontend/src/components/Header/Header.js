import React from "react";

const Header = ({handleLogout, userInfo}) => {
    return (
        <nav className="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
        
            {/* Sidebar Toggle (Topbar) */}
            <button id="sidebarToggleTop" className="btn btn-link d-md-none rounded-circle mr-3">
                <i className="fa fa-bars"></i>
            </button>

            {/* Topbar Navbar */}
            <ul className="navbar-nav ml-auto">
                
                {/* Nav Item - User Information */}
                <li className="nav-item dropdown no-arrow">
                <a className="nav-link dropdown-toggle" href="/" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span className="mr-2 d-none d-lg-inline text-gray-600 small">안녕하세요, {userInfo.userName}님</span>
                </a>

                {/* Dropdown - User Information */}
                <div className="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                    <a className="dropdown-item" href="/">
                    <i className="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    Profile
                    </a>
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
};
export default Header;