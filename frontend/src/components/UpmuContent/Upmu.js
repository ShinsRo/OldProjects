import React, { Component }  from 'react';
import Modal from 'react-modal';
import { List, Map } from 'immutable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import ContentTable from './ContentTable';

import store from '../../stores';
import * as upmuActions from '../../stores/modules/saveUpmu';
import * as projectActions from '../../stores/modules/projectState';
import AddContentModal from './AddContentModal';

class Upmu extends Component {

static defaultProps = {
  upmus: List(),
  projectState: '',
  treeDir:'',
}
  constructor(props) {
    super(props);
    this.handleChangeDir = this.handleChangeDir.bind(this);
  }

  componentDidMount() {
    const {dirState, projectState} = store.getState();
    const {UpmuActions} = this.props;
    //projectState.get('selectedProject') && UpmuActions.getUpmu(projectState.get('selectedProject'));
  }

  state = {
    addModalIsOpen: false,
    updateModalIsOpen: false,
    upmu: {}, 
  };

  handleCloseAddModal = () => {
    this.setState({addModalIsOpen: false});
  }

  // handleCloseUpdateModal = () => {
  //   this.setState({updateModalIsOpen: false});
  // }

  handleTitleChange = (input) => {
    const {UpmuActions} = this.props;
    UpmuActions.changeTitleInput(input);
  }

  handleContentChange = (input) => {
    const {UpmuActions} = this.props;
    UpmuActions.changeContentInput(input);
  }

  handleClickAdd = () => {
    this.setState({addModalIsOpen: true});
  }

  // handleClickUpmu = () => {
  //   this.setState({updateModalIsOpen: true});
  // }

  handleInsert = (e) => {    
    const {saveUpmu, dirState, projectState} = store.getState();

    const {UpmuActions} = this.props;

    const upmu = {
        name: saveUpmu.get('titleInput'),
        contents: saveUpmu.get('contentInput'),
        dirId: projectState.get('selectedDirId'),
    };

    console.log(upmu);
    UpmuActions.saveUpmu(upmu);
    this.closeModal();
    e.preventDefault();
  }

  handleUpdate = (upmu) => {    
    const {saveUpmu, projectState} = store.getState();

    const {UpmuActions} = this.props;

    const upmuItem = {
        name: saveUpmu.get('titleInput'),
        contents: saveUpmu.get('contentInput'),
        upmuId: upmu.upmuId,
        dirId: projectState.get('selectedDirId'),
    };

    // upmu.setState({      
    //     name: saveUpmu.get('titleInput'),
    //     contents: saveUpmu.get('contentInput'),
    // })

    console.log('Upmu -> handleUpdate',upmuItem);
    UpmuActions.updateUpmu(upmuItem);
    //e.preventDefault();
  }

  handleChangeDir = (dirId) => {
    const {ProjectActions, UpmuActions} = this.props;
    const {projectState} = store.getState();
    //const {dir} = JSON.stringify(dirId);

    console.log('e.target.value',dirId);
    ProjectActions.saveDirId(dirId);
    UpmuActions.getUpmu(dirId);
  }

    render(){
      const { openModal }  = this;
      const {saveUpmu, projectState} = this.props;
      console.log(saveUpmu.titleInput);
      const { handleTitleChange, handleContentChange, handleInsert, handleChangeDir } = this;
            
      console.log(this.state.addModalIsOpen);
      const addModal = this.state.addModalIsOpen && 
                  <AddContentModal
                    onClose={this.handleCloseModal} 
                    handleTitleChange = {this.handleTitleChange}
                    handleContentChange = {this.handleContentChange}
                    />;

        return (
            <div className="card shadow mb-4">              
              <div>
              <h2>프로젝트</h2>
                <span className="btn btn-primary btn-icon-split" onClick={this.handleClickAdd} data-toggle="modal" data-target="#UpmuAddModal">
                    <span className="icon text-white-50">
                      <i className="fas fa-flag"></i>
                    </span>
                    <span className="text">업무 내용 추가</span>
                </span>
                {addModal}
              </div>
            <div className="card-body">
              <ContentTable
                handleTitleChange = {handleTitleChange}
                handleContentChange = {handleContentChange}
                upmus={saveUpmu.get('upmus')} 
                onClickDir = {handleChangeDir} 
                selectedProject= {projectState.get('selectedProject')}
                selectedDirId = {projectState.get('selectedDirId')} 
                dirs={projectState.get('dirs')}
                handleUpdate= {this.handleUpdate}
                //updateModalIsOpen = {this.state.updateModalIsOpen}
                //handleClickUpmu = {this.handleClickUpmu}
                handleCloseUpdateModal = {this.handleCloseUpdateModal}
              />

              <button type="button" data-toggle="modal" data-target="#UpmuAddModal" className="btn btn-info btn-icon-split">
                <span className="icon text-white-50">
                  <i className="fas fa-info-circle"></i>
                </span>
              <span className="text">로그 보기</span>
              </button>
              
            </div>
          </div>
          );
    }
};

export default connect(
    (state) => ({
        saveUpmu: state.saveUpmu,
        userState: state.userState,
        projectState: state.projectState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch),
        UpmuActions: bindActionCreators(upmuActions, dispatch),
    })
)(Upmu);