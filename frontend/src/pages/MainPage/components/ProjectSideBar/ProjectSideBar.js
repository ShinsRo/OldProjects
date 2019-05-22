/** 
 * 프로젝트 트리 뷰를 담을 사이드 바
 * 
 * 2019.05.22
 * @file ProjectSideBar 정의
 * @author 김승신
 */

import React from 'react';

import ProjTreeView from './ProjTreeView';

class ProjectSideBar extends React.Component {
    render() {
        const { projectState, handlers } = this.props;
        
        const errObj = projectState.get('errObj');
        if (!projectState || !errObj.get('isHandled')) return (<></>);
        
        return (
        <ul className="navbar-nav bg-gradient-dark-2 sidebar sidebar-dark accordion" id="accordionSidebar">
            {/* rotate-n-15 */}
            {/* Sidebar - Brand */}
            <a className="sidebar-brand d-flex align-items-center justify-content-center" href="/">
                <div className="sidebar-brand-icon">
                </div>
                
                <div className="sidebar-brand-text mx-3">
                </div>
            </a>
            
            <ProjTreeView 
                projectState={projectState} 
                handlers={handlers}
                movePfile = {this.props.movePfile}
                copyPfile = {this.props.copyPfile}
                moveAttachment = {this.props.moveAttachment}
                copyAttachment = {this.props.copyAttachment}
                getPfile = {this.props.getPfile}
                getAttachment = {this.props.getAttachment}
                getPLog = {this.props.getPLog}
                />
        </ul>
        );
    }
}
export default ProjectSideBar;