import React from 'react';
import stores from '../../../../stores';
import { pdir_api } from '../../../../stores/modules/projectState';
import AddModal from './AddModal';
import DirCorrectModal from './DirCorrectModal';

class ProjTreeView extends React.Component {

    constructor(props) {
        super(props);
        this.drawTree = this.drawTree.bind(this);
        this.onDirClick = this.onDirClick.bind(this);
        this.onAddClick = this.onAddClick.bind(this);
        this.dirDisable = this.dirDisable.bind(this);
        this.dirCorrect = this.dirCorrect.bind(this);
        this.reloadFromParent = this.reloadFromParent.bind(this);

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

    onDropDir(e, dir) {
        const enterChar = e.target.getElementsByClassName('enterChar')[0];
        if (enterChar) enterChar.innerHTML = "";

        const from = JSON.parse(e.dataTransfer.getData('from'));
        const to = dir;
        
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
        e.dataTransfer.setData('from', JSON.stringify(dir));
        e.dataTransfer.setData('fromId', dir.id);
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
                        onDrop={(e) => { this.onDropDir(e, dir); }}
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
                        <div key={dir.id} onClick={() => { this.onDirClick(dir.id) }} className="kss-tree-item-title"
                            onDragLeave={(e) => { this.onDirLeave(e); }}
                            onDragEnter={(e) => { this.onDirEnter(e); }}
                            onDrop={(e) => { this.onDropDir(e, dir); }}
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
        
        this.props.handlers("handleDirItemClick", { selectedDirId: did });
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
                {/* {this.state.showAddModal && } */}
            </div>
        );
    }
}

export default ProjTreeView;