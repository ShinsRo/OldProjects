import React, { Component } from 'react';
import axios from 'axios';
import { saveAs } from 'file-saver';
import base64 from 'base-64'

class AttachmentPanel extends Component {

    state = {
        
    }

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

            var strList = response.data;
            
            console.log(typeof strList);
            console.log(strList.length);
            
            strList.forEach(element => {
                console.log(element.length);
            });
            
            var str = strList.join('');
            
            console.log(str.length);
            console.log(str);
       

            var decoded = base64.decode(str);

            var blob = new Blob([base64.decode(str)], {type: "octet/stream"});
            // var url = URL.createObjectURL(blob);
            // var a = document.createElement("a");
            // a.href = url;
            // a.url = "file-" + new Date().getTime();
            // document.body.appendChild(a);
            // a.click()

            saveAs(blob, attachment.attachmentName);
        })
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