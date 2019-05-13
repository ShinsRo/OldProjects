import React, { Component } from 'react';

class LogPanel extends Component {

    state = {

    }



    render() {
        const { pfileLog, attachmentLog } = this.props;

        if (this.props.status === 'pfile') {
            return (
                <div className="card shadow mb-4" style={{ width: '100%' }}>
                    <div className="card-header py-3">
                        <div className="m-0 font-weight-bold text-darkblue">
                            파일 로그
                        </div>
                    </div>
                    <div className="card-body">

                        <div className="row">
                            <div className="col-2">
                                <div className="font-weight-bold text-darkblue"><h2 >제목</h2></div>
                            </div>
                            <div className="col-10">
                                <div className="text-darkblue">{pfileLog.name}</div>
                            </div>
                        </div>

                        <div className="row mb-3" style={{ height: '300px', overflowY: 'scroll' }}>
                            <div className="col-2">
                                <div className="font-weight-bold text-darkblue" ><h2 >내용</h2></div>
                            </div>
                            <div className="col-10">
                                <div className="text-darkblue">{pfileLog.contents}</div>
                            </div>
                        </div>


                        <div className="row">
                            <div className="col-2 font-weight-bold text-darkblue">
                                <h2>일시</h2>
                            </div>

                            <div className="col-4 text-darkblue">
                                {pfileLog.newDate}
                            </div>

                            <div className="col-2 font-weight-bold text-darkblue">
                                <h2>상태</h2>
                            </div>

                            <div className="col-4 text-darkblue">
                                {pfileLog.stat}
                            </div>
                        </div>
                    </div>
                </div>
            )

        } else if (this.props.status === 'attachment') {
            return (
                <div className="card shadow mb-4" style={{ width: '100%' }}>
                    <div className="card-header py-3">
                        <div className="m-0 font-weight-bold text-darkblue">
                            첨부파일 로그
                        </div>
                    </div>
                    <div className="card-body">

                        <div className="row">
                            <div className="col-2">
                                <div className="font-weight-bold text-darkblue"><h2 >제목</h2></div>
                            </div>
                            <div className="col-10">
                                <div className="text-darkblue">{attachmentLog.name}</div>
                            </div>
                        </div>

                        <div className="row mb-3" style={{ height: '300px', overflowY: 'scroll' }}>
                            <div className="col-2">
                                <div className="font-weight-bold text-darkblue" ><h2 >내용</h2></div>
                            </div>
                            <div className="col-10">
                                <div className="text-darkblue">{attachmentLog.contents}</div>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-2 font-weight-bold text-darkblue">
                                <h2>일시</h2>
                            </div>

                            <div className="col-4 text-darkblue">
                                {attachmentLog.newDate}
                            </div>

                            <div className="col-2 font-weight-bold text-darkblue">
                                <h2>상태</h2>
                            </div>

                            <div className="col-4 text-darkblue">
                                {attachmentLog.stat}
                            </div>
                        </div>
                    </div>
                </div>
            )

        }
    }
}

export default LogPanel;