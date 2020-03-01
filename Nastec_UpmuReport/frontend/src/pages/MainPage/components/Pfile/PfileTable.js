import React from 'react';
import AttachmentItem from './AttachmentItem' ;
import PfileItem from './PfileItem';

class PfileTable extends React.Component {

    componentWillReceiveProps(nextProps){
        if(this.props.selectedDirId !== nextProps.selectedDirId){
            this.props.clearDownloadGroup();
        }
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

                                onClickPfile = {this.props.handleClickPfile}
                                onClickPfileDelete = {this.props.handleClickDeletePfile}
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

                                        onClickAttachment = {this.props.handleClickAttachment}
                                        onClickDeleteAttachment = {this.props.handleClickDeleteAttachment}
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
