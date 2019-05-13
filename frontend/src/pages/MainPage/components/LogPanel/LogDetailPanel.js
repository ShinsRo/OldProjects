import React, { Component } from 'react';

class LogDetailPanel extends Component {

    state = {

    }

    handleBackButton = () => {
        const {handleLogViewLevel} = this.props;

        handleLogViewLevel('logList');
    }



    render() {
        // const { pfileLog, attachmentLog } = this.props;
        const { log } = this.props;

            return (
                <div className="card shadow mb-4" style={{ width: '100%' , height: '100%'}}>
                    <div className="card-header py-3">
                        <div className="row">
                            <div className="col-2 m-0 font-weight-bold text-dark-1 justify-content-center">
                                <button className="btn border border-dark" onClick={this.handleBackButton}>{'<<'}</button>
                            </div>
                            <div className="col-10 mt-2 font-weight-bold text-dark-1" style={{textAlign: "center"}}> 
                                파일 로그
                            </div>
                        </div>
                    </div>
                    <div className="card-body">

                        <div className="row mb-3">
                            <div className="col-2">
                                <div className="font-weight-bold text-dark-1" style={{textAlign: "center"}}>제목</div>
                            </div>
                            <div className="col-10">
                                <textarea className="form-control text-dark-1 " readOnly rows='1' style={{ resize: 'none' }} value={log.name}/>
                            </div>
                        </div>

                        <div className="row mb-3">
                            <div className="col-2">
                                <div className="font-weight-bold text-dark-1" style={{textAlign: "center"}}>내용</div>
                            </div>
                            <div className="col-10">
                                <textarea className="form-control text-dark-1 " readOnly rows='5' style={{ resize: 'none' }} value={log.contents}/>
                            </div>
                        </div>


                        <div className="row">
                            <div className="col-2 font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                                일시
                            </div>

                            <div className="col-4 text-dark-1">
                                <textarea className="form-control text-dark-1 " readOnly rows='1' style={{ resize: 'none' }} value={log.newDate}/>                                
                            </div>

                            <div className="col-2 font-weight-bold text-dark-1" style={{textAlign: "center"}}>
                                상태
                            </div>

                            <div className="col-4 text-dark-1">
                                <textarea className="form-control text-dark-1 " readOnly rows='1' style={{ resize: 'none' }} value={log.logState}/>
                            </div>
                        </div>
                    </div>
                </div>
            )

        // } else if (this.props.status === 'attachment') {
        //     return (
        //         <div className="card shadow mb-4" style={{ width: '100%' }}>
        //             <div className="card-header py-3">
        //                 <div className="m-0 font-weight-bold text-darkblue">
        //                     첨부파일 로그
        //                 </div>
        //             </div>
        //             <div className="card-body">

        //                 <div className="row">
        //                     <div className="col-2">
        //                         <div className="font-weight-bold text-darkblue"><h2 >제목</h2></div>
        //                     </div>
        //                     <div className="col-10">
        //                         <div className="text-darkblue">{attachmentLog.name}</div>
        //                     </div>
        //                 </div>

        //                 <div className="row mb-3" style={{ height: '300px', overflowY: 'scroll' }}>
        //                     <div className="col-2">
        //                         <div className="font-weight-bold text-darkblue" ><h2 >내용</h2></div>
        //                     </div>
        //                     <div className="col-10">
        //                         <div className="text-darkblue">{attachmentLog.contents}</div>
        //                     </div>
        //                 </div>

        //                 <div className="row">
        //                     <div className="col-2 font-weight-bold text-darkblue">
        //                         <h2>일시</h2>
        //                     </div>

        //                     <div className="col-4 text-darkblue">
        //                         {attachmentLog.newDate}
        //                     </div>

        //                     <div className="col-2 font-weight-bold text-darkblue">
        //                         <h2>상태</h2>
        //                     </div>

        //                     <div className="col-4 text-darkblue">
        //                         {attachmentLog.stat}
        //                     </div>
        //                 </div>
        //             </div>
        //         </div>
        //     )
    }
}

export default LogDetailPanel;