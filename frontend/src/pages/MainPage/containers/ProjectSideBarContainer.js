/** 
 * 프로젝트 패널 사이드 바 컨테이너 정의
 * 
 * 2019.05.22
 * @file ProjectSideBarContainer 정의
 * @author 김승신, 김윤상
 */

import React, { Component } from 'react';

import store from '../../../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../../../stores/modules/projectState';
import * as pfileAction from '../../../stores/modules/pfileState'
import * as attachmentAction from '../../../stores/modules/attachmentState'
import * as plogActions from '../../../stores/modules/plogState';


import { ProjectSideBar } from '../components/ProjectSideBar';


class ProjectSideBarContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            reload: false,
        };
        this.handlers = this.handlers.bind(this);
    }
    componentDidMount() {
        const { userInfo } = store.getState().userState;
        const memberInfo = userInfo.memberInfo;
        
        const { ProjectActions } = this.props;
        ProjectActions.list(memberInfo.mid, memberInfo.name);
        ProjectActions.setMemberAutocompletor();
    }

    handlers(cmd, params) {
        const { ProjectActions, PfileActions, AttachmentAction, projectState, PLogActions } = this.props;
        switch (cmd) {
            case "reload":
                this.setState({ reload: !this.state.reload });
                break;
            case "handleDirItemClick":  // (selectedDirId, selectedProject)
                const { selectedDirId, selectedProject } = params;

                if(projectState.get('selectedDirId') && projectState.get('selectedDirId') !== selectedDirId){
                    PfileActions.getPfile(selectedDirId);
                    AttachmentAction.getAttachment(selectedDirId);
                }

                
                if(selectedProject.isOrigin) {
                    PLogActions.getPLog(selectedProject.pid);
                    PLogActions.setProject(selectedProject.pid);
                }

                if(this.props.mainContentViewLevel === 'default') {
                    this.props.setMainContent('detail');
                }

                ProjectActions.saveItem({ selectedProject, selectedDirId, detailViewLevel: 'project' });        
                break;
            default:
                break;
        }
    }

    render() {
        const { projectState } = store.getState();
        
        const { ProjectActions, PfileActions, AttachmentAction, PLogActions } = this.props;
        
        return (<>
            <ProjectSideBar 
                projectState={projectState} 
                ProjectActions={ProjectActions}
                handlers={this.handlers}
                movePfile = {PfileActions.movePfile}
                copyPfile = {PfileActions.copyPfile}
                moveAttachment = {AttachmentAction.moveAttachment}
                copyAttachment = {AttachmentAction.copyAttachment}
                getPfile = {PfileActions.getPfile}
                getAttachment = {AttachmentAction.getAttachment}
                getPLog = {PLogActions.getPLog}
            ></ProjectSideBar>
        </>);
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        userState: state.userState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
    PfileActions: bindActionCreators(pfileAction, dispatch),
        AttachmentAction: bindActionCreators(attachmentAction, dispatch),
        PLogActions : bindActionCreators(plogActions, dispatch),
    })
) (ProjectSideBarContainer);
