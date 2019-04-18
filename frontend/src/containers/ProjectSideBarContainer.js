import React, { Component } from 'react';

import store from '../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';
import * as pfileAction from '../stores/modules/pfileState'


import { ProjectSideBar } from '../components/ProjectSideBar';


class ProjectSideBarContainer extends Component {
    constructor(props) {
        super(props);
        this.handleDirItemClick = this.handleDirItemClick.bind(this);
    }
    componentDidMount() {
        const { selectedUser } = store.getState().userState;
        const { ProjectActions } = this.props;
        ProjectActions.list(selectedUser.mid, selectedUser.name);
    }
    
    handleDirItemClick (selectedDirId) {
        const { ProjectActions, pfileActions } = this.props;
        ProjectActions.saveItem({ selectedDirId, detailViewLevel: 'project' });
        pfileActions.getPfile(selectedDirId);
    };

    render() {
        const { projectState } = store.getState();
        
        const { ProjectActions } = this.props;
        
        return (
            <ProjectSideBar 
                projectState={projectState} 
                ProjectActions={ProjectActions}
                handleDirItemClick={this.handleDirItemClick}
            ></ProjectSideBar>
        );
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        userState: state.userState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        pfileActions: bindActionCreators(pfileAction, dispatch)

    })
) (ProjectSideBarContainer);