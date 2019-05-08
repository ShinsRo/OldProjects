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
        };
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
        const {handleClickAttachment} = this.props;

        this.setState({ attahment: attahment })
        handleClickAttachment(attahment);
    }

    onClickDeleteAttachment = (attachmentId) => {
        const {handleDeleteAttachment} = this.props;

        handleDeleteAttachment(attachmentId);
    }

    render() {
        const {pfiles, attachments, selectedDirId} = this.props;
        return (
            <div overflow-x = "auto">
                <div className="container" id="PfileTable" cellSpacing="0" width="100%">
                    <div className="row">
                        
                            <div className="col-8">제목</div>
                            <div className="col-2">유형</div>
                            <div className="col-1">상세정보</div>
                            <div className="col-1">삭제</div>
                            
                    </div>
                    <div>
                        
                        {pfiles && pfiles.map((pfile, idx) => {
                            return(  
                            <PfileItem
                                idx = {idx}
                                pfile = {pfile}
                                onClickPfile = {this.onClickPfile}
                                onClickPfileDelete = {this.onClickPfileDelete}
                                />
                            )
                        })}

                        {attachments && attachments.map((attachment, idx) => {
                            return(
                            <AttachmentItem 
                                idx = {idx}
                                attachment = {attachment}
                                onClickAttachment = {this.onClickAttachment}
                                onClickDeleteAttachment = {this.onClickDeleteAttachment}
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