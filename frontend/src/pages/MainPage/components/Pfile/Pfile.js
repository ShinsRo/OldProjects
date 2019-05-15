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
    const {ProjectActions} = this.props;

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

    PfileActions.deletePfile(pfileId);
  }


  handleDeleteAttachment = (attachmentId) => {
    const {AttachmentActions} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    AttachmentActions.deleteAttachment(attachmentId);
  }

    render(){
      const {pfileState, projectState, attachmentState } = this.props;

      const addButton =  projectState.get('selectedDirId') &&(
          // <div className="row justify-content-end mr-3">       
          //   <div className="col-1.5 mr-4">
          //       <button type="button" className="btn btn-light-1 p-2 " onClick= {this.handlePfileAddForm}>
          //         파일추가
          //       </button>   
          //       </div>
          //     <div className="col-2.7">     
          //       <button type="button" className="btn btn-light-1 p-2" onClick= {this.handleAttachmentAddForm}>
          //         첨부파일 추가
          //       </button>
          //     </div>
          //   </div>

          <div className="dropdown">
            <button class="btn dropdown-toggle text-dark-1" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              목록 추가
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <a class="dropdown-item" href="#" onClick= {this.handlePfileAddForm}>업무</a>
              <div className="dropdown-divider"></div>
              <a class="dropdown-item" href="#" onClick= {this.handleAttachmentAddForm}>첨부 파일</a>
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
          
          <div className="card-body" style={{ height: '650px' , overflowX :'auto', overflowY:'scroll' }}>
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
