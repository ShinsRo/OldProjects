import React, { Component } from 'react';


class AttachmentItem extends Component {

    static defaultProps = {
        attachment: {
            name: '이름',
            volume: 1000,
        }
    }



    render() {
        const { attachment, idx } = this.props;

        console.log('attachment Item render');
        return (
            <div class="row" key={idx} >
                    <div class="col-8">{attachment.attachmentName}</div>
                    <div class="col-2">첨부 파일</div>
                    <div class="col-1">
                        <span class="badge badge-primary btn-dark-1" onClick={() => this.props.onClickAttachment(attachment)}>
                            보기
                        </span>
                    </div>
                    <div class="col-1">
                        <span class="badge badge-danger bg-danger btn-sm" onClick={() => this.props.onClickDeleteAttachment(attachment.attachmentId)}>
                             삭제
                        </span>
                    </div>
                </div>
        );
    }
}

export default AttachmentItem;