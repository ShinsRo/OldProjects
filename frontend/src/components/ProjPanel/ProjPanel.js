import React from 'react';

import ProjTreeView from './ProjTreeView';

class ProjPanel extends React.Component {
    render() {
        console.log("Rendering: ProjPanel");
        const { projectState, handleDirItemClick } = this.props;
        
        const errObj = projectState.get('errObj');
        if (!projectState || !errObj.get('isHandled')) return (<></>);
        
        return (
            <ul className="navbar-nav bg-gradient-darkblue sidebar sidebar-dark accordion" id="accordionSidebar">
            {/* rotate-n-15 */}
            {/* Sidebar - Brand */}
            <a className="sidebar-brand d-flex align-items-center justify-content-center" href="/">
                <div className="sidebar-brand-icon">
                </div>
                
                <div className="sidebar-brand-text mx-3">
                </div>
            </a>
            {/* Divider */}
            <hr className="sidebar-divider"/>

            {/* Heading */}
            {/* <li className="nav-item active">
                <div className="nav-link" href="index.html">
                    <i className="fas fa-fw fa-building"></i>
                    <span>부서명</span>
                </div>
            </li> */}
            
            <ProjTreeView projectState={projectState} handleDirItemClick={handleDirItemClick}/>
            {/* Divider */}

            {/* Divider */}
            <hr className="sidebar-divider d-none d-md-block"/>

            {/* Sidebar Toggler (Sidebar) */}
            <div className="text-center d-none d-md-inline">
                <button className="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        );
    }
}
export default ProjPanel;