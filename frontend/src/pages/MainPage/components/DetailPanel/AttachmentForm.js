import React, { Component } from 'react';

class AttachmentForm extends Component {

    state = {
        pfile: {},
        coment: '',
        isSuccess: '',
    };

    componentWillMount() {
        this.setState({ 'isSuccess': false })
    }

    onFormSubmit = (e) => {

        // const { saveItem } = this.props;
        e.preventDefault() // Stop form submit
        this.attachmentUpload(this.state.uploadAttachment)
            .then(() => {
                this.setState({ uploadAttachment: '' })
                //saveItem({ detailViewLevel: 'attachment'})
            })

        this.setState({ 'isSuccess': true })
    }

    handleContentChange = (e) => {
        // const {PfileActions} = this.props;
        // PfileActions.changeContentInput(e.target.value);
        this.setState({ coment: e.target.value })
    }

    onFileChange = (e) => {
        this.setState({ uploadAttachment: e.target.files[0] })
    }

    attachmentUpload = (file) => {
        const { saveAttachment, selectedDirId, setAttachment } = this.props;

        const formData = new FormData();

        const AttachmentReqDto = {
            'pdir': selectedDirId,
            'coment': this.state.coment,
        }

        setAttachment(file);

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


        if (this.state.isSuccess) {
            return (
                <div>
                    <div>성공!!</div>
                </div>
            );
        }

        return (
                <div className="card shadow mb-4" style={{ width: '100%' }}>
                    <div className="card-header py-3">
                        <div class="custom-file ">
                            <input type="file"
                                class="custom-file-input m-3" id="inputGroupFile01"
                                aria-describedby="inputGroupFileAddon01"
                                onChange={this.onFileChange} />
                            <label class="custom-file-label" for="inputGroupFile01">
                                {this.state.uploadAttachment ? this.state.uploadAttachment.name : "Choose file"}
                            </label>
                        </div>
                    </div>

                    <div className="card-body">
                        <div className="row" style={{ width: '100%' }}>
                            <div className="col-2 text-lg font-weight-bold text-darkblue" style={{textAlign: "center"}}>
                                코멘트
                            </div>
                            <div className="col">
                                <textarea rows='12' style={{ resize: 'none' }} className="form-control" onChange={this.handleContentChange} />
                            </div>
                        </div>

                        <div className="row justify-content-center mt-3">
                            <button className="btn btn-dark-1 p-2" onClick={this.onFormSubmit} >Upload</button>
                        </div>
                    </div>
                </div>
        );
    }
}

export default AttachmentForm;