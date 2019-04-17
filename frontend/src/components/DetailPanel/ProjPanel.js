import React, { Component } from 'react';

class ProjPanel extends Component {

    render() {
        return (<>
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <div className="m-0 font-weight-bold text-darkblue">
                        프로젝트 패널 타이틀
                    </div>
                </div>
                <div className="card-body">
                    프로젝트 패널 내용
                </div>
            </div>
        </>);
    }
}
export default ProjPanel;