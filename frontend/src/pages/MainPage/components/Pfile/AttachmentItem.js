import React, { Component } from 'react';


class AttachmentItem extends Component {

    static defaultProps = {
        attachment: {
            name: '이름',
            volume: 1000,
        }
    }

    handleMouseOver = (e) => {
        e.target.parentNode.style.backgroundColor = 'rgb(154, 154, 172)'
    }

    handleMouseOut = (e) => {
        e.target.parentNode.style.backgroundColor = ''
    }

    render() {
        const { attachment, idx } = this.props;

        console.log('attachment Item render');
        return (
            <div class="row" key={idx} onClick={() => this.props.onClickAttachment(attachment)}
                onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>
                    <div class="col-9 text-dark-1">{attachment.attachmentName}</div>
                    <div class="col-2 text-dark-1">첨부 파일</div>
                    <div class="col-1 ">
                        <span class="badge badge-danger bg-danger btn-sm" onClick={() => this.props.onClickDeleteAttachment(attachment.attachmentId)}>
                             삭제
                        </span>
                    </div>
                    <hr></hr>
                </div>
        );
    }
}

export default AttachmentItem;