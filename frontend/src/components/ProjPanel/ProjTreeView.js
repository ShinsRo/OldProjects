import React from 'react';
import TreeView from 'deni-react-treeview';
import store from '../../stores';
import { BASE_URL, END_POINT } from '../../stores/modules/projectState';

import jQuery from 'jquery';
window.$ = window.jQuery = jQuery;

class ProjTreeView extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            updateFlag: true,
            addingCnt: 0,
            modalShow: false,
        }
        this.modalCloseEl = React.createRef();
        this.addItem = this.addItem.bind(this);
    }

    componentWillMount() {
        if (!this.props.project) {
            return false;
        }
    }

    shouldComponentUpdate(nextProps, nextState) {
        const api = this.refs.treeview.api;
        
        if (nextState.addingCnt !== this.state.addingCnt) {
            let add = api.addItem (nextState.dirName, false, nextState.targetDir);
            add['id'] = nextState.addId;
            this.props.handleDirItemClick(Number(add.id));
        }
        if(!nextState.updateFlag) {
            return false;
        } else {
            this.setState({updateFlag: false});
            return true;
        }
    }

    onRenderItem(item, treeview) {
        return (
            <>
                <div 
                    draggable
                    // onDragStart={e => this.drag(e, item)}
                    // onContextMenu={e => this.onItemClick(e, item.id)}
                    style = {{ width: '100%' }}
                    onDragEnter={(e) => { 
                        e.currentTarget.lastChild.lastChild.innerHTML = '  <=========='
                    }}
                    onDragLeave={(e) => {
                        e.currentTarget.lastChild.lastChild.innerHTML = ''
                    }}
                    onDragOver={(e) => {
                        e.preventDefault();
                    }}
                    onDrop={(e) => {
                        e.currentTarget.lastChild.lastChild.innerHTML = ''
                        console.log(e.currentTarget);
                        console.log(e.target);
                    }}
                    oneDrop
                    key={item.id}>
                    <div className="treeview-item">
                        <input type="hidden" value="item.id"></input>
                        <span className="action-btn trash" onClick={this.deleteItemClick.bind(this, item.id)}><i className="fas fa-trash-alt"></i></span>
                        <span className="action-btn edit" onClick={this.editItemClick.bind(this)}><i className="fas fa-edit"></i></span>
                        <span className="treeview-item-text">{item.text}</span>
                        <span></span>
                    </div>
                </div>
            </>
        )
    }
    
    editItemClick() {}

    deleteItemClick(id) {
        if(!window.confirm('정말 삭제할까요?')) return true;

        const URL = BASE_URL + END_POINT.DIR_DISABLE;
        const { userState, projectState } = store.getState();
        const data = {
            userId: userState.selectedUser.userId,
            projId: projectState.get('selectedProject'),
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
            this.refs.treeview.api.removeItem(id);
        });
    }

    onSelectItem(item) {
        this.props.handleDirItemClick(Number(item.id));
    }

    addItem(e, project) {
        e.preventDefault();

        window.$('#dirAddModal').modal('toggle');

        const { userState, projectState } = store.getState();
        let { addingCnt } = this.state
        const URL = BASE_URL + END_POINT.DIR_REGISTER;
        const jsonObj = {};

        new FormData(e.target).forEach((value, key) => {
            jsonObj[key] = value;
        });

        const dirName = jsonObj.dirName;
        const data = {
            userId: userState.selectedUser.userId,
            projId: project.projId,
            parentId: projectState.get('selectedDirId') || null,
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
            return res.json()
        }).then(json => {
            const targetDir = this.refs.treeview.api.getSelectedItem() || this.refs.treeview.api.getRootItem();
            this.setState( { addId: json, targetDir, dirName, addingCnt: ++addingCnt } );
        });
    }

    render() {
        console.log("Rendering: ProjTreeView");
        const {dirs, project} = this.props;
        const dirList = (dirs && dirs.get(`${project.projId}`)) || [];
        const dirTree = convertDirListToTree(dirList, project.userId, project.projName);
        
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
                        onSelectItem={this.onSelectItem.bind(this)}
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
                            <form className="user" onSubmit={(e) => this.addItem(e, project)}>
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

function convertDirListToTree(dirList, userId, projName) {
    const tempMap = {};
    const tempTree = [];
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
        // if ( tempMap[key].userId !== userId ) return; // 내 폴더만 보는 경우
        const parent = tempMap[key].parent;
        // tempMap[key].isLeaf = tempMap[key].children.length === 0;
        if (parent !== 'root') {
            tempMap.hasOwnProperty(parent) && tempMap[parent].children.push(tempMap[key]);
        } else {
            tempTree.push(tempMap[key]);
        }
    });
    
    return tempTree;
    // const _root = {
    //     id: null,
    //     text: projName,
    //     children: [],
    //     isLeaf: false,
    //     parent: undefined,
    // }
    // _root.children = tempTree;
    // const dirTree = [_root]

    // return dirTree;
}

export default ProjTreeView;

