import React, { Component }  from 'react';
import Modal from 'react-modal';
import { List, Map } from 'immutable';
import * as upmuActions from '../../stores/modules/saveUpmu';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import store from '../../stores';
import * as dirStateActions from '../../stores/modules/dirState'
import ContentTable from './ContentTable';

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

  /*
  function upmuList() {
    
    const {UpmuActions, dirId, upmus} = this.props;
    console.log(upmus);
    UpmuActions.getUpmu(dirId);
    const upmuItem = upmus.map((upmu) => 
      <li key={upmu.toString()}>{upmu.name} {upmu.contents} </li> 
    );

    return (
      <ul> {upmuItem} </ul>
    );    
  };
*/

static defaultProps = {
  upmus: List(),
  projectState: '',
}

static state = Map({
  upmus: List()
})

  componentDidMount() {
    const {dirState, userState} = store.getState();
    const {UpmuActions} = this.props;
    UpmuActions.getUpmu(saveUpmu.get('dirId'));
  }
/*
  getList = () => {
    const {saveUpmu} = store.getState();
    const {UpmuActions} = this.props;
    
    UpmuActions.getUpmu(saveUpmu.get('dirId'));

    return  saveUpmu.upmus.map((upmu) => 
    (<ContentItem key={upmu.name} upmu={upmu}/>))
  }
*/
  state = {
    modalIsOpen: false
  };

  openModal = () => {
    console.log(this.state)
    this.setState({modalIsOpen: true});
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

  // handleUpmuChange = () => {
  //   const {saveUpmu} = store.getState();
  //   return saveUpmu.get('upmus');
  // }

  handleInsert = (e) => {
    const {saveUpmu, dirState} = store.getState();
    const {UpmuActions} = this.props;
    //const {titleInput, contentInput} = this.props;

    const upmu = {
        name: saveUpmu.get('titleInput'),
        contents: saveUpmu.get('contentInput'),
        dirId: dirState.get('dir'),
    };

    console.log(upmu);
    UpmuActions.saveUpmu(upmu);
    this.closeModal();
    e.preventDefault();
    //this.state.set('upmus', saveUpmu.get('upmus'))
    //this.props.saveUpmu.setState('upmus', saveUpmu.get('upmus'));
  }

  handleChangeDir = (e) => {
    const {DirStateActions, UpmuActions} = this.props;
    const {dirState} = store.getState();

    DirStateActions.changeSelectedDir(1002);
    console.log('e.target.value',e.target.value);
    UpmuActions.getUpmu(1002); 
    
  }

    render(){
      const { openModal}  = this;
      const {saveUpmu} = this.props;
      const { handleTitleChange, handleContentChange, handleInsert, handleChangeDir } = this;
      //alert(`asd${saveUpmu}`)
      /*
      const upmuList = upmus.map((upmu) => 
        (<upmuItem key={upmu.name} upmu={upmu}/>)
      );

      console.log('--' + upmuList);
*/

        return (
            <div>
              <div>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onAfterOpen={this.afterOpenModal}
                >
                    <input value={saveUpmu.titleInput} placeholder="name" onChange={handleTitleChange}/>
                    <input value={saveUpmu.contentInput} placeholder="content" onChange={handleContentChange}/>
                    <button onClick={handleInsert}>add</button>
                    <button onClick={this.closeModal}>close</button>
                </Modal>
            </div>
              <div>
              <h2>프로젝트</h2>
                <button onClick={openModal}>add</button>
                <button>delete</button>
                <button>update</button>
                <button>log</button>
              </div>
            <div>
              {/*
              {saveUpmu.get('upmus').map((upmu, idx) => {
                console.log('--------', upmu);
                return (
                  <ContentItem key={idx} upmu={upmu}/>
                );
                })}
              */}
              <ContentTable upmus={saveUpmu.get('upmus')} onClickDir = {handleChangeDir} />              
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
        dirState: state.dirState
    }),
    (dispatch) => ({
        //UpmuContentActions: bindActionCreators(upmuContentActions, dispatch),
        UpmuActions: bindActionCreators(upmuActions, dispatch),
        DirStateActions : bindActionCreators(dirStateActions, dispatch),
    })
)(Upmu);