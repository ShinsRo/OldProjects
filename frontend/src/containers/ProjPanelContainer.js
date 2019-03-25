import React, { Component } from 'react';

import store from '../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';

import { ProjPanel } from '../components/ProjPanel';

class ProjPanelContainer extends Component {
    // constructor(props) {
    //     super(props);
    // }
    componentDidMount() {
        const { ProjectActions } = this.props;
        
        ProjectActions.axiosGetAsync('api/projects/list', {userId: '1111'});
    }
    render() {
        const { projectState } = store;
        // const { userState } = this.props;
        //임시 유저 스토어
        
        const { userState } = { userState: { selectedUser: {userId: '1111', userName: '김승신'} }};
        console.log(projectState);
        
        return (<ProjPanel projectState={projectState} userState={userState}></ProjPanel>);
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch)
    })
) (ProjPanelContainer);