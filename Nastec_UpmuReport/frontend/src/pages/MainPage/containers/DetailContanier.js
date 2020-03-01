/** 
 * 상세보기 패널 내용 제어 정의
 * 
 * 2019.05.22
 * @file DetailContanier 정의
 * @author 김승신, 김윤상
 */
import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../../../stores/modules/projectState';
import * as pfileAction from '../../../stores/modules/pfileState';
import * as attachmentActions from '../../../stores/modules/attachmentState';
import * as plogActions from '../../../stores/modules/plogState';



import { ProjPanel, PfilePanel, Pfileform, AttachmentPanel, AttachmentForm } from '../components/DetailPanel';

class DetailContanier extends Component {
    constructor(props) {
        super(props);
        this.state = {
            reload: false,
        };
    }

    wrapWithCard(panel) {
        return (
            <div className="card shadow mb-4" style={{ height: '100%', width: '100%'}}>
                {panel}
            </div>
        );
    }

    render() {
        const { projectState, pfileState, attachmentState, PfileActions, ProjectActions, AttachmentActions, PLogActions } = this.props;
        const { wrapWithCard } = this
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
         * 2. pfile의 경우
         *      선택한 파일의 자세한 정보를 보여줍니다.
         * 
         * 3. pfileAdd의 경우
         *      파일을 추가 할 수 있는 form으로 렌더링 됩니다.
         * 
         * 4. pfileUpdate의 경우
         *      파일을 수정 할 수 있는 form으로 렌더링 됩니다.
         * 
         * 5. attachment 경우
         *      첨부 파일의 자세한 정보를 보여줍니다.
         * 
         * 6. attachmentAdd 경우
         *      첨부 파일을 추가 할 수 있는 form으로 렌더링 됩니다.
         */
        
        if (detailViewLevel === 'project') {
            return wrapWithCard(<ProjPanel 
                    reload={this.props.reloadPage}
                    projectState={projectState}
                />);
        } else if (detailViewLevel === 'pfile') {
            return wrapWithCard(<PfilePanel 
                    pfileState={pfileState}
                    handleUpdateBts = {ProjectActions.saveItem}
                    selectedProject = {projectState.get('selectedProject')}
                />);
        } else if (detailViewLevel === 'pfileAdd') {
            return wrapWithCard(
                <Pfileform 
                    status='add'                    
                    selectedDirId = {projectState.get('selectedDirId')}
                    savePfile = {PfileActions.savePfile}
                    setPfile = {PfileActions.setPfile}
                    saveItem = {ProjectActions.saveItem}
                    pfile = {{name : '' , contents: ''}}
                    reloadPLog = {PLogActions.getPLog}
                    selectedProject = {projectState.get('selectedProject')}
                />
                );
        }else if (detailViewLevel === 'pfileUpdate') {
            return wrapWithCard(
                <Pfileform
                    status='update'
                    saveItem = {ProjectActions.saveItem}
                    selectedDirId = {projectState.get('selectedDirId')}
                    pfile = {pfileState.get('pfile')}
                    updatePfile = {PfileActions.updatePfile}
                    setPfile = {PfileActions.setPfile}
                    reloadPLog = {PLogActions.getPLog}
                    selectedProject = {projectState.get('selectedProject')}
                />
                );
        }else if (detailViewLevel === 'attachment') {
            return wrapWithCard(
                <AttachmentPanel 
                    attachment = {attachmentState.get('attachment')}
                    downloadAttachment = {AttachmentActions.downloadAttachment}
                    selectedProject = {projectState.get('selectedProject')}
                />
            );
        }else if (detailViewLevel === 'attachmentAdd') {
            return (
                <AttachmentForm 
                    saveAttachment= {AttachmentActions.saveAttachment}
                    selectedDirId = {projectState.get('selectedDirId')}
                    setAttachment = {AttachmentActions.setAttachment}
                    reloadPLog = {PLogActions.getPLog}
                    saveItem = {ProjectActions.saveItem}
                    selectedProject = {projectState.get('selectedProject')}
                    downloadAttachment = {AttachmentActions.downloadAttachment}
                />
            );
        }        
        else {
            return wrapWithCard(<>프로젝트 혹은 업무를 선택하세요.</>);
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
) (DetailContanier);
