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
                <div class="row" key={idx} >
                    <div class="col-8">{pfile.name}</div>
                    <div class="col-2">업무 일지</div>
                        <div class="col-1">
                            <button onClick={() => this.props.onClickPfile(pfile) } class="btn btn-info btn-circle btn-sm">
                                <i class="fas fa-info-circle"></i>
                            </button>
                        </div>
                        <div class="col-1">
                            <button onClick={() => this.props.onClickPfileDelete(pfile.pfileId)} class="btn btn-danger btn-circle btn-sm">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                </div>
                );
    }
}

export default PfileItem;