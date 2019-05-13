import React, { Component } from 'react';
import { BASE_URL } from '../../../../supports/API_CONSTANT';

class AttachmentPanel extends Component {

    state = {

    }

    onClickDownload = (attachmentId) => {
        const { downloadAttachment } = this.props;

        downloadAttachment(attachmentId);
    }

    download = () => {
        const { attachment } = this.props;
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
                    <div className="m-0 font-weight-bold text-dark-1">
                        <div className="row text-dark-1 justify-content-center">
                            <div className="col" style={{textAlign: "center"}}>
                                첨부파일
                            </div>
                            
                        </div>
                    </div>
                </div>

                <div className="card-body">
                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-dark-1" style={{textAlign: "center"}}>제목</div>
                        </div>

                        <div className="col-10">
                            <textarea className="form-control text-dark-1" rows='1' value={attachment.attachmentName} readOnly style={{ resize: 'none' }}/>
                        </div>
                    </div>

                    <hr/>

                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-dark-1" style = {{textAlign: "center"}}>코멘트</div>
                        </div>

                        <div className="col-10">
                            <textarea className="form-control text-dark-1" rows="6" readOnly value={attachment.coment ? attachment.coment : "내용 없음"} style={{ resize: 'none' }}/>
                        </div>
                    </div>

                    <hr />

                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-dark-1" style = {{textAlign: "center"}}>용량</div>
                        </div>

                        <div className="col-4">
                            <textarea className="form-control text-dark-1" value={attachment.volume + ' byte'} readOnly rows='1' style={{ resize: 'none' }}/>
                        </div>                        

                        <div className="col-2">
                            <div className="font-weight-bold text-dark-1" style = {{textAlign: "center"}}>생성일시</div>
                        </div>

                        <div className="col-4">
                            <textarea className="form-control text-dark-1" rows='1' readOnly value={attachment.newDate} style={{ resize: 'none' }}/>
                        </div>
                    </div>

                    <hr></hr>

                    <div className="row justify-content-center">                    
                        <button type="button" className="btn btn-dark-1 p-2" onClick={this.download}>
                            다운로드
                        </button>                
                    </div>
                </div>
            </div>
        );
    }
}

export default AttachmentPanel;
