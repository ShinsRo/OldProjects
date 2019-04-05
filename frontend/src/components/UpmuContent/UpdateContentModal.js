import React, { Component } from 'react';
import store from '../../stores';

class UpdateContentModal extends Component {
  static defaultProps = {
    upmu: {
      name: '',
      contents: '',
    },
  }

  constructor(props) {
    super(props);

    this.state = {
      upmu: props.upmu,
      status: props,
      inputTitle: '',
      inputContents: '',
    }

    props.handleTitleChange(this.state.upmu.name);
    props.handleContentChange(this.state.upmu.contents);
  }

  onTitleChange = (e) => {
    this.props.handleTitleChange(e.target.value);
  }

  onContentChange = (e) => {
    this.props.handleContentChange(e.target.value);
  }

  handleSubmit = (e) => {
    //this.props.onClose();
    this.props.handleSubmit(this.state.upmu);    
    //this.props.handleCloseUpdateModal();
  }

  render() {
    const upmu = this.state;
    console.log("modal ---" , upmu.name);
    const {saveUpmu} = store.getState();

    return (
      <div class="modal fade" id="UpmuUpdateModal" tabIndex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" backdrop="static">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
              <h4 class="modal-title" id="myModalLabel">업무 일지 수정</h4>
            </div>
            <div class="modal-body">
              ...
              
              <input type="text" name= "name" defaultValue={this.state.upmu.name} onChange={this.onTitleChange} placeholder="name"/>
              <input type="text" name= "name" defaultValue={this.state.upmu.contents} onChange={this.onContentChange} placeholder="contents"/>
              
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal" onClick={this.props.onClose}>Close</button>
              <button type="button" class="btn btn-primary" onClick={this.handleSubmit}>Save changes</button>
            </div>
          </div>
        </div>
      </div>
      );
  }
}

export default UpdateContentModal;