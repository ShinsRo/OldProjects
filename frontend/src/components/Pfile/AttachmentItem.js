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

        console.log('pfile Item render');
        return (
            <tr key={idx} >
                    <td>{attachment.attachmentName.substring(0,9)}</td>
                    <td>{attachment.volume}</td>
                    <td>첨부</td>
                    <td>첨부</td>
                    <td>첨부</td>
                    <td>첨부</td>
                        <td>
                            <button class="btn btn-info btn-circle btn-sm">
                                <i class="fas fa-info-circle"></i>
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-danger btn-circle btn-sm">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                </tr>
        );
    }
}

export default AttachmentItem;