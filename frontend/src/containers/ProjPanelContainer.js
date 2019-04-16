import React, { Component } from 'react';

import store from '../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';
import * as pfileAction from '../stores/modules/pfileState'


import { ProjPanel } from '../components/ProjPanel';


class ProjPanelContainer extends Component {
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
        ProjectActions.saveItem({ selectedDirId });
        pfileActions.getPfile(selectedDirId);
    };

    render() {
        const { projectState } = store.getState();
        
        const { ProjectActions } = this.props;
        
        return (
            <ProjPanel 
                projectState={projectState} 
                ProjectActions={ProjectActions}
                handleDirItemClick={this.handleDirItemClick}
            ></ProjPanel>
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
) (ProjPanelContainer);