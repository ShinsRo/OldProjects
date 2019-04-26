import React, { Component } from 'react';

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
                            <span onClick={() => this.props.onClickPfile(pfile) } class="badge badge-primary bg-darkblue">
                                보기
                            </span>
                        </div>
                        <div class="col-1">
                            <span onClick={() => this.props.onClickPfileDelete(pfile.pfileId)} class="badge badge-danger bg-danger btn-sm">
                                삭제
                            </span>
                        </div>
                </div>
                );
    }
}

export default PfileItem;