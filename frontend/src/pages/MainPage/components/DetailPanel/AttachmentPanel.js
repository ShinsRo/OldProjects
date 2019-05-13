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
                    <div className="m-0 font-weight-bold text-darkblue">
                        <div className="row text-darkblue justify-content-between">
                            <div className="col-4">
                                첨부파일
                            </div>
                            <div className="col-2">
                                <button type="button" className="btn btn-dark-1 p-2" onClick={this.download}>
                                    다운로드
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="card-body">
                    <div className="row">
                        <div className="col">
                            <div className="font-weight-bold text-darkblue"><h2 >제목</h2></div>
                            <div className="text-darkblue">{attachment.attachmentName}</div>
                        </div>
                    </div>

                    <hr/>

                    <div className="row">
                        <div className="col">
                            <div className="font-weight-bold text-darkblue"><h2 >코멘트</h2></div>
                            <div className="text-darkblue">{attachment.coment ? attachment.coment : "내용 없음"}</div>
                        </div>
                    </div>

                    <hr />

                    <div className="row">
                        <div className="col">
                            <div className="font-weight-bold text-darkblue"><h2 >용량</h2></div>

                            <div className="text-darkblue">{attachment.volume} byte</div>
                        </div>

                        <div className="col">
                            <div className="font-weight-bold text-darkblue"><h2>생성일</h2></div>

                            <div className="text-darkblue">{attachment.newDate[0]}년 {attachment.newDate[1]}월 {attachment.newDate[2]}일</div>
                        </div>
                    </div>
                </div>

            </div>
        );
    }
}

export default AttachmentPanel;