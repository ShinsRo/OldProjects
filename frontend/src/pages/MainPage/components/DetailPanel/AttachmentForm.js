import React, { Component } from 'react';

class AttachmentForm extends Component {

    state = {
        pfile: {}, 
        coment :'',
        isSuccess: '',
      };

    componentWillMount() {
        this.setState({'isSuccess' : false})
    }

    onFormSubmit = (e) => {

        const {saveItem} = this.props;
        e.preventDefault() // Stop form submit
        this.attachmentUpload(this.state.uploadAttachment)
        .then(()=>{
          this.setState({uploadAttachment:''})
          //saveItem({ detailViewLevel: 'attachment'})
        })

        this.setState({'isSuccess' : true})
    }     
    
    handleContentChange = (e) => {
        // const {PfileActions} = this.props;
        // PfileActions.changeContentInput(e.target.value);
        this.setState({ coment: e.target.value })
    }

    onFileChange = (e) => {
        this.setState({uploadAttachment:e.target.files[0]})
        console.log(e.target.files[0]);
    }
    
    attachmentUpload = (file) => {
        const {saveAttachment, selectedDirId, setAttachment} = this.props;
    
        const formData = new FormData();   
        
        const AttachmentReqDto = {
            'pdir' : selectedDirId,
            'coment' : this.state.coment,
        }

        setAttachment(file);

        formData.append('file',file);
        formData.append('json', JSON.stringify(AttachmentReqDto));
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        return  saveAttachment(formData, config)
    }        


    render() {

         
        if(this.state.isSuccess){
            return (
                <div>
                    <div>성공!!</div>
                </div>
            );
        }

        return (
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <span className="filebox"> 
                        <label htmlFor="ex_file">파일 가져오기</label>
                        <input type="file" id="ex_file" onChange={this.onFileChange}/>                        
                        {this.state.uploadAttachment && this.state.uploadAttachment.name}
                    </span>
                </div>

                <div className="card-body">
                    <div className="m-0 font-weight-bold text-darkblue">
                        <h2>코멘트</h2>
                        <textarea onChange={this.handleContentChange} /> 
                    </div>

                    <div>
                    <button type="submit" onClick={this.onFormSubmit} >Upload</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default AttachmentForm;