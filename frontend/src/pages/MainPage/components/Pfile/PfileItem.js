import React, { Component } from 'react';

class PfileItem extends Component {

    static defaultProps = {
        pfile: {
            name: '이름',
            contents: '내용',
        }
    }

    handleMouseOver = (e) => {
        e.target.parentNode.style.backgroundColor = "rgb(154, 154, 172)"
    }

    handleMouseOut = (e) => {
        e.target.parentNode.style.backgroundColor = ""
    }


    render() {
        const { pfile, idx } = this.props;

        console.log('pfile Item render');
        return (
                <div class="row" id="pfileRow" key={idx} onClick={() => this.props.onClickPfile(pfile) } 
                    onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>
                    
                    <div class="col-9 text-dark-1">{pfile.name}</div>
                    <div class="col-2 text-dark-1">업무 일지</div>
                    <div class="col-1">
                        <span onClick={() => this.props.onClickPfileDelete(pfile.pfileId)} class="badge badge-danger bg-danger btn-sm">
                            삭제
                        </span>
                    </div>
                    <hr></hr>
                </div>
                );
    }
}

export default PfileItem;
