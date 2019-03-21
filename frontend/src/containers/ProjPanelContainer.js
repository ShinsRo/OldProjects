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
        
        ProjectActions.axiosGetAsync('api/projects/list', {userId: '1111'});
    }
    render() {
        const { projectState } = this.props;
        // const { userState } = this.props;
        //임시 유저 스토어
        const { userState } = {userState: { selectedUser: {userId: '1111', userName: '김승신'} }};
        
        return (<ProjPanel projectState={projectState} userState={userState}></ProjPanel>);
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