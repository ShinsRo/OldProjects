import React from "react";

const logoStyle = {
}

const defaultProps = {
    users:['default']
};

const Sidebar = ({depts,select}) => {
    return (
        <ul className="navbar-nav bg-gradient-darkblue sidebar sidebar-dark accordion" id="accordionSidebar">
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
            <li className="nav-item active">
                <a className="nav-link" href="index.html">
                <i className="fas fa-fw fa-building"></i>
                <span>부서명</span></a>
            </li>

            {/* Nav Item - Pages Collapse Menu */}
            {
                Object.keys(depts).map((key, idx) => {
                    const usersInDept = depts[key];
                    return (
                        <li className="nav-item" key={idx}>
                            <a className="nav-link collapsed" href="/" data-toggle="collapse" data-target={"#collapse"+key} aria-expanded="true" aria-controls="collapsePages">
                                <i className="fas fa-fw fa-folder"></i>
                                <span>{key}</span>
                            </a>
                            <div id={"collapse"+key}  className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                            <div className="bg-white py-2 collapse-inner rounded">
                                <h6 className="collapse-header">구성 인원:</h6>
                                {
                                    usersInDept.map((user, idx)=> {
                                        return <div key={idx} className="collapse-item" onClick={() => select(user)} value={user}>{user.posi} {user.userName}</div>
                                    })
                                }
                            </div>
                            </div>
                        </li>
                    );
                })
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