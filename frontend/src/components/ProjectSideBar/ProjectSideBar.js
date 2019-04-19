import React from 'react';

import ProjTreeView from './ProjTreeView';

class ProjectSideBar extends React.Component {
    render() {
        console.log("Rendering: ProjectSideBar");
        const { projectState, handlers } = this.props;
        
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
            
            <ProjTreeView projectState={projectState} handlers={handlers}/>

            {/* Sidebar Toggler (Sidebar) */}
            <div className="text-center d-none d-md-inline">
                <button className="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        );
    }
}
export default ProjectSideBar;