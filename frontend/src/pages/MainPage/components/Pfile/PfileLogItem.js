import React, { Component } from 'react';


class PfileLogItem extends Component {

    static defaultProps = {
        pfileLog: {
            name: '',
            contents: '',
            date : '',
            stat : '',
        }
    }

    render() {
        const { pfileLog, idx } = this.props;

        console.log('pfile log Item render');
        return (
            <div class="row" key={idx} >
                    <div class="col-6">{pfileLog.name}</div>
                    <div class="col-3">{pfileLog.stat}</div>
                    <div class="col-2">{pfileLog.newDate}</div>
                </div>
        );
    }
}

export default PfileLogItem;