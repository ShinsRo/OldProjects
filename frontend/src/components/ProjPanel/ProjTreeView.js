import React from 'react';
import TreeView from 'deni-react-treeview';

class ProjTreeView extends React.Component {

    onRenderItem(item, treeview) {
        return (
            <div>
                <div 
                    draggable
                    onDragStart={e => this.drag(e, item)}
                    className="treeview-item">
                    {/* <div ref={`topDiv${item.id}`} onDrop={e => this.drop(e, item)} onDragOver={e => this.allowDrop(e, item)}>>----------------------------</div> */}
                    <span className="action-btn trash" onClick={this.deleteItemClick.bind(this, item.id)}><i className="fas fa-trash-alt"></i></span>
                    <span className="action-btn edit" onClick={this.editItemClick.bind(this)}><i className="fas fa-edit"></i></span>
                    <span className="treeview-item-text">{item.text}</span>
                    {/* <div ref={`botDiv${item.id}`} onDrop={e => this.drop(e, item)} onDragOver={e => this.allowDrop(e, item)}>>----------------------------</div> */}

                </div>
            </div>
        )
    }
    
    drop(e, item) {
        const dropedDirId = e.dataTransfer.getData('dirId');
        console.log(dropedDirId);
    }
    drag(e, item) {
        e.dataTransfer.setData('dirId', item.id);
    }
    allowDrop(e, item) {
        e.preventDefault();
        console.log('allowDrop');
        console.log(this.refs);
        console.log(this.refs[`botDiv${item.id}`]);
        
    }

    deleteItemClick(id) {
        this.refs.treeview.api.removeItem(id);
    }
    
    editItemClick(id) {
        
    }

    render() {
        const {dirs, project} = this.props;
        const dirList = (dirs && dirs.get(`${project.projId}`)) || [];
        
        const dirTree = convertDirListToTree(dirList, project.userId);
        
        return (
            <div>
                <div className="row">
                    {/* filter */}
                </div>
                <div className="dir-upmureport-theme row">
                    <TreeView 
                        items={ dirTree }
                        ref="treeview"
                        selectRow={ true }
                        onRenderItem={ this.onRenderItem.bind(this) }
                        ></TreeView>
                </div>
                <div className="row" style={{ float: 'right', cursor: 'pointer' }}>
                    <div className="btn-cirecle btn-sm bg-darkblue text-white" data-toggle="modal" data-target="#dirAddModal"><i className="fas fa-plus"></i> 폴더 추가하기</div>
                </div>

                {/* 디렉토리 추가 모달 */}
                <div className="modal fade" id="dirAddModal" tabIndex="-1" role="dialog" aria-labelledby="dirAddModal" aria-hidden="true">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                            <h5 className="modal-title font-weight-bold" id="exampleModalLabel"><font className="text-darkblue">{project.projName}</font> 에 디렉토리를 추가합니다.</h5>
                            <button className="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            </div>
                            <div className="modal-body">
                            <form className="user">
                                <div className="form-group row">
                                <div className="col-xl-3">
                                    <h6 className="m-0 font-weight-bold" style={{
                                        position: 'absolute',
                                        top: '50%',
                                        'msTransform': 'translateY(-50%)',
                                        'transform': 'translateY(-50%)',
                                    }}>디렉토리 이름 :</h6>
                                </div>
                                <div className="col-xl-9">
                                    <input type="text" className="form-control form-control-user" id="exampleLastName" placeholder="Directory name"/>
                                </div>
                                </div>
                            </form>
                            
                            </div>
                            <div className="modal-footer">
                            <button className="btn btn-secondary" type="button" data-dismiss="modal">취소하기</button>
                            <a className="btn btn-primary" href="login.html">추가하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        )
    }
}

function convertDirListToTree(dirList, userId) {
    const tempMap = {};
    const dirTree = [];
    const keyOrder = [];

    // 디렉토리 정렬
    dirList.sort( (a, b) => {
        if (a.dirName < b.dirName) return -1;
        return 1;
    } );

    dirList.forEach( (dir, idx) => {
        tempMap[dir.dirId] = {
            id: dir.dirId,
            text: dir.dirName,
            children:[],
            isLeaf: false,

            projId: dir.projId,
            userId: dir.userId,
            userName: dir.userName,
            parent: dir.parentDirId,
        }
        keyOrder.push(dir.dirId);
    });

    keyOrder.forEach( key => {
        if ( tempMap[key].userId !== userId ) return;
        const parent = tempMap[key].parent;
        // tempMap[key].isLeaf = tempMap[key].children.length === 0;
        if (parent !== 'root') {
            tempMap.hasOwnProperty(parent) && tempMap[parent].children.push(tempMap[key]);
        } else {
            dirTree.push(tempMap[key]);
        }
    });

    return dirTree;
}

export default ProjTreeView;