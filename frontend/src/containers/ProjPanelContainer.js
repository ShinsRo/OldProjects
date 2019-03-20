import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import * as projectActions from '../stores/modules/project_module';

import { ProjPanel } from '../components/ProjPanel';

class ProjPanelContainer extends Component {
    constructor(props) {
        super(props);
    }
    
    componentDidMount() {
        const { ProjectActions } = this.props;
        ProjectActions.getAllAsync('testurl');
    }

    render() {
        const { projectState } = this.props;
        return (<ProjPanel projects={projectState.projects}></ProjPanel>);
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
    })
) (ProjPanelContainer);