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

    render() {
        const { attachmentLog, idx } = this.props;

        console.log('AttachmentLog log Item render');
        return (
            <div class="row" key={idx} >
                    <div class="col-6">{attachmentLog.name}</div>
                    <div class="col-2">{attachmentLog.contentType}</div>
                    <div class="col-2">{attachmentLog.stat}</div>
                    <div class="col-2">{attachmentLog.newDate}</div>
                </div>
        );
    }
}

export default AttachmentLogItem;