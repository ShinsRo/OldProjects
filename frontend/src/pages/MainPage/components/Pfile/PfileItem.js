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

    onDragStart(e, target) {
        if (e.target.draggable && target) {
            e.dataTransfer.setData('type', 'FILE');
            e.dataTransfer.setData('from', JSON.stringify(target));
        }
        e.dataTransfer.setData('draggable', e.target.draggable);

        console.log(e.dataTransfer)
    }


    render() {
        const { pfile, idx } = this.props;

        console.log('pfile Item render');
        return (
                <div className="row m-0" id="pfileRow" key={idx} style={{textAlign:"left"}}
                    draggable={true} onDragStart={(e) => {this.onDragStart(e, pfile)}} >
                    
                    <div className="col-1 text-dark-1 border-right"/>
                    <div className="col-8 pr-0 text-dark-1 border-right justify-content-center" >
                        <div className="m-0 p-0" style={{height:'100%', width: '100%', cursor: "pointer"}} onClick={() => this.props.onClickPfile(pfile) } 
                        onMouseOver={this.handleMouseOver} onMouseOut={this.handleMouseOut}>
                            {pfile.name}
                        </div>
                    </div>
                    <div className="col-2 text-dark-1 border-right">
                        파일
                    </div>
                    <div className="col-1">
                        <span onClick={() => this.props.onClickPfileDelete(pfile.pfileId)} className="badge badge-danger bg-danger btn-sm" style={{cursor: 'pointer'}}>
                            삭제
                        </span>
                    </div>
                    <hr></hr>
                </div>
                );
    }
}

export default PfileItem;
