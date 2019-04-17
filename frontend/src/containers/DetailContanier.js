import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';
import * as pfileAction from '../stores/modules/pfileState'


import { ProjPanel, UpmuPanel } from '../components/DetailPanel';

class DetailContanier extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const { projectState } = this.props;
        const detailViewLevel = projectState.get('detailViewLevel');
    
        /**
         * @author 김승신
         * projectState에서 관리하는 디테일 뷰 레벨에 따라 디테일 패널 내용을 분기합니다.
         * 1. project의 경우
         *      프로젝트 사이드 바에서 프로젝트를 선택하면 해당 컴포넌트(ProjectSideBar)
         *      projectState에 detailViewLevel을 project로 설정하고, 스토어가 변경되어
         *      본 컴포넌트를 업데이트(재 랜더링)합니다.
         * 
         * @author 김윤상
         * 2. upmu의 경우
         */
        if (detailViewLevel === 'project') {
            return (<ProjPanel/>);
        } else if (detailViewLevel === 'umpu') {
            return (<UpmuPanel/>);
        } else {
            return (<>프로젝트 혹은 업무를 선택하세요.</>);
        }

        
    }
}
export default connect(
    (state) => ({
        projectState: state.projectState,
        pfileState: state.pfileState,
        userState: state.userState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        pfileActions: bindActionCreators(pfileAction, dispatch),
    })
) (DetailContanier);