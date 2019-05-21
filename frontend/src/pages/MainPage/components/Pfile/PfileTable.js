import React from 'react';
import { List } from 'immutable';
import AttachmentItem from './AttachmentItem' ;
import PfileItem from './PfileItem';

class PfileTable extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
            dirs: List(),
            attachments: List(),
            updateModal: false,
            pfile: {},
            attahment : {},
            // attachmentGroup: [],
        };
    }

    componentWillReceiveProps(nextProps){
        if(this.props.selectedDirId !== nextProps.selectedDirId){
            this.props.clearDownloadGroup();
        }
    }

    onClickPfile = (pfile) => {
        const {handleClickPfile} = this.props;
        this.setState({ pfile: pfile })
        handleClickPfile(pfile);
    }

    onClickPfileDelete = (pfileId) => {        
        const {handleDeletePfile} = this.props;
        handleDeletePfile(pfileId);
    }

    onClickAttachment = (attahment) => {
        const {handleClickAttachment , addDownloadGroup} = this.props;

        this.setState({ attahment: attahment })
        handleClickAttachment(attahment);
        addDownloadGroup(attahment.attachmentId)
    }

    onClickDeleteAttachment = (attachmentId) => {
        const {handleDeleteAttachment} = this.props;

        handleDeleteAttachment(attachmentId);
    }

    onDragStart(e, target) {
        if (e.target.draggable && target) {
            e.dataTransfer.setData('type', 'FILE');
            e.dataTransfer.setData('from', JSON.stringify(target));
            //e.dataTransfer.setData('fromId', target.id);
        }
        e.dataTransfer.setData('draggable', e.target.draggable);

        console.log(e.dataTransfer)
    }

    render() {

        const {pfiles, attachments} = this.props;

        console.log(this.props)

        return (
            <div height="100px">
                <div className="container" id="PfileTable" cellSpacing="0" width="100%" >
                    <div className="row m-0">
                            <div className="col-1 text-dark-1 border-right"></div>
                            <div className="col-8 text-dark-1 border-right">제목</div>
                            <div className="col-2 text-dark-1 border-right">유형</div>                            
                            <div className="col-1 text-dark-1">삭제</div>
                            
                    </div>
                    <hr></hr>
                    <div style={{ height: '450px' , overflowX :'auto', overflowY:'auto' }}>
                        
                        {pfiles && pfiles.map((pfile, idx) => {
                            return(  
                            <PfileItem
                                key = {idx}
                                idx = {idx}
                                pfile = {pfile}
                                onClickPfile = {this.onClickPfile}
                                onClickPfileDelete = {this.onClickPfileDelete}
                                />
                            )
                        })}      

                        {attachments.length > 0 && <hr></hr>}

                        {attachments && attachments.map((attachment, idx) => {
                            return(   
                                    <AttachmentItem 
                                        key = {idx}
                                        idx = {idx}
                                        attachment = {attachment}
                                        onClickAttachment = {this.onClickAttachment}
                                        onClickDeleteAttachment = {this.onClickDeleteAttachment}
                                        addDownloadGroup = {this.props.addDownloadGroup}
                                        deleteDownloadGroup= {this.props.deleteDownloadGroup}
                                    />
                            )
                        })}     
                    </div>
                </div>
            </div>
        )
    }
}

export default PfileTable;
