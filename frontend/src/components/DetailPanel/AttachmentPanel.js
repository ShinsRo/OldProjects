import React, { Component } from 'react';
import axios from 'axios';
import { saveAs } from 'file-saver';

class AttachmentPanel extends Component {

    onClickDownload = (attachmentId) => {
        const {downloadAttachment} = this.props;
        
        downloadAttachment(attachmentId);
    }

    download = () => {
        const {attachment} = this.props;
        //var FileSaver = require('file-saver');

        axios({
            url: `http://localhost:8080/upmureport/attachment/download/${attachment.attachmentId}`,
            method: 'GET',
            
          }).then((response) => {
            const blob = new Blob([response.data], {type: "octet/stream"});
            console.log(blob);
            saveAs(blob, attachment.attachmentName);
            // const url = URL.createObjectURL(blob);
            // const link = document.createElement('a');
            console.log(response.data);
            // console.log(url);

            // link.href = url;
            // link.setAttribute('download', attachment.attachmentName);
            // document.body.appendChild(link);
            // link.click();
          });
    }

    render() {
        const { attachment } = this.props;
        console.log(attachment);

        return (
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <div className="m-0 font-weight-bold text-darkblue">
                        <h2>제목</h2>
                        <a>{attachment.attachmentName}</a>
                    </div>
                </div>
                <div className="card-body">
                    <div className="m-0 font-weight-bold text-darkblue">
                    <h2>용량</h2>
                    <a>{attachment.volume}</a>
                    </div>
                </div>

                <div className="card-body">
                    <div className="m-0 font-weight-bold text-darkblue">
                    <h2>생성일</h2>
                    <a>{attachment.newDate}</a>
                    </div>
                </div>
                
                <div>
                <button type="button" className="btn btn-info btn-icon-split" onClick={this.download}>
                        <span className="icon text-white-50">
                            <i className="fas fa-info-circle"></i>
                        </span>
                        <span className="text">다운로드</span>
                    </button>
                </div>
            </div>
        );
    }
}

export default AttachmentPanel;