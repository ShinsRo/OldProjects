import React, { Component }  from 'react';
import Modal from 'react-modal';
import { List, Map } from 'immutable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import ContentTable from './ContentTable';

import store from '../../stores';
import * as upmuActions from '../../stores/modules/saveUpmu';
import * as projectActions from '../../stores/modules/projectState';


const customStyles = {
  content : {
    top                   : '50%',
    left                  : '50%',
    right                 : 'auto',
    bottom                : 'auto',
    marginRight           : '-50%',
    transform             : 'translate(-50%, -50%)'
  }
};

class Upmu extends Component {

static defaultProps = {
  upmus: List(),
  projectState: '',
}

static state = Map({
  upmus: List()
})

  componentDidMount() {
    const {dirState, projectState} = store.getState();
    const {UpmuActions} = this.props;
    UpmuActions.getUpmu(projectState.get('selectedDirId'));
  }

  state = {
    modalIsOpen: false,
  };

  openModal = () => {
    const {saveUpmu} = store.getState();
    console.log(this.state)
    console.log('open modal')
    console.log(saveUpmu)
    this.setState({
      modalIsOpen: true,
    });
  }

  closeModal = () => {
    this.setState({modalIsOpen: false});
  }

  handleTitleChange = (e) => {
    const {UpmuActions} = this.props;
    UpmuActions.changeTitleInput(e.target.value);
  }

  handleContentChange = (e) => {
    const {UpmuActions} = this.props;
    UpmuActions.changeContentInput(e.target.value);
  }

  handleInsert = (e) => {
    const {saveUpmu, projectState} = store.getState();
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

  handleChangeDir = (dirId) => {
    const {ProjectActions, UpmuActions} = this.props;
    const {projectState} = store.getState();
    //const {dir} = JSON.stringify(dirId);

    console.log('e.target.value',dirId);
    ProjectActions.saveDirId(dirId);
    UpmuActions.getUpmu(dirId);
  }

  handleClickUpmu = (upmu) => {
    const {ProjectActions, UpmuActions} = this.props;
    const {saveUpmu} = this.props;
    console.log(upmu.name);

    this.openModal();

    UpmuActions.changeTitleInput(upmu.name);
    UpmuActions.changeContentInput(upmu.contents);

    // this.state.set({
    //   titleInput: upmu.name,
    //   contentInput: upmu.contents,
    // })

    console.log(saveUpmu.get('titleInput'));  
  }

    render(){
      const { openModal}  = this;
      const {saveUpmu, projectState} = this.props;
      console.log(saveUpmu.titleInput);
      const { handleTitleChange, handleContentChange, handleInsert, handleChangeDir, handleClickUpmu } = this;
      
        return (
            <div className="card shadow mb-4">
              <div>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                >
                    <textarea name= "name" value={saveUpmu.titleInput} placeholder="name" onChange={handleTitleChange}/>
                    <textarea name="content" value={saveUpmu.contentInput} placeholder="content" onChange={handleContentChange}/>
                    <button onClick={handleInsert}>add</button>
                    <button onClick={this.closeModal}>close</button>
                </Modal>
            </div>

              <div>
              <h2>프로젝트</h2>
                <button onClick={openModal}>add</button>
                <button>delete</button>
                <button onClick={openModal}>update</button>
                <button>log</button>
              </div>
            <div className="card-body">
              <ContentTable
                upmus={saveUpmu.get('upmus')} 
                onClickDir = {handleChangeDir} 
                onClickUpmu = {handleClickUpmu}
                selectedProject= {projectState.get('selectedProject')}
                selectedDirId = {projectState.get('selectedDirId')} 
                dirs={projectState.get('dirs')}

              />              
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
        //UpmuContentActions: bindActionCreators(upmuContentActions, dispatch),
        ProjectActions: bindActionCreators(projectActions, dispatch),
        UpmuActions: bindActionCreators(upmuActions, dispatch),
    })
)(Upmu);