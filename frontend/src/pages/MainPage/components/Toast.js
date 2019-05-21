import React, { Component } from 'react';

class Toast extends Component {

    render() {
        return(<>
            <div id="toastContainer" className="toast" data-delay="3000">
                <div className="toast-header">
                    <i className="fas fa-exclamation-triangle mr-2"></i>
                    <strong id="toastHeader" className="mr-auto font-weight-bold text-black"></strong>
                    <small id="toastTime"></small>
                    <button type="button" className="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div className="toast-body text-black">
                    <span id="toastBody"></span>
                </div>
            </div>
        </>);
    }
}

export default Toast;