import React, { Component } from 'react';
import axios from 'axios';
import { saveAs } from 'file-saver';
import base64 from 'base-64'
import { BASE_URL } from '../../../../supports/API_CONSTANT';

class AttachmentPanel extends Component {

    state = {
        
    }

    onClickDownload = (attachmentId) => {
        const {downloadAttachment} = this.props;
        
        downloadAttachment(attachmentId);
    }

    download = () => {
        const {attachment} = this.props;
        const link = document.createElement('a');
        link.href = `${BASE_URL}/attachment/download/${attachment.attachmentId}`;
        link.download = "ffffffffffff";
        document.body.appendChild(link);        
        link.click();
        document.body.removeChild(link);
    }

    render() {
        const { attachment } = this.props;
        console.log(attachment);

        return (
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <div className="m-0 font-weight-bold text-bright-1">
                        <div className="row text-bright-1">
                            <h2 >제목</h2>
                            <div className="col-4">
                                <button type="button" className="btn btn-dark-1 p-2" onClick={this.download}>                        
                                    다운로드
                                </button>
                            </div>
                        </div>
                            <hr/>
                        <div className="text-bright-2">{attachment.attachmentName}</div>
                    </div>
                </div>

                <hr/>
                <div className="card-body">
                    <div className="m-0 font-weight-bold text-bright-1">
                        <div className="text-bright-1"><h2 >코멘트</h2></div>
                            <hr/>
                    <div className="text-bright-2">{attachment.coment}</div>

                    </div>
                </div>

                <hr/>
                <div className="card-body">
                    <div className="m-0 font-weight-bold text-bright-1">
                        <div className="text-bright-1"><h2 >용량</h2></div>
                            <hr/>
                    <div className="text-bright-2">{attachment.volume} byte</div>

                    </div>
                </div>

                <hr/>
                <div className="card-body">
                    <div className="m-0 font-weight-bold">
                        <div className="text-bright-1"><h2>생성일</h2></div>
                        <hr/>
                        <div className="text-bright-2">{attachment.newDate[0]}년 {attachment.newDate[1]}월 {attachment.newDate[2]}일</div>
                    </div>
                </div>
            
            </div>
        );
    }
}

export default AttachmentPanel;