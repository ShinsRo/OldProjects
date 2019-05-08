import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../../../stores/modules/projectState';
import * as pfileAction from '../../../stores/modules/pfileState';
import * as attachmentActions from '../../../stores/modules/attachmentState';
import * as plogActions from '../../../stores/modules/plogState';

import PfileLogItem from '../components/Pfile/PfileLogItem';
import AttachmentLogItem from '../components/Pfile/AttachmentLogItem';

class LogContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    // componentWillMount(){
    //     const { PLogActions, projectState } = this.props;

    //     PLogActions.getPLog(projectState.get('selectedDirId'));
    // }

    wrapWithCard(panel) {
        return (
            <div className="card shadow mb-4" style={{ height: '100%', width: '100%'}}>
                {panel}
            </div>
        );
    }

    render() {
        const { wrapWithCard } = this
        // const { projectState, pfileState, attachmentState, PfileActions,ProjectActions, AttachmentActions  } = this.props;
        const { plogState  } = this.props;
        const pfileLogs =  plogState.get('pfileLogs');
        const attachmentLogs = plogState.get('attachmentLogs');
        console.log('log containner ==================');
        console.log('pfileLogs ==================' , pfileLogs);
        console.log('attachmentLogs ==================', attachmentLogs);
        // const detailViewLevel = projectState.get('detailViewLevel');
        
        if(pfileLogs.length === 0 && attachmentLogs.length === 0) {
            return wrapWithCard(<>
                <div className="card-body text-center">
                    
                    <img
                        src={process.env.PUBLIC_URL + '/resources/img/undraw_no_data_qbuo.svg'} 
                        alt="log does not exist"
                        style={{ width: '200px' }}
                    />
                    <div>로그가 없습니다!</div>
                </div>
            </>);
        
        } else {
            return wrapWithCard(<>
                <div className="card-body">
                    <div className= "font-weight-bold text-darkblue col-10" >
                        <h1> 로그 </h1>
                    </div>

                    <div className="container">
                    {pfileLogs && pfileLogs.map((pfileLog, idx) => {
                            return(
                            <PfileLogItem 
                                idx = {idx}
                                pfileLog = {pfileLog}
                            />
                            )
                        })} 

                    {attachmentLogs && attachmentLogs.map((attachmentLog, idx) => {
                            return(
                            <AttachmentLogItem 
                                idx = {idx}
                                attachmentLog = {attachmentLog}
                            />
                            )
                        })} 
                    </div>
                </div>
            </>);
        }
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