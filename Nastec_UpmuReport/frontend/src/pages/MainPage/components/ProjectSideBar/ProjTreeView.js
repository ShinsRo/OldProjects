/** 
 * 프로젝트 트리 뷰 렌더 방식 등 정의
 * 
 * 2019.05.22
 * @file ProjTreeView 정의
 * @author 김승신
 */
import React from 'react';
import stores from '../../../../stores';
import { pdir_api } from '../../../../stores/modules/projectState';
import AddModal from './AddModal';
import DirCorrectModal from './DirCorrectModal';
import BehaviorModal from '../Pfile/BehaviorModal'

class ProjTreeView extends React.Component {

    constructor(props) {
        super(props);
        this.onDrop = this.onDrop.bind(this);

        this.drawTree = this.drawTree.bind(this);
        this.onDirClick = this.onDirClick.bind(this);
        this.onAddClick = this.onAddClick.bind(this);
        this.dirDisable = this.dirDisable.bind(this);
        this.dirCorrect = this.dirCorrect.bind(this);
        this.reloadFromParent = this.reloadFromParent.bind(this);
        this.showBehaviorModal = this.showBehaviorModal.bind(this);       

        this.state = {
            treeMap: {},
            projectMap: {},
            dirTrees: [],
            showAddModal: false,
            showDirCorrectModal: false,
            reload: false,
        };
    }

    reloadFromParent() {
        this.props.handlers("reload");
    }

    reload() {
        this.setState({ reload: !this.state.reload });
    }

    dirDisable(e, dir) {
        if(!window.confirm("정말 디렉토리를 삭제하시겠습니까?")) {
            return;
        }
        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");

        pdir_api.disable({ did: dir.id })
            .then   (res => {
                const deleted = res.data;
                dirContainer.delete(deleted);
                dirContainer.buildDirTrees();

                this.reload();
                alert("디렉토리를 삭제했습니다.");
            })
            .catch  (err => { 
                alert("에러로 인해 삭제에 실패했습니다.") 
            });
    }

    dirCorrect(e, dir) {
        this.setState({ showDirCorrectModal: true });
    }

    onDrop(e, targetDir) {         
        const enterChar = e.target.getElementsByClassName('enterChar')[0];

        if (enterChar) enterChar.innerHTML = "";
        
        if (e.dataTransfer.getData('draggable') === 'false') return;
        if (!e.dataTransfer.getData('from')) return;

        const type = e.dataTransfer.getData('type');
        const from = JSON.parse(e.dataTransfer.getData('from'));

        switch (type) {
            case "DIRECTORY":
                this.moveDir(from, targetDir);
                break;
            
            case "FILE":
                this.setState({ 
                    dropInfo: {
                        from: from, 
                        to : targetDir, 
                        type: 'pfile' 
                    }}, this.showBehaviorModal());
                break;

            case "ATTACHMENT" :
                 this.setState({ 
                    dropInfo: {
                        from: from, 
                        to : targetDir, 
                        type: 'attachment' 
                    }}, this.showBehaviorModal());
                break;
            default:
                break;
        }
     }

     showBehaviorModal() {        
        window.$('#BehaviorModal').modal('show');
     }

    moveDir(from, to) {
        if(!from.id) return;
        
        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");
        const treeMap = dirContainer.treeMap;
        
        const dDto = {
            did: from.id,
            parentDid: to.id,
            pid: to.pid,
        }

        let ancestorId = to.id;
        while (ancestorId !== 'root') {
            if (ancestorId === from.id) return;
            ancestorId = treeMap[ancestorId].parent;
        }

        pdir_api.correct(dDto, 'move')
            .then( res => {
                treeMap[from.id].parent = to.id;
                dirContainer.buildDirTrees();

                this.reload();
            }).catch( err => {
                alert("오류로 인해 디렉토리를 바꿀 수 없습니다.");
            });
    }

    onDirEnter(e) {
        const enterChar = e.target.getElementsByClassName('enterChar')[0];
        if (enterChar) enterChar.innerHTML = "↵";
    }

    onDragStart(e, dir) {
        if (e.target.draggable && dir) {
            e.dataTransfer.setData('type', 'DIRECTORY');
            e.dataTransfer.setData('from', JSON.stringify(dir));
            e.dataTransfer.setData('fromId', dir.id);
        }
        e.dataTransfer.setData('draggable', e.target.draggable);
    }

    onDirLeave(e) {
        const enterChar = e.target.getElementsByClassName('enterChar')[0];
        if (enterChar) enterChar.innerHTML = "";
    }
    
    drawTree(trees, handleDirItemClick) {
        const selectedDirId = this.props.projectState.get('selectedDirId');
        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");

        if (!trees) return (<></>);
        else {
            return trees.map((dir, idx) => {
                if (!dir.filter) return (<></>);

                const isRoot = dir.parent === 'root';
                const keyword = dirContainer.filterKeyword;
                const idxOf = dir.title.indexOf(keyword);

                let itemTitle = dir.title;
                if (keyword && idxOf !== -1) {
                    itemTitle = (
                        <span>
                            {dir.title.substring(0, idxOf)}
                            <span className="autocomplete-highlight" style={{ color: 'black' }}>{keyword}</span>
                            {dir.title.substring(idxOf + keyword.length)}
                        </span>
                    );
                }
                return (<div key={dir.id}>
                    <div className="kss-tree-item" 
                        draggable={dir.parent !== 'root'}
                        onDragOver={(e) => {
                            e.preventDefault();
                        }}
                        onDragStart={(e) => { this.onDragStart(e, dir); }}
                        onDragLeave={(e) => { this.onDirLeave(e); }}
                        onDragEnter={(e) => { this.onDirEnter(e); }}
                        onDrop={(e) => { this.onDrop(e, dir); }}
                    >
                        <span className="kss-tree-icon" onClick={() => { this.toggleFold(dir.id) }}>
                            {(() => {
                                if (dir.child.length === 0) return (<>&nbsp;</>);
                                else if (dir.isOpen) return (<i className="fas fa-chevron-down"></i>);
                                else return (<i className="fas fa-chevron-right"></i>);
                            })()} 
                        </span>
                        <div className="overlay" onClick={() => { this.onDirClick(dir.id) }}></div>
                        <div className="active text-right"
                            onClick={() => { this.onDirClick(dir.id) }}
                            style={{ display: (selectedDirId && selectedDirId === dir.id) ? 'block': '' }}>
                            <div className="dropleft">
                                { !isRoot 
                                    && <span className="fas fa-ellipsis-h mr-2 mt-2" data-toggle="dropdown"/> 
                                }
                                <div className="dropdown-menu">
                                <div className="dropdown-item" onClick={(e) => { this.dirDisable(e, dir); }}>삭제</div>
                                <div className="dropdown-item" onClick={(e) => { this.dirCorrect(e, dir); }}
                                    data-toggle="modal" 
                                    data-target="#dirCorrectModal"
                                >수정</div>
                                </div>
                            </div>
                        </div>
                        <div key={dir.id} 
                            className="kss-tree-item-title"
                            onClick={() => { this.onDirClick(dir.id) }}
                            onDragLeave={(e) => { this.onDirLeave(e); }}
                            onDragEnter={(e) => { this.onDirEnter(e); }}
                            onDrop={(e) => { this.moveDir(e, dir); }}
                        >
                        {(() => {
                            if (!isRoot) {
                                return dir.isOpen ? 
                                    (<i className="fas fa-folder-open pr-1"></i>):
                                    (<i className="fas fa-folder pr-1"></i>);
                            }
                        })()}                     
                        <span
                            // suppressContentEditableWarning={true} 
                            // contentEditable="true" 
                        >
                            {itemTitle}
                        </span>
                        </div>
                        <span className="enterChar" style={{ float: 'right' }}></span>
                    </div>
                    {(() => {
                        if (dir.isOpen) 
                            return (
                                <div className="kss-tree-branch-wspace">
                                    {this.drawTree(dir.child, handleDirItemClick)}
                                </div>
                            );
                    })()}
                </div>);
            });
        }
    }

    onDirClick(did) {
        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");
        dirContainer.treeMap[did].isOpen = !dirContainer.treeMap[did].isOpen;
        dirContainer.setChildVisble(did);
        
        const selectedPid = dirContainer.treeMap[did].pid;
        const selectedProject = dirContainer.projectMap[selectedPid];
        
        this.props.handlers("handleDirItemClick", { selectedDirId: did, selectedProject, });
    }
    
    onAddClick() {
        // const { projectState } = this.props;
        // const dirContainer = projectState.get("dirContainer");
        this.setState({ showAddModal: true });
    }

    onFilterInputChange(e) {
        const keyword = e.target.value;

        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");

        dirContainer.filterKeyword = keyword;
        dirContainer.filterTree();

        this.reload();
    }

    render() {
        const { projectState, handleDirItemClick } = this.props;
        const dirContainer = projectState.get("dirContainer");
        if (!dirContainer) return (<></>);
        const userInfo = stores.getState().userState.userInfo;
        
        const dirTrees = dirContainer.getDirTree();

        return (
            <div>
                <div className="row">
                    {/* filter */}
                    <div className="kss-tree-filter">
                        <i className="fas fa-filter filter-icon"></i>
                        <input 
                            type="text" 
                            placeholder="filter" 
                            className="kss-tree-filter-input"
                            onChange={this.onFilterInputChange.bind(this)}
                        />
                    </div>
                </div>
                <div className="kss-tree">
                    <div className="kss-tree-title">
                        {userInfo.memberInfo.name}님의 진행 중인 프로젝트
                        <span 
                            className="fas fa-plus-circle" 
                            data-toggle="modal" 
                            data-target="#addModal"
                            onClick={this.onAddClick}
                        ></span>
                    </div>
                    { this.drawTree(dirTrees, handleDirItemClick) }
                </div>
                <AddModal reload={this.reload.bind(this)}></AddModal>
                <DirCorrectModal reload={this.reload.bind(this)}></DirCorrectModal>

                <BehaviorModal 
                dropInfo = {this.state.dropInfo}
                movePfile = {this.props.movePfile}
                copyPfile = {this.props.copyPfile}
                moveAttachment = {this.props.moveAttachment}
                copyAttachment = {this.props.copyAttachment}
                getPfile = {this.props.getPfile}
                getAttachment = {this.props.getAttachment}
                getPLog = {this.props.getPLog}/>

                {/* {this.state.showAddModal && } */}
            </div>
        );
    }
}

export default ProjTreeView;