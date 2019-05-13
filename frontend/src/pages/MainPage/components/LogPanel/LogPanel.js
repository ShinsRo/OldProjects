import React, { Component } from 'react';

class LogPanel extends Component {
    constructor(props) {
        super(props);

        this.state = {
            HEADER_ORDER: ['newDate', 'name', 'pdirName', 'logType', 'logState'],
            // '시간', '이름', '대상', '분류', '행동'
            HEADER_NAME: [
                { title: 'NO', colspan: '1' },
                { title: '시간', colspan: '3' },
                { title: '이름', colspan: '4' },
                { title: '대상', colspan: '1' },
                { title: '분류', colspan: '1' },
                { title: '행동', colspan: '1' },
            ]
        };
    }

    render() {
        const { HEADER_ORDER, HEADER_NAME } = this.state;
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
                
                <div className="container" id="LogTable" style={{ width:'100%'}}>
                    <div className="row" style={{ width:'100%',textAlign:"center", fontSize:"15px"}}>
                        <div className="col-1">NO</div>
                        <div className="col-3">시간</div>
                        <div className="col-5">이름</div>
                        <div className="col-1">대상</div>
                        <div className="col-1">분류</div>
                        <div className="col-1">행동</div>                                                     
                    </div>

                    <hr className="m-0"></hr>

                    <div className="row m-0" style={{ width:'100%' , overflowX :'auto', overflowY:'scroll' }}>
                        {logs.map((log, idx) => {
                            return (
                            <div className="row" onClick={() => {onClickLog(log)}} style={{ width:'100%', textAlign:"center"}}>
                                <div className="col-1">{idx + 1}</div>
                                <div className="col-3" style={{ textAlign:"center"}}>{log.newDate}</div>
                                <div className="col-5" style={{ textAlign:"center"}}>{log.name}</div>
                                <div className="col-1" style={{ textAlign:"center"}}>{log.pdirName}</div>
                                <div className="col-1" style={{ textAlign:"center"}}>{log.logType}</div>
                                <div className="col-1" style={{ textAlign:"center"}}>{log.logState}</div>
                            </div>
                            );
                        })}
                    </div>
                    
                </div>
                    
            </>);
        }

        return (<>
            <div className="card shadow mb-4"  style={{ height: '100%', width: '100%'}}>
                <div className="card-header font-weight-bold text-dark-1" style={{textAlign: "center"}}>로그</div>
                <div className="card-body">
                    {content}
                </div>
            </div>
        </>);
    }
}

export default LogPanel;