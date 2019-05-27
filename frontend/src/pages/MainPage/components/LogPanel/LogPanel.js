import React, { Component } from 'react';

class LogPanel extends Component {

    handleMouseOver = (e) => {
        e.target.parentNode.style.backgroundColor = "rgb(154, 154, 172)"
    }

    handleMouseOut = (e) => {
        e.target.parentNode.style.backgroundColor = ""
    }

    handleType = (type) => {
        if(type === 'PFILE'){
            return '업무'
        } else {
            return '첨부파일'
        }
    }

    handleName = (name) => {
        if(name.length >= 25){
            return name.substring(0,25) + ' ...'
        } else {
            return name;
        }
    }
    
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
                    <div className="row text-dark-1 text-bold" style={{ width:'100%', fontSize:"18px"}}>
                        <div className="col-1" style={{textAlign:"center"}}>NO</div>
                        <div className="col-4">제목</div>
                        <div className="col-3">일시</div>
                        <div className="col-2 pl-0">대상</div>
                        <div className="col-2 pl-0">분류</div>
                        {/* <div className="col-1">행동</div>                                                      */}
                    </div>
                </div>

                <div className="container m-0 p-0" style={{ width:'100%', overflowY:'auto', height:"240px" }}>
                    <hr className="m-0"></hr>

                    <div className="row m-0 p-0 text-dark-1" style={{ width:'100%', overflowX :'auto' }} onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}> 
                        {logs.map((log, idx) => {
                            return (
                            <div className="row" onClick={() => {onClickLog(log)}} style={{ width:'100%', fontSize: '15px'}} key={idx}>
                                <div className="col-1" style={{textAlign:"center"}}>{idx + 1}</div>
                                <div className="col-4">{this.handleName(log.name)}</div>
                                <div className="col-3">{log.newDate}</div>
                                <div className="col-2">{log.pdirName}</div>
                                <div className="col-2">{this.handleType(log.logType)}</div>
                                {/* <div className="col-1" style={{ textAlign:"center"}}>{log.logState}</div> */}
                            </div>
                            );
                        })}
                    </div>                    
                </div>
                    
            </>);
        }

        return (<>
            <div className="card shadow"  style={{ width: '100%'}}>
                <div className="card-header font-weight-bold text-dark-1 m-0" style={{fontSize:"25px"}}>로그</div>
                <div className="card-body m-0">
                    {content}
                </div>
            </div>
        </>);
    }
}

export default LogPanel;
