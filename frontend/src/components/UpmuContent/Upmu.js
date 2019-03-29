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
  treeDir:'',
}

static state = Map({
  upmus: List(),
  dirs: List(),
})

  componentDidMount() {
    const {dirState, projectState} = store.getState();
    const {UpmuActions} = this.props;
<<<<<<< HEAD
    UpmuActions.getUpmu(projectState.get('selectedDirId'));
=======
    projectState.get('selectedProject') && UpmuActions.getUpmu(projectState.get('selectedProject'));   
>>>>>>> refs/remotes/origin/dev
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
<<<<<<< HEAD
    const {saveUpmu, projectState} = store.getState();
=======
    const {saveUpmu, dirState, projectState} = store.getState();
>>>>>>> refs/remotes/origin/dev
    const {UpmuActions} = this.props;

    const upmu = {
        name: saveUpmu.get('titleInput'),
        contents: saveUpmu.get('contentInput'),
<<<<<<< HEAD
        dirId: projectState.get('selectedDirId'),
=======
        dirId: projectState.get('selectedProject'),
        localPath: 'dd',
>>>>>>> refs/remotes/origin/dev
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

<<<<<<< HEAD
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
=======
    DirStateActions.changeSelectedDir(1002);
    console.log('e.target.value',e.target.value);
    UpmuActions.getUpmu(1002);     
>>>>>>> refs/remotes/origin/dev
  }

    render(){
      const { openModal}  = this;
<<<<<<< HEAD
      const {saveUpmu, projectState} = this.props;
      console.log(saveUpmu.titleInput);
      const { handleTitleChange, handleContentChange, handleInsert, handleChangeDir, handleClickUpmu } = this;
      
=======
      const {saveUpmu} = this.props;
      const { handleTitleChange, handleContentChange, handleInsert, handleChangeDir } = this;
      const {projectState} = this.props;
      // const dirs 
      //       = this.props.projectState 
      //       && this.props.projectState.get('errObj').get('isHandled') 
      //       && this.props.projectState.get('dirs')
      //alert(`asd${saveUpmu}`)
      /*
      const upmuList = upmus.map((upmu) => 
        (<upmuItem key={upmu.name} upmu={upmu}/>)
      );

      console.log('--' + upmuList);
*/

>>>>>>> refs/remotes/origin/dev
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
<<<<<<< HEAD

              <div>
=======
              <div >
>>>>>>> refs/remotes/origin/dev
              <h2>프로젝트</h2>
                <button onClick={openModal}>add</button>
                <button>delete</button>
                <button onClick={openModal}>update</button>
                <button>log</button>
              </div>
            <div className="card-body">
<<<<<<< HEAD
              <ContentTable
                upmus={saveUpmu.get('upmus')} 
                onClickDir = {handleChangeDir} 
                onClickUpmu = {handleClickUpmu}
                selectedProject= {projectState.get('selectedProject')}
                selectedDirId = {projectState.get('selectedDirId')} 
                dirs={projectState.get('dirs')}

              />              
=======
              {/*
              {saveUpmu.get('upmus').map((upmu, idx) => {
                console.log('--------', upmu);
                return (
                  <ContentItem key={idx} upmu={upmu}/>
                );
                })}
              */}
              <ContentTable 
                upmus={saveUpmu.get('upmus')} 
                onClickDir = {handleChangeDir} 
                selectedProject = {projectState.get('selectedProject')} 
                dirs = {projectState.get('dirs')}
                />              
>>>>>>> refs/remotes/origin/dev
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