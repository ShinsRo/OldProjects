import React, { Component } from 'react';

class PfilePanel extends Component {

    state = {
        isUpdate: false,
    }

    onClickUpdateBts = (e) => {

        e.preventDefault();
        const { handleUpdateBts } = this.props;
        handleUpdateBts({ detailViewLevel: 'pfileUpdate' });
    }

    onClickCancleBts = (e) => {
        e.preventDefault();
        const { handleUpdateBts } = this.props;
        handleUpdateBts({ detailViewLevel: 'pfileUpdate' });
    }

    render() {

        const pfile = this.props.pfileState.get('pfile');

        return (

            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <div className="m-0 font-weight-bold text-darkblue">
                        파일
                        </div>
                </div>

                <div className="card-body">
                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-darkblue"><h2 >제목</h2></div>
                        </div>
                        <div className="col-10">
                            <div className="text-darkblue">{pfile.name}</div>
                        </div>
                    </div>

                    <hr />

                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-darkblue"><h2 >내용</h2></div>
                        </div>
                        <div className="col-10" style={{ height:'240px'  ,overflowY:'scroll'}}>
                            <div className="text-darkblue">{pfile.contents}</div>
                        </div>
                    </div>


                    <div className="row justify-content-end">
                        <div className="btn-group" role="group" aria-label="Basic example">
                            <button type="button" className="btn btn-dark-1 p-2" onClick={this.onClickUpdateBts}>
                                수정 하기
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}
export default PfilePanel;