import React, { Component }  from 'react';
import { List } from 'immutable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import PfileTable from './PfileTable';
import * as pfileActions from '../../../../stores/modules/pfileState';
import * as projectActions from '../../../../stores/modules/projectState';
import * as attachmentActions from '../../../../stores/modules/attachmentState';

class Pfile extends Component {

  static defaultProps = {
    pfiles: List(),
  }

  state = {
    pfile: {}, 
  };


  handleClickPfile = (pfile) => {
    const {ProjectActions, PfileActions} = this.props;

    ProjectActions.saveItem({ detailViewLevel: 'pfile' });

    PfileActions.setPfile(pfile);
  }

  handleClickAttachment = (attachment) => {
    const {AttachmentActions, ProjectActions} = this.props;

    AttachmentActions.setAttachment(attachment);
    ProjectActions.saveItem({ detailViewLevel: 'attachment' });

  } 

  handlePfileAddForm = () => {
    const {ProjectActions, projectState} = this.props;
    console.log(projectState.get('selectedDirId'))
    ProjectActions.saveItem({ detailViewLevel: 'pfileAdd' });
  }

  handleAttachmentAddForm = () => {
    const {ProjectActions} = this.props;
    ProjectActions.saveItem({ detailViewLevel: 'attachmentAdd' });
  }

  handlePfileUpdateForm = () => {
    const {ProjectActions} = this.props;

    ProjectActions.saveItem({ detailViewLevel: 'pfileUpdate' });

  }

  handleDeletePfile = (pfileId) => {
    const {PfileActions} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    console.log('delete pfile --' , pfileId)
    PfileActions.deletePfile(pfileId);
  }


  handleDeleteAttachment = (attachmentId) => {
    const {AttachmentActions} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    console.log('delete attachment --' , attachmentId)
    AttachmentActions.deleteAttachment(attachmentId);
  }

    render(){
      const {pfileState, projectState, attachmentState } = this.props;

      const addButton =  projectState.get('selectedDirId') &&(
        <nav>
          <div className="row justify-content-end">
            <div class="btn-group" role="group" aria-label="Basic example">              
                <button type="button" className="btn btn-dark-1 p-2 " onClick= {this.handlePfileAddForm}>
                  파일추가
                </button>           
                <button type="button" className="btn btn-dark-1 p-2" onClick= {this.handleAttachmentAddForm}>
                  첨부파일 추가
                </button>
            </div>
          </div>
        </nav>
        )

        return (
          
        <div> 
          <div className="card-header py-3">
            <div className="row">
              <div className="font-weight-bold text-darkblue col-10">
                <h1>프로젝트</h1>
              </div>
            </div>
            <hr/>
            <div>
              
                {addButton}                
              
            </div> 
            
          </div>       
          <div className="card-body">
              <PfileTable
                pfiles={pfileState.get('pfiles')}
                attachments = {attachmentState.get('attachments')}
                selectedDirId = {projectState.get('selectedDirId')}
                handleDeletePfile = {this.handleDeletePfile}                
                handleClickPfile = {this.handleClickPfile}
                handleClickAttachment = {this.handleClickAttachment}
                handleDeleteAttachment = {this.handleDeleteAttachment}
              />
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