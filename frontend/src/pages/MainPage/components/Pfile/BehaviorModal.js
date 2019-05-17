import React, { Component } from 'react';
import stores from '../../../../stores'

class BehaviorModal  extends Component {

    onclickMove = () => {
        const { dropInfo, movePfile, moveAttachment, getPLog} = this.props;
        const { type, from, to } = dropInfo;
        const store = stores.getState();
        const project = store.projectState.get('selectedProject');
        const isGetLog = (project.pid === to.pid);


        console.log(from);
        console.log(to);
        console.log(type);
        console.log(store);

        switch ( type ) {
            case "pfile" :
                console.log(from.pfileId, to.id);
                movePfile(from.pfileId, to.id).then(() => {
                    isGetLog && getPLog(to.pid);
                });
                break;
            
            case "attachment" :
                console.log(from);
                console.log(to);
                moveAttachment(from.attachmentId, to.id).then(() => {
                    isGetLog && getPLog(to.pid);
                });
                break;
            
            default:
                break;           
        }       
    }

    onclickCopy = () => {
        const { dropInfo, copyPfile, copyAttachment, getPLog} = this.props;
        const { type, from, to } = dropInfo;
        const store = stores.getState();
        const project = store.projectState.get('selectedProject');
        const isGetLog = (project.pid === to.pid);

        console.log(dropInfo);

        switch ( type ) {
            case "pfile" :
                copyPfile(from.pfileId, to.id).then(() => {
                    isGetLog && getPLog(to.pid);
                });
                break;
            
            case "attachment" :
                copyAttachment(from.attachmentId, to.id).then(() => {
                    isGetLog && getPLog(to.pid);
                });
                break;            
            default:
                break;           
        }
    }

    render() {


        return (
            <div className="modal" id="BehaviorModal" tabIndex="-1" role="dialog">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <div className="modal-title">What?</div>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="text-dark-1 font-weight-bold" style={{font:'40px'}}>무엇을 하시겠습니까?</div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-dark-1" data-dismiss="modal" onClick={this.onclickMove}>이동</button>
                            <button type="button" className="btn btn-dark-1" data-dismiss="modal" onClick={this.onclickCopy}>복사</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default BehaviorModal;