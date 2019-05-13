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
        const { logs } = this.props;

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
                
                <table className="table table-sm text-sm">
                    <thead>
                        <tr>
                            {HEADER_NAME.map((hn, idx) => {
                                return (<th key={idx} colspan={hn.colspan} scope="col">{hn.title}</th>)
                            })}
                        </tr>
                    </thead>
                    <tbody>
                        {logs.map((log, idx) => {
                            return (
                            <tr key={idx}>
                                <td>{idx}</td>
                                {HEADER_ORDER.map((ho, jdx) => {
                                    return (<td key={ho} colspan={HEADER_NAME[jdx + 1].colspan}>{log[ho]}</td>)
                                })}
                            </tr>
                            );
                        })}
                    </tbody>
                </table>
                    
            </>);
        }

        return (<>
            <div className="card shadow mb-4"  style={{ height: '100%', width: '100%'}}>
                <div className="card-header">로그</div>
                <div className="card-body">
                    {content}
                </div>
            </div>
        </>);
    }
}

export default LogPanel;