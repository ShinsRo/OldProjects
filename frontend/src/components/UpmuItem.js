import React, { Component } from 'react';


class UpmuItem extends Component {

    static defaultProps = {
        upmu: {
            name: '이름',
            content: '내용',
        }
    }

    render() {
        const { upmu } = this.props;

        return (
            <div style={style}>
                <div><b>{upmu.name}</b></div>
                <div>{upmu.content}</div>
            </div>
        );
    }
}