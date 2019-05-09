import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../../../stores/modules/projectState';
import * as pfileAction from '../../../stores/modules/pfileState';
import * as attachmentActions from '../../../stores/modules/attachmentState';
import * as plogActions from '../../../stores/modules/plogState';

import { LogPanel } from "../components/LogPanel";

class LogContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const { plogState } = this.props;
        
        const pLogs =  plogState.get('pLogs');
        const logs = pLogs || []

        return (
            <LogPanel
                logs={logs}
                handler={''}
            />
        );
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        pfileState: state.pfileState,
        userState: state.userState,
        attachmentState: state.attachmentState,
        plogState : state.plogState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        PfileActions: bindActionCreators(pfileAction, dispatch),
        AttachmentActions: bindActionCreators(attachmentActions, dispatch),
        PLogActions : bindActionCreators(plogActions, dispatch),
    })
) (LogContainer);