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
                        <button class="btn btn-info btn-circle btn-sm" onClick={() => this.props.onClickAttachment(attachment)}>
                            <i class="fas fa-info-circle"></i>
                        </button>
                    </div>
                    <div class="col-1">
                        <button class="btn btn-danger btn-circle btn-sm">
                             <i class="fas fa-divash"></i>
                        </button>
                    </div>
                </div>
        );
    }
}

export default AttachmentItem;