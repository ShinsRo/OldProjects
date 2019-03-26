import React, { Component } from 'react';


class UpmuItem extends Component {

    static defaultProps = {
        upmu: {
            name: '이름',
            contents: '내용',
        }
    }

    render() {
        const { upmu } = this.props;

        console.log('upmu Item render');
        return (
            <div>
                <div><b>{upmu.name}</b></div>
                <div>{upmu.contents}</div>
            </div>
        );
    }
}

export default UpmuItem;