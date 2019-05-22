import React, { Component }  from 'react';
import { List } from 'immutable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';


import PfileTable from './PfileTable';
import * as pfileActions from '../../../../stores/modules/pfileState';
import * as projectActions from '../../../../stores/modules/projectState';
import * as attachmentActions from '../../../../stores/modules/attachmentState';

/**
 * @author : 김윤상		2019.05.22. 
 * 
 * @description
 *  - 업무 리스트 컨테이너
 */

class Pfile extends Component {

  static defaultProps = {
    pfiles: List(),
  }

  state = {
    pfile: {}, 
    attachmentGroup: List(),
  };

  componentWillMount(){
    this.setState({'selectedProject': this.props.projectState.get('selectedProject')})
  }

  componentWillReceiveProps(nextProps){
    const oldSelectedProject  = this.state.selectedProject.pid
    const newSelectedProject = nextProps.projectState.get('selectedProject').pid
    
    // 이전 선택 된 프로젝트와  현재 선택 된 프로젝트가 다를 경우 선택 된 첨부파일들 초기화
    if(oldSelectedProject !== newSelectedProject){ 
      this.state.attachmentGroup.clear();
    }
  }

  // 업무 클릭 이벤트
  handleClickPfile = (pfile) => {
    const {ProjectActions, PfileActions} = this.props;

    ProjectActions.saveItem({ detailViewLevel: 'pfile' });

    PfileActions.setPfile(pfile);
  }

  // 첨부파일 클릭 이벤트
  handleClickAttachment = (attachment) => {
    const {AttachmentActions, ProjectActions} = this.props;

    AttachmentActions.setAttachment(attachment);
    ProjectActions.saveItem({ detailViewLevel: 'attachment' });
  }

  // 업무 추가 선택
  handleClickPfileAddForm = () => {
    const {ProjectActions} = this.props;

    ProjectActions.saveItem({ detailViewLevel: 'pfileAdd' });
  }

  // 첨부파일 추가 선택
  handleClickAttachmentAddForm = () => {
    const {ProjectActions} = this.props;
    ProjectActions.saveItem({ detailViewLevel: 'attachmentAdd' });
  }

  // 업무 삭제 클릭
  handleClickDeletePfile = (pfileId) => {
    const {PfileActions} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    PfileActions.deletePfile(pfileId);
  }

  // 첨부파일 삭제 클릭
  handleClickDeleteAttachment = (attachmentId) => {
    const {AttachmentActions} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    AttachmentActions.deleteAttachment(attachmentId);
  } 

  // 모두 다운로드 버튼 클릭
  handleClickAttachmentGroupDownload = () => {
    const {AttachmentActions} = this.props;
    const attachmentGroup = this.props.attachmentState.get('attachmentGroup');   

    let body = [];

    attachmentGroup.map(attachment => {
      body.push(attachment.attachmentId);
    })

    AttachmentActions.downloadAttachmentGroup(body);
  }

    render(){
      const { pfileState, projectState, attachmentState, AttachmentActions } = this.props;


      const addButton =  projectState.get('selectedDirId') && projectState.get('selectedProject') && (projectState.get('selectedProject').prole != '게스트') && (
          <div className="dropdown">
            <button className="btn dropdown-toggle text-dark-1" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              목록 추가
            </button>
            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <a className="dropdown-item" href="#" onClick= {this.handleClickPfileAddForm}>업무</a>
              <div className="dropdown-divider"></div>
              <a className="dropdown-item" href="#" onClick= {this.handleClickAttachmentAddForm}>첨부 파일</a>
            </div>          
          </div>
        )

        return (          
        <div> 
          <div className="card-header py-3">  
            
            <div className="row">
              <div className="col text-dark-1 font-weight-bold" style={{fontSize: "25px"}}>
                업무 목록
              </div>

              <div className="col-2 m-0 align-self-end">
                {addButton}
              </div>
            </div>      
          </div>   

          <div className="card-body" >
            
              <PfileTable
                pfiles={pfileState.get('pfiles')}
                attachments = {attachmentState.get('attachments')}
                selectedDirId = {projectState.get('selectedDirId')}                
                attachmentGroup = {attachmentState.get('attachmentGroup')}
                selectedProject = {projectState.get('selectedProject')}

                handleClickDeletePfile = {this.handleClickDeletePfile}                
                handleClickPfile = {this.handleClickPfile}
                handleClickAttachment = {this.handleClickAttachment}
                handleClickDeleteAttachment = {this.handleClickDeleteAttachment}

                addDownloadGroup = { AttachmentActions.addGroup}
                deleteDownloadGroup = { AttachmentActions.deleteGroup}
                clearDownloadGroup = { AttachmentActions.clearGroup}
              />
            
            
            <button type="button" className="btn btn-dark-1 p-2" onClick={this.handleClickAttachmentGroupDownload}>
                모두  다운로드
            </button>            
            
            </div>
          </div>
          );
    }
};

export default connect(
    (state) => ({
        pfileState: state.pfileState,
        userState: state.userState,
        projectState: state.projectState,
        attachmentState: state.attachmentState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        PfileActions: bindActionCreators(pfileActions, dispatch),
        AttachmentActions: bindActionCreators(attachmentActions, dispatch),
    })
)(Pfile);
