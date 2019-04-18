import React, { Component } from 'react';


style : {

}

class PfileItem extends Component {

    static defaultProps = {
        pfile: {
            name: '이름',
            contents: '내용',
        }
    }

    render() {
        const { pfile, idx } = this.props;

        console.log('pfile Item render');
        return (
                <tr key={idx} >
                    <td >{pfile.name.substring(0,10)}</td>
                    <td>업무 일지</td>
                    <td>{pfile.contents}</td>
                    <td>{pfile.localPath}</td>
                    <td>{pfile.newDate}</td>
                    <td>{pfile.updateDate}</td>
                        <td>
                            <button onClick={() => this.props.onClickPfile(pfile) } class="btn btn-info btn-circle btn-sm">
                                <i class="fas fa-info-circle"></i>
                            </button>
                        </td>
                        <td>
                            <button onClick={() => this.props.onClickPfileDelete(pfile.pfileId)} class="btn btn-danger btn-circle btn-sm">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                </tr>
                );
    }
}

export default PfileItem;