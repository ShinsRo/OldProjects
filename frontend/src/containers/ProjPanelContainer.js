import React, { Component } from 'react';

import store from '../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';
import * as dirStateActions from '../stores/modules/dirState';

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
        const { userState,  projectState} = store.getState();
        const { ProjectActions } = this.props;

        

        ProjectActions.axiosPostAsync('api/projects/dirs', {projId, userId: userState.userInfo.userId});
        // .then(
        //     () => { projectState.get('dirs') && console.log(projectState.get('dirs'))}
        //     //() => {return DirStateActions.setDirTree(projectState.get('dirs'))}
        // );        
    }

    // disState의 값을 변경해주는 함수 => 패널로 전달
    // setDirState(dirs) {
    //     const { DirStateActions } = this.props;

    //     console.log(dirs);
    //     DirStateActions.setDirTree(dirs);
    // }
    
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
        dirState: state.dirState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        DirStateActions: bindActionCreators(dirStateActions, dispatch),
    })
) (ProjPanelContainer);