import React from "react";

const defaultProps = {
    users:['default']
};

const Sidebar = ({depts,select,userInfo}) => {
    
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
            <hr className="sidebar-divider"/>

            {/* Heading */}
            <li className="nav-item active text-white">
                <div className="nav-link">
                <i className="fas fa-id-card ml-5"></i>
                <span>Profile</span></div>
            </li>
            <li className="nav-item active">
            <i className="fas fa-user fa-2x ml-3"></i>
                <input type="button" class="btn btn-success btn-icon-split ml-3" value="   내 정보   "/>
            </li>
            {/* Divider */}


            {/* Nav Item - Pages Collapse Menu */}
            {
              
            }
            {/* Divider */}
            <hr className="sidebar-divider d-none d-md-block"/>

            {/* Sidebar Toggler (Sidebar) */}
            <div className="text-center d-none d-md-inline">
                <button className="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
    );
};
Sidebar.defaultProps = defaultProps;
export default Sidebar;