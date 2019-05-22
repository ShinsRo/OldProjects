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

    handleCheckBox = (e) => {
        const {addDownloadGroup, deleteDownloadGroup, attachment} = this.props;

        if(e.target.checked){
            addDownloadGroup(attachment);
        } else {
            deleteDownloadGroup(attachment);
        }
    }

    onDragStart(e, target) {
        if (e.target.draggable && target) {
            e.dataTransfer.setData('type', 'ATTACHMENT');
            e.dataTransfer.setData('from', JSON.stringify(target));
        }
        e.dataTransfer.setData('draggable', e.target.draggable);

        console.log(e.dataTransfer)
    }

        

    render() {
        const { attachment, idx } = this.props;
        
        return (
            <div className="row m-0" key={idx} draggable={true} onDragStart={(e) => {this.onDragStart(e, attachment)}}>
                    <div className="col-1 text-dark-1 border-right">
                        <input type="checkbox" aria-label="Checkbox for following text input" style={{width:"17px", height:"17px", alignSelf:"center"}}
                            onClick={this.handleCheckBox}/>
                    </div>
                    <div className="col-8 text-dark-1 border-right" >
                        <div className="m-0 p-0" onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}
                            onClick={() => this.props.onClickAttachment(attachment)} style={{height:'100%', width: '100%', cursor: "pointer"}}>
                            {attachment.attachmentName}
                        </div>
                    </div>
                    <div className="col-2 text-dark-1 border-right">
                        첨부파일
                    </div>
                    <div className="col-1 ">
                        <span className="badge badge-danger bg-danger btn-sm" onClick={() => this.props.onClickDeleteAttachment(attachment.attachmentId)} style={{cursor: 'pointer'}}>
                             삭제
                        </span>
                    </div>
                    <hr></hr>
                </div>
        );
    }
}

export default AttachmentItem;
