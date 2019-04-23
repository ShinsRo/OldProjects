import React, { Component }  from 'react';
import { List, Map } from 'immutable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import axios from 'axios';

import PfileTable from './PfileTable';
import AttachmentTable from './AttachmentTable';

import store from '../../stores';
import * as pfileActions from '../../stores/modules/pfileState';
import * as projectActions from '../../stores/modules/projectState';
import * as attachmentActions from '../../stores/modules/attachmentState';
import AddPfileModal from './AddPfileModal';

class Pfile extends Component {

  static defaultProps = {
    pfiles: List(),
  }

  constructor(props) {
    super(props);
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

  handlePfileUpdateForm = () => {
    const {ProjectActions} = this.props;

    ProjectActions.saveItem({ detailViewLevel: 'pfileUpdate' });

  }

  handleDeletePfile = (pfileId) => {
    const {PfileActions, projectState} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    console.log('delete pfile --' , pfileId)
    PfileActions.deletePfile(pfileId);
  }

  onFormSubmit = (e) => {
    e.preventDefault() // Stop form submit
    this.attachmentUpload(this.state.uploadAttachment)
    .then(()=>{
      this.setState({uploadAttachment:''})
    })
  }

  onChange = (e) => {
    this.setState({uploadAttachment:e.target.files[0]})
    console.log(e.target.files[0]);
  }

  attachmentUpload = (file) => {
    const {projectState, AttachmentActions} = this.props;
    // const url = 'http://localhost:8080/upmureport/attachment';
    console.log(file)
    const formData = new FormData();
    formData.append('file',file);
    formData.append('json', projectState.get('selectedDirId'));
    const config = {
        headers: {
            'content-type': 'multipart/form-data'
        }
    }
    return  AttachmentActions.saveAttachment(formData, config)
  }

    render(){
      const {pfileState, projectState, attachmentState } = this.props;

      // console.log(this.state.addModalIsOpen);
      // const addModal = this.state.addModalIsOpen && 
      //             <AddPfileModal
      //               onClose={this.handleCloseModal} 
      //               handleTitleChange = {this.handleTitleChange}
      //               handleContentChange = {this.handleContentChange}
      //               handleInsert = {this.handleInsert}
      //               />;

      const addButton =  projectState.get('selectedDirId') &&(
        <div>
          <button type="button" className="btn btn-info btn-icon-split" onClick= {this.handlePfileAddForm}>
            <span className="icon text-white-50">
              <i className="fas fa-info-circle"></i>
            </span>
            <span className="text">파일 추가</span>
          </button>

          <span className="filebox"> 
              <label htmlFor="ex_file">파일 가져오기</label>
              <input type="file" id="ex_file" onChange={this.onChange}/>
              <button type="submit" onClick={this.onFormSubmit} >Upload</button>
              {this.state.uploadAttachment && this.state.uploadAttachment.name}
            </span>
        </div>
        )

        return (
            <div className="card shadow mb-4">              
              <div>
              <h2>프로젝트</h2>
              </div>
              <div>
                {addButton}                
              </div>
            <div className="card-body">
              <PfileTable
                pfiles={pfileState.get('pfiles')}
                attachments = {attachmentState.get('attachments')}
                selectedDirId = {projectState.get('selectedDirId')}
                handleDeletePfile = {this.handleDeletePfile}                
                handleClickPfile = {this.handleClickPfile}
                handleClickAttachment = {this.handleClickAttachment}
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