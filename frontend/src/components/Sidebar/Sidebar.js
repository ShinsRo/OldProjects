import React from "react";

const Sidebar = () => {
    return (
        <ul className="navbar-nav bg-gradient-darkblue sidebar sidebar-dark accordion" id="accordionSidebar">

            {/* Sidebar - Brand */}
            <a className="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div className="sidebar-brand-icon rotate-n-15">
                <i className="fas fa-laugh-wink"></i>
                </div>
                <div className="sidebar-brand-text mx-3">UPMUREPORT</div>
            </a>

            {/* Divider */}
            <hr className="sidebar-divider my-0"/>

            {/* Nav Item - Dashboard */}
            <li className="nav-item active">
                <a className="nav-link" href="index.html">
                <i className="fas fa-fw fa-tachometer-alt"></i>
                <span>DASHBOARD</span></a>
            </li>

            {/* Divider */}
            <hr className="sidebar-divider"/>

            {/* Heading */}
            <div className="sidebar-heading">
                부서명
            </div>

            {/* Nav Item - Pages Collapse Menu */}
            <li className="nav-item">
                <a className="nav-link collapsed" href="/" data-toggle="collapse" data-target="#collapse1" aria-expanded="true" aria-controls="collapseTwo">
                <i className="fas fa-fw fa-folder"></i>
                <span>대표이사</span>
                </a>
                <div id="collapse1" className="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div className="bg-white py-2 collapse-inner rounded">
                    <h6 className="collapse-header">구성 인원:</h6>
                    <a className="collapse-item" href="buttons.html">이성기</a>
                </div>
                </div>
            </li>

            {/* Nav Item - Pages Collapse Menu */}
            <li className="nav-item">
                <a className="nav-link collapsed" href="/" data-toggle="collapse" data-target="#collapse2" aria-expanded="true" aria-controls="collapsePages">
                <i className="fas fa-fw fa-folder"></i>
                <span>정산팀</span>
                </a>
                <div id="collapse2" className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
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
            </li>

            {/* Nav Item - Pages Collapse Menu */}
            <li className="nav-item">
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
            </li>
            {/* Divider */}
            <hr className="sidebar-divider d-none d-md-block"/>

            {/* Sidebar Toggler (Sidebar) */}
            <div className="text-center d-none d-md-inline">
                <button className="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
    );
};
export default Sidebar;