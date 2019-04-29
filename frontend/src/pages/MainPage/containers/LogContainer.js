import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../../../stores/modules/projectState';
import * as pfileAction from '../../../stores/modules/pfileState';
import * as attachmentActions from '../../../stores/modules/attachmentState';

class LogContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

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
        // const detailViewLevel = projectState.get('detailViewLevel');
        
        if(false) {
            return wrapWithCard(<></>);
        } else {
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
        }

    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        pfileState: state.pfileState,
        userState: state.userState,
        attachmentState: state.attachmentState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        PfileActions: bindActionCreators(pfileAction, dispatch),
        AttachmentActions: bindActionCreators(attachmentActions, dispatch),
    })
) (LogContainer);