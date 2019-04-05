import React, { Component } from 'react';
import store from '../../stores';

class UpdatePfileModal extends Component {
  static defaultProps = {
    pfile: {
      name: '',
      contents: '',
    },
  }

  constructor(props) {
    super(props);

    this.state = {
      pfile: props.pfile,
      status: props,
      inputTitle: '',
      inputContents: '',
    }

    props.handleTitleChange(this.state.pfile.name);
    props.handleContentChange(this.state.pfile.contents);
  }

  onTitleChange = (e) => {
    this.props.handleTitleChange(e.target.value);
  }

  onContentChange = (e) => {
    this.props.handleContentChange(e.target.value);
  }

  handleSubmit = (e) => {
    //this.props.onClose();
    this.props.handleSubmit(this.state.pfile);    
    //this.props.handleCloseUpdateModal();
  }

  render() {
    const pfile = this.state;
    console.log("modal ---" , pfile.name);
    const {pfileState} = store.getState();

    return (
      <div class="modal fade" id="UpdatePfileModal" tabIndex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" backdrop="static">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
              <h4 class="modal-title" id="myModalLabel">업무 일지 수정</h4>
            </div>
            <div class="modal-body">
              ...
              
              <input type="text" name= "name" defaultValue={this.state.pfile.name} onChange={this.onTitleChange} placeholder="name"/>
              <input type="text" name= "name" defaultValue={this.state.pfile.contents} onChange={this.onContentChange} placeholder="contents"/>
              
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

export default UpdatePfileModal;