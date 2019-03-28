import React from "react";

const defaultProps = {
    users:['default']
};

const Sidebar = ({depts,select}) => {
    return (
        <ul className="navbar-nav bg-gradient-darkblue sidebar sidebar-dark accordion" id="accordionSidebar">
            {/* Sidebar - Brand */}
            <a className="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div className="sidebar-brand-icon rotate-n-15">
                {/* <i className="fas fa-laugh-wink"></i> */}
                </div>
                <div className="sidebar-brand-text mx-3">UPMUREPORT</div>
            </a>

            {/* Divider */}
            <hr className="sidebar-divider"/>

            {/* Heading */}
            <li className="nav-item active">
                <a className="nav-link" href="index.html">
                <i className="fas fa-fw fa-building"></i>
                <span>부서명</span></a>
            </li>
            {/* <div className="sidebar-heading">
            </div> */}

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

            {/* {
                users.map((user, key) => {
                    return (
                            <div id={"collapse"+user.dept}  className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                            <div className="bg-white py-2 collapse-inner rounded">
                                <h6 className="collapse-header">구성 인원:</h6>
                                <a className="collapse-item" href="buttons.html">{user.userName}</a>
                            </div>
                            </div>      
                    );
                })
            } */}
        

            {/*
                users.map((user,idx) => {
                    return (
                        
                        <div id={"collapse"+idx}  className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                            {
                                () => {
                                    if (true) {
                                        return 'true';
                                    } else {
                                        return 'false';
                                    }
                                }
                            }
                            <div className="bg-white py-2 collapse-inner rounded">
                                <h6 className="collapse-header">구성 인원:</h6>
                                <a className="collapse-item" href="buttons.html">차장 배인자</a>
                                <a className="collapse-item" href="buttons.html">대리 김미화</a>
                                <a className="collapse-item" href="buttons.html">사원 곽선희</a>
                                <a className="collapse-item" href="buttons.html">사원 장선영</a>
                                <a className="collapse-item" href="buttons.html">사원 이연주</a>
                                <a className="collapse-item" href="buttons.html">사원 이수연</a>
                                <a className="collapse-item" href="buttons.html">사원 조명희</a>
                                
                            </div>
                            </div>
                    );             
                }) 
            */}

            {/* Nav Item - Pages Collapse Menu */}
            {/* <li className="nav-item">
                <a className="nav-link collapsed" href="/" data-toggle="collapse" data-target="#collapse3" aria-expanded="true" aria-controls="collapsePages">
                <i className="fas fa-fw fa-folder"></i>
                <span>운영팀</span>
                </a>
                <div id="collapse3" className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                <div className="bg-white py-2 collapse-inner rounded">
                    <h6 className="collapse-header">구성 인원:</h6>
                    <a className="collapse-item" href="buttons.html">부장 서동환</a>
                    <a className="collapse-item" href="buttons.html">대리 김태영</a>
                    <a className="collapse-item" href="buttons.html">과장 곽창섭</a>
                    <a className="collapse-item" href="buttons.html">대리 김수준</a>
                    <a className="collapse-item" href="buttons.html">사원 박송이</a>
                    <a className="collapse-item" href="buttons.html">사원 김대열</a>
                </div>
                </div>
            </li> */}
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