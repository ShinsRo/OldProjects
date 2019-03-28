import React from 'react';
import TreeView from 'deni-react-treeview';
import store from '../../stores';
import { BASE_URL, END_POINT } from '../../stores/modules/projectState';

class ProjTreeView extends React.Component {

    // constructor(props) {
    //     super(props);
    // }

    componentWillMount() {
        if (!this.props.project) {
            return false;
        }
    }

    onRenderItem(item, treeview) {
        return (
            <div>
                <div 
                    draggable
                    onDragStart={e => this.drag(e, item)}
                    onClick={this.onItemClick.bind(this, item.id)}
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
    
    onItemClick(dirId) {
        this.props.handleDirItemClick(dirId);
    }

    addItem(e) {
        e.preventDefault(e.target);
        alert()
        const { userState, projectState } = store.getState();
        let { slectedDirId } = projectState
        
        const URL = BASE_URL + END_POINT.DIR_REGISTER;
        const jsonObj = {};
        e.target.forEach((value, key) => {
            jsonObj[key] = value;
        });

        const dirName = jsonObj.dirName;
        const data = {
            userId: userState.userInfo.userId,
            projId: this.state.projId,
            parentId: slectedDirId,
            dirName,
        }
        
        fetch(URL, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'accept': 'application/json'
            }
        }).then(res => {
            alert("디렉토리 추가함")
            if (!slectedDirId) slectedDirId = this.refs.treeview.api.getRootItem();
            this.refs.treeview.api.addItem (dirName, false, slectedDirId)
        });
        // window.location.href="/";
        // this.props.handleDirItemActionCall(END_POINT.DIR_REGISTER, this.props.project.projId, -1, dir);
    }

    deleteItemClick(id) {
        const URL = BASE_URL + END_POINT.DIR_DISABLE;
        const { userState } = store.getState();
        const data = {
            userId: userState.userInfo.userId,
            projId: this.state.projId,
            dirId: id,
        }
        fetch(URL, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'accept': 'application/json'
            }
        }).then(res => {
            alert("디렉토리 삭제함")
            this.refs.treeview.api.removeItem(id);
        });
    }
    
    editItemClick(id) {
        // this.props.handleDirItemActionCall(END_POINT.DIR_UPDATE, this.props.project.projId, id, {});
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
    render() {
        console.log("Rendering: ProjTreeView");
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
                <div className="row" style={{ marginLeft: '3px', cursor: 'pointer' }}>
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
                            <form className="user" onSubmit={this.addItem.bind(this)}>
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
                                    <input name="dirName" type="text" className="form-control form-control-user" id="exampleLastName" placeholder="Directory name"/>
                                </div>
                                </div>
                                <div className="modal-footer">
                                    <button className="btn btn-secondary" type="button" data-dismiss="modal">취소하기</button>
                                    <input className="btn btn-darkblue" type="submit" value="추가하기"></input>
                                </div>
                            </form>
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