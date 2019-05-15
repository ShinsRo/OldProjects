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
            <div class="row" key={idx} onClick={() => this.props.onClickAttachment(attachment)}>
                    <div class="col-1 ml-0.5 text-dark-1 align-self-center">
                        <input type="checkbox" aria-label="Checkbox for following text input" style={{width:"17px", height:"17px", alignSelf:"center"}}/>
                    </div>
                    <div class="col-8 text-dark-1" onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>{attachment.attachmentName}</div>
                    <div class="col-2 text-dark-1" onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>
                        {/* <img
                            src={process.env.PUBLIC_URL + '/resources/img/' + image} 
                            alt="log does not exist"
                            style={{ width: '31px', height:'18px' }}
                        /> */}
                        첨부파일
                    </div>
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
