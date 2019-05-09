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

    handleMouseOver = (e) => {
        e.target.parentNode.style.backgroundColor = "rgb(154, 154, 172)"
    }

    handleMouseOut = (e) => {
        e.target.parentNode.style.backgroundColor = ""
    }

    handleClick = () => {
        const {pfileLog , setPfileLog, projSaveItem} = this.props;
        setPfileLog(pfileLog);
        projSaveItem({ detailViewLevel: 'pfileLog' });
    }

    render() {
        const { pfileLog, idx } = this.props;

        console.log('pfile log Item render');
        return (
            <div class="row" key={idx} onClick={this.handleClick} 
            onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>
                    <div class="col-6">{pfileLog.name}</div>
                    <div class="col-3">{pfileLog.stat}</div>
                    <div class="col-3">{pfileLog.newDate}</div>
                </div>
        );
    }
}

export default PfileLogItem;