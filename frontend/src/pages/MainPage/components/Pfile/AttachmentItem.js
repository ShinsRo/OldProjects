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
            //e.dataTransfer.setData('fromId', target.id);
        }
        e.dataTransfer.setData('draggable', e.target.draggable);

        console.log(e.dataTransfer)
    }

        

    render() {
        const { attachment, idx } = this.props;

        // const jpgList = ['JPG', 'jpg', 'png', 'PNG'];
        // const pptList = ['ppt', 'pptx', 'PPT', 'PPTX'];
        // const excelList = ['xlsx', 'xlsm', 'xls' , 'XLSX', 'XLSM', 'XLS'];
        // const wordList = ['doc' , 'docx' , 'DOC', 'DOCX'];

        // let image;

        // let types = attachment.attachmentName.split('.');


        // if(jpgList.includes(types[types.length-1])){
        //     image = 'jpg.PNG';
        // } else if (pptList.includes(types[types.length-1])){
        //     image = 'ppt.PNG';
        // } else if (excelList.includes(types[types.length-1])){
        //     image = 'excel.PNG';
        // } else if (wordList.includes(types[types.length-1])){
        //     image = 'word.PNG';
        // } else {
        //     image = 'att.PNG'
        // }

        console.log('attachment Item render');
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
                        {/* <img
                            src={process.env.PUBLIC_URL + '/resources/img/' + image} 
                            alt="log does not exist"
                            style={{ width: '31px', height:'18px' }}
                        /> */}
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
