import React, { Component } from 'react';

class AttachmentForm extends Component {

    state = {
        pfile: {},
        coment: '',
    };

    onFormSubmit = (e) => {

        const { saveItem, reloadPLog, selectedProject } = this.props;
        e.preventDefault() // Stop form submit
        this.attachmentUpload(this.state.uploadAttachment)
            .then(() => {
                this.setState({ uploadAttachment: '' })
                reloadPLog(selectedProject.pid);
                saveItem({ detailViewLevel: 'attachment' });
            })
    }

    handleContentChange = (e) => {
        this.setState({ coment: e.target.value })
    }

    onFileChange = (e) => {
        if(e.target.files[0].size > 1048576){
            window.alert('파일 용량이 초과하였습니다. \n최대 용량은 1048.576 KB 입니다.')
        }else {
            this.setState({ uploadAttachment: e.target.files[0] })
        }
    }

    attachmentUpload = (file) => {
        const { saveAttachment, selectedDirId } = this.props;

        const formData = new FormData();

        const AttachmentReqDto = {
            'pdir': selectedDirId,
            'coment': this.state.coment,
        }

        //setAttachment(file);

        formData.append('file', file);
        formData.append('json', JSON.stringify(AttachmentReqDto));
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        return saveAttachment(formData, config)
    }


    render() {

        return (
                <div className="card shadow mb-4" style={{ height: '100%', width: '100%'}}>
                    <div className="card-header py-3">
                    <div className="row font-weight-bold text-dark-1" style={{textAlign: "center" ,marginLeft:"10px", fontSize:'25px'}}>
                        첨부파일을 추가합니다
                    </div>  
                    </div>

                    <div className="card-body"> 
                        <div className="row justify-content-end mb-2">
                            <div className="col-2 font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                                첨부 파일
                            </div>
                            <div className="col-10 pr-5">
                            <div class="custom-file">
                                <input type="file"
                                    class="custom-file-input" id="inputGroupFile01"
                                    aria-describedby="inputGroupFileAddon01"
                                    onChange={this.onFileChange}/>

                                <label class="custom-file-label" htmlFor="inputGroupFile01">
                                    {this.state.uploadAttachment ? this.state.uploadAttachment.name : "Choose file"}
                                </label>
                            </div>
                            </div>
                        </div>  

                        <hr/>

                        <div className="row mb-4" style={{ width: '100%' }}>
                            <div className="col-2 font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                                코멘트
                            </div>
                            <div className="col">
                                <textarea rows='9' style={{ resize: 'none' }} className="form-control" onChange={this.handleContentChange} />
                            </div>
                        </div>

                        

                        <div className="row justify-content-end mt-3 mr-4">
                            <button className="btn btn-dark-1 p-2" onClick={this.onFormSubmit} >Upload</button>
                        </div>
                    </div>
                </div>
        );
    }
}

export default AttachmentForm;