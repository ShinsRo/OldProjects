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

        const { pfileState } = this.props;

        console.log(pfileState.get('pfile').name);

        return (
            
                <div className="card shadow mb-4">
                    <div className="card-header py-3">
                        <div className="m-0 font-weight-bold text-darkblue">
                            {pfileState.get('pfile').name}
                        </div>
                    </div>
                    <div className="card-body">
                        <div>
                            {pfileState.get('pfile').contents}
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
