import React, { Component } from 'react';

class LogPanel extends Component {
    
    render() {

        const { logs, onClickLog } = this.props;
        
        
        let content = (
            <div className="text-center">
                    
                <img
                    src={process.env.PUBLIC_URL + '/resources/img/undraw_no_data_qbuo.svg'} 
                    alt="log does not exist"
                    style={{ width: '200px' }}
                />

                <div>로그가 없습니다!</div>
            </div>
        );

        if (logs.length) {
            content = (<>
                
                <div className="container m-0 p-0" id="LogTable" style={{ width:'100%' }}>
                    <div className="row" style={{ width:'100%',textAlign:"center", fontSize:"15px"}}>
                        <div className="col-1">NO</div>
                        <div className="col-4">이름</div>
                        <div className="col-3">시간</div>
                        <div className="col-2 pl-0">대상</div>
                        <div className="col-2 pl-0">분류</div>
                        {/* <div className="col-1">행동</div>                                                      */}
                    </div>
                </div>

                <div className="container m-0 p-0" style={{ width:'100%', overflowY:'auto', height:"240px" }}>
                    <hr className="m-0"></hr>

                    <div className="row m-0 p-0" style={{ width:'100%', overflowX :'auto' }}>
                        {logs.map((log, idx) => {
                            return (
                            <div className="row" onClick={() => {onClickLog(log)}} style={{ width:'100%',  textAlign:"center", fontSize: '15px'}}>
                                <div className="col-1">{idx + 1}</div>
                                <div className="col-4" style={{ textAlign:"center"}}>{log.name}</div>
                                <div className="col-3" style={{ textAlign:"center"}}>{log.newDate}</div>
                                <div className="col-2" style={{ textAlign:"center"}}>{log.pdirName}</div>
                                <div className="col-2" style={{ textAlign:"center"}}>{log.logType}</div>
                                {/* <div className="col-1" style={{ textAlign:"center"}}>{log.logState}</div> */}
                            </div>
                            );
                        })}
                    </div>
                    
                </div>
                    
            </>);
        }

        return (<>
            <div className="card shadow mb-4"  style={{ width: '100%'}}>
                <div className="card-header font-weight-bold text-dark-1 m-0" style={{textAlign: "center"}}>로그</div>
                <div className="card-body m-0">
                    {content}
                </div>
            </div>
        </>);
    }
}

export default LogPanel;
