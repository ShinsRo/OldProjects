import React, { Component } from 'react';


class AttachmentItem extends Component {

    static defaultProps = {
        attachment: {
            name: '이름',
            volume: 1000,
        }
    }

    render() {
        const { attachment } = this.props;

        console.log('pfile Item render');
        return (
            <tr>
                <td>{attachment.name}</td>
                <td>첨부파일</td>
                <td>{attachment.volume}</td>
                <td>d</td>
                <td>f</td>
                <td>s</td>
            </tr>
        );
    }
}

export default AttachmentItem;