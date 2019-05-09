import React, { Component } from 'react';


class AttachmentLogItem extends Component {

    static defaultProps = {
        attachmentLog: {
            name: '',
            contents: '',
            date : '',
            contentType : '',
            stat : '',
        }
    }

    handleMouseOver = (e) => {
        e.target.parentNode.style.backgroundColor = "rgb(154, 154, 172)"
    }

    handleMouseOut = (e) => {
        e.target.parentNode.style.backgroundColor = ""
    }

    handleClick = () => {
        const {attachmentLog , setAttachmentLog, projSaveItem} = this.props;
        setAttachmentLog(attachmentLog);
        projSaveItem({ detailViewLevel: 'attachmentLog' });
    }

    render() {
        const { attachmentLog, idx } = this.props;

        console.log('AttachmentLog log Item render');
        return (
            <div class="row" key={idx} onClick={this.handleClick}
            onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>
                    <div class="col-6">{attachmentLog.name}</div>
                    <div class="col-3">{attachmentLog.stat}</div>
                    <div class="col-3">{attachmentLog.newDate}</div>
                </div>
        );
    }
}

export default AttachmentLogItem;