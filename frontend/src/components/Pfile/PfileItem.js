import React, { Component } from 'react';


class PfileItem extends Component {

    static defaultProps = {
        pfile: {
            name: '이름',
            contents: '내용',
        }
    }

    render() {
        const { pfile } = this.props;

        console.log('pfile Item render');
        return (
            <div>
                <div><b>{pfile.name}</b></div>
                <div>{pfile.contents}</div>
            </div>
        );
    }
}

export default PfileItem;