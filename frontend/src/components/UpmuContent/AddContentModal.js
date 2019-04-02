import React, { Component } from 'react';
import store from '../../stores';

class AddContentModal extends Component {
  static defaultProps = {
    upmu: {
      name: '',
      contents: '',
    },
  }

  constructor(props) {
    super(props);

    this.state ={
      status: props,
      inputTitle: '',
      inputContents: '',
    }
  }

  onTitleChange = (e) => {
    this.props.handleTitleChange(e.target.value);
  }

  onContentChange = (e) => {
    this.props.handleContentChange(e.target.value);
  }

  handleSubmit = () => {
    
  }

  render() {
    const upmu = this.state;
    //console.log("modal ---" , upmu.name);
    const {saveUpmu} = store.getState();

    return (
      <div class="modal fade" id="UpmuAddModal" tabIndex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
              <h4 class="modal-title" id="myModalLabel">업무 일지 추가</h4>
            </div>
            <div class="modal-body">
              ...
              <input type="text" name= "name" onChange={this.onTitleChange} placeholder="name"/>
              <input type="text" name= "name" onChange={this.onContentChange} placeholder="contents"/>
      </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal" onClick={this.props.onClose}>Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
          </div>
        </div>
      </div>
      );
  }
}

export default AddContentModal;