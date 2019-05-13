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
            case "handleDirItemClick":  // (selectedDirId)
                const { selectedDirId, selectedProject } = params;

                if(projectState.get('selectedDirId') && projectState.get('selectedDirId') !== selectedDirId){
                    PfileActions.getPfile(selectedDirId);
                    AttachmentAction.getAttachment(selectedDirId);
                }

                
                if(selectedProject.isOrigin) {
                    PLogActions.getPLog(selectedProject.pid);
                }

                if(this.props.mainContentViewLevel === 'default') {
                    this.props.setMainContent('detail');
                }

                ProjectActions.saveItem({ selectedDirId, detailViewLevel: 'project' });        
                break;
            default:
                break;
        }
    }

    render() {
        const { projectState } = store.getState();
        
        const { ProjectActions } = this.props;
        
        return (<>
            <ProjectSideBar 
                projectState={projectState} 
                ProjectActions={ProjectActions}
                handlers={this.handlers}
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