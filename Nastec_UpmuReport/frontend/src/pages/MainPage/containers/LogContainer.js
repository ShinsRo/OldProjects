import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../../../stores/modules/projectState';
import * as pfileAction from '../../../stores/modules/pfileState';
import * as attachmentActions from '../../../stores/modules/attachmentState';
import * as plogActions from '../../../stores/modules/plogState';

import { LogPanel, LogDetailPanel } from "../components/LogPanel";


class LogContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    handleLogClick = (log) => {
        const {PLogActions} = this.props;

        PLogActions.setLog(log);
        this.handleLogViewLevel('logDetail');
    }

    handleLogViewLevel = (level) => {
        const {PLogActions} = this.props;

        PLogActions.setLogViewLevel(level);
    }

    render() {
        const { plogState } = this.props;

        const pLogs = plogState.get('pLogs');
        const pLog = plogState.get('pLog');


        const logs = pLogs || []
        const log = pLog || '';

        const logViewLevel = plogState.get('logViewLevel');

        /**
         * @author 김윤상
         * plogState에서 관리하는 디테일 뷰 레벨에 따라 디테일 패널 내용을 분기합니다.
         * 1. logList 의 경우
         *      해당 프로젝트의 로그들을 리스트 형태로 보여줍니다. 
         * 
         * 2. logDetail 의 경우
         *      로그 클릭 시 해당 로그의 디테일한 정보를 보여주는 패널로 변경 됩니다.
         * 
         */
        
        
        if (logViewLevel ===  'logList') {
            return (
                <LogPanel
                    logs={logs}
                    handler={''}
                    setLogViewLevel={this.handleLogViewLevel}
                    onClickLog = {this.handleLogClick}
                />
            );
        } else if (logViewLevel === 'logDetail') {
            return (
                <LogDetailPanel
                    log = {log}
                    handleLogViewLevel ={this.handleLogViewLevel}
                />
            );
        } else {
            return (
                <div>

                </div>
            );
        }



    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        pfileState: state.pfileState,
        userState: state.userState,
        attachmentState: state.attachmentState,
        plogState: state.plogState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        PfileActions: bindActionCreators(pfileAction, dispatch),
        AttachmentActions: bindActionCreators(attachmentActions, dispatch),
        PLogActions: bindActionCreators(plogActions, dispatch),
    })
)(LogContainer);
