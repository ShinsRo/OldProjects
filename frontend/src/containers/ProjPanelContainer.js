import React, { Component } from 'react';

import store from '../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';

import { ProjPanel } from '../components/ProjPanel';

class ProjPanelContainer extends Component {
    constructor(props) {
        super(props);
        this.loadDirs = this.loadDirs.bind(this);
    }
    componentDidMount() {
        const { userState } = store.getState();
        const { ProjectActions } = this.props;

        ProjectActions.axiosGetAsync('api/projects/list', {userId: userState.userInfo.userId});
    }

    loadDirs (projId) {
        const { userState } = store.getState();
        const { ProjectActions } = this.props;

        ProjectActions.axiosPostAsync('api/projects/dirs', {projId, userId: userState.userInfo.userId})
    }
    
    render() {
        const { projectState } = store.getState();
        // const { userState } = this.props;
        //임시 유저 목록 스토어
        
        const { userState } = { userState: { selectedUser: {userId: '1111', userName: '김승신'} }};
        
        return (<ProjPanel projectState={projectState} userState={userState} onProjClick={this.loadDirs}></ProjPanel>);
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        userState: state.userState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch)
    })
) (ProjPanelContainer);