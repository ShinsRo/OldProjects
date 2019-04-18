import React, { Component }  from 'react';
import { List, Map } from 'immutable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import PfileTable from './PfileTable';
import AttachmentTable from './AttachmentTable';

import store from '../../stores';
import * as pfileActions from '../../stores/modules/pfileState';
import * as projectActions from '../../stores/modules/projectState';
import AddPfileModal from './AddPfileModal';

class Pfile extends Component {

  static defaultProps = {
    pfiles: List(),
  }
  constructor(props) {
    super(props);
    this.handleChangeDir = this.handleChangeDir.bind(this);
  }

  state = {
    addModalIsOpen: false,
    updateModalIsOpen: false,
    pfile: {}, 
  };

  handleCloseAddModal = () => {
    this.setState({addModalIsOpen: false});
  }

  handleTitleChange = (input) => {
    const {PfileActions} = this.props;
    PfileActions.changeTitleInput(input);
  }

  handleContentChange = (input) => {
    const {PfileActions} = this.props;
    PfileActions.changeContentInput(input);
  }

  handleClickAdd = () => {
    this.setState({addModalIsOpen: true});
  }

  handleInsert = (e) => {    
    const {pfileState, projectState} = store.getState();
    const {PfileActions} = this.props;

    const pfile = {
        name: pfileState.get('titleInput'),
        contents: pfileState.get('contentInput'),
        pdirId: projectState.get('selectedDirId'),
    };

    console.log(pfile);
    PfileActions.savePfile(pfile);
    //this.handleCloseAddModal();
    e.preventDefault();
  }

  handleUpdate = (pfile) => {    
    const {pfileState, projectState} = store.getState();
    const {PfileActions} = this.props;

    const pfileItem = {
        name: pfileState.get('titleInput'),
        contents: pfileState.get('contentInput'),
        pfileId: pfile.pfileId,
        dirId: projectState.get('selectedDirId'),
    };

    console.log('pfile -> handleUpdate',pfileItem);
    PfileActions.updatePfile(pfileItem);
    //e.preventDefault();
  }

  handleChangeDir = (dirId) => {
    const {ProjectActions, PfileActions} = this.props;
    const {projectState} = store.getState();

    console.log('e.target.value',dirId);
    ProjectActions.saveDirId(dirId);
    PfileActions.getPfile(dirId);
    
  }

  handleDeletePfile = (pfileId) => {
    const {PfileActions, projectState} = this.props;

    if (!window.confirm('ㄹㅇ?')) return;

    console.log('delete pfile --' , pfileId)
    PfileActions.deletePfile(pfileId);
    //UpmuActions.getUpmu(projectState.get('selectedDirId'));
    //UpmuActions.getUpmu(upmuId);
  }

    render(){
      const {pfileState, projectState} = this.props;
      const { handleTitleChange, handleContentChange, handleInsert, handleChangeDir } = this;

      console.log(this.state.addModalIsOpen);
      const addModal = this.state.addModalIsOpen && 
                  <AddPfileModal
                    onClose={this.handleCloseModal} 
                    handleTitleChange = {this.handleTitleChange}
                    handleContentChange = {this.handleContentChange}
                    handleInsert = {this.handleInsert}
                    />;

      const addButton =  projectState.get('selectedDirId') &&(
                  <span>
                      <span className="btn btn-primary btn-icon-split" onClick={this.handleClickAdd} data-toggle="modal" data-target="#pfileAddModal">
                          <span className="icon text-white-50">
                              <i className="fas fa-flag"></i>
                          </span>
                          <span className="text">업무 내용 추가</span>   
                      </span>                      
                  </span>)

        return (
            <div className="card shadow mb-4">              
              <div>
              <h2>프로젝트</h2>
              </div>
              <div>
                {addButton}
                {addModal}    

                <button type="button" data-toggle="modal" data-target="#AddPfileModal" className="btn btn-info btn-icon-split">
                <span className="icon text-white-50">
                  <i className="fas fa-info-circle"></i>
                </span>
              <span className="text">로그 보기</span>
              </button>
                
              </div>
            <div className="card-body">
              <PfileTable
                handleTitleChange = {handleTitleChange}
                handleContentChange = {handleContentChange}
                pfiles={pfileState.get('pfiles')} 
                onClickDir = {handleChangeDir} 
                selectedProject= {projectState.get('selectedProject')}
                selectedDirId = {projectState.get('selectedDirId')} 
                dirs={projectState.get('dirs')}
                handleUpdate= {this.handleUpdate}
                //updateModalIsOpen = {this.state.updateModalIsOpen}
                //handleClickUpmu = {this.handleClickUpmu}
                handleCloseUpdateModal = {this.handleCloseUpdateModal}
                handleDeletePfile = {this.handleDeletePfile}
              />    
             
            </div>

            <div className="card-body">
              <AttachmentTable
                selectedDirId = {projectState.get('selectedDirId')} />

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
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        PfileActions: bindActionCreators(pfileActions, dispatch),
    })
)(Pfile);