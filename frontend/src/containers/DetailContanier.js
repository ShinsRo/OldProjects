import React, { Component } from 'react';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';
import * as pfileAction from '../stores/modules/pfileState';
import * as attachmentActions from '../stores/modules/attachmentState';



import { ProjPanel, PfilePanel, Pfileform, AttachmentPanel } from '../components/DetailPanel';

class DetailContanier extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }



    render() {
        const { projectState, pfileState, attachmentState, PfileActions,ProjectActions, AttachmentActions  } = this.props;
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
         * 4. pfile의 경우
         *      파일을 수정 할 수 있는 form으로 렌더링 됩니다.
         */
        if (detailViewLevel === 'project') {
            return (<ProjPanel projectState={projectState}/>);
        } else if (detailViewLevel === 'pfile') {
            return (<PfilePanel 
                pfileState={pfileState}
                handleUpdateBts = {ProjectActions.saveItem}
                />);
        } else if (detailViewLevel === 'pfileAdd') {
            return (
                <Pfileform 
                    status='add'                    
                    selectedDirId = {projectState.get('selectedDirId')}
                    savePfile = {PfileActions.savePfile}
                />
                );
        }else if (detailViewLevel === 'pfileUpdate') {
            return (
                <Pfileform 
                    status='update'
                    saveItem = {ProjectActions.saveItem}
                    selectedDirId = {projectState.get('selectedDirId')}
                    pfile = {pfileState.get('pfile')}
                    updatePfile = {PfileActions.updatePfile}
                />
                );
        }else if (detailViewLevel === 'attachment') {
            return (
                <AttachmentPanel 
                    attachment = {attachmentState.get('attachment')}
                    downloadAttachment = {AttachmentActions.downloadAttachment}
                />
                );
        }
        
        else {
            return (<>프로젝트 혹은 업무를 선택하세요.</>);
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
) (DetailContanier);