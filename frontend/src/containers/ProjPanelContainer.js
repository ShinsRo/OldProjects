import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import * as projectActions from '../stores/modules/project_module';

import { ProjPanel } from '../components/ProjPanel';
import { Map, List } from 'immutable';

class ProjPanelContainer extends Component {
    // constructor(props) {
    //     super(props);
    // }
    componentDidMount() {
        const { ProjectActions } = this.props;
        console.log(this.props);
        
        ProjectActions.axiosGetAsync('api/projects/list', {userId: '1111'});
    }
    render() {
        const { projectState } = this.props;
        
        return (<ProjPanel projectState={projectState}></ProjPanel>);
    }
}

export default connect(
    (state) => ({
        projectState: state.get('projectState'),
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch)
    })
) (ProjPanelContainer);