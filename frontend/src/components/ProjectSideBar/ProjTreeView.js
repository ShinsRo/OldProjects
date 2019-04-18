import React from 'react';
import stores from '../../stores';
import AddModal from './AddModal';
// import jQuery from 'jquery';
// window.$ = window.jQuery = jQuery;

class ProjTreeView extends React.Component {

    constructor(props) {
        super(props);
        this.drawTree = this.drawTree.bind(this);
        this.onDirClick = this.onDirClick.bind(this);
        this.onDirChange = this.onDirChange.bind(this);
        this.onAddClick = this.onAddClick.bind(this);
        
        this.state = {
            treeMap: {},
            projectMap: {},
            dirTrees: [],
            showAddModal: true,
        };
    }

    onDirChange(e, did) {
        console.log(">>>>>>>>>>", e);
        
    }

    drawTree(trees, handleDirItemClick) {
        const selectedDirId = this.props.projectState.get('selectedDirId');
        
        if (!trees) return (<></>);
        else {
            return trees.map((dir, idx) => {
                return (<div key={dir.id} >
                    <div className="kss-tree-item">
                        <span className="kss-tree-icon" onClick={() => { this.toggleFold(dir.id) }}>
                            {(() => {
                                if (dir.child.length === 0) return (<>&nbsp;</>);
                                else if (dir.isOpen) return (<i className="fas fa-chevron-down"></i>);
                                else return (<i className="fas fa-chevron-right"></i>);
                            })()} 
                        </span>
                        <div className="overlay" onClick={() => { this.onDirClick(dir.id) }}></div>
                        <div className="active" 
                            onClick={() => { this.onDirClick(dir.id) }} 
                            style={{ display: (selectedDirId && selectedDirId === dir.id) ? 'block': '' }}>
                        </div>
                        <div key={dir.id} onClick={() => { this.onDirClick(dir.id) }} className="kss-tree-item-title">
                        {(() => {
                            if (dir.parent !== 'root') {
                                return dir.isOpen ? 
                                    (<i className="fas fa-folder-open pr-1"></i>):
                                    (<i className="fas fa-folder pr-1"></i>);
                            }
                        })()}                     
                        <span
                            suppressContentEditableWarning={true} 
                            contentEditable="true" 
                            onClick={(e) => { this.onDirChange(e, dir.id) }}
                        >
                            {dir.title}
                        </span>
                        </div>
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
        this.props.handleDirItemClick(did);
    }
    
    onAddClick() {
        // const { projectState } = this.props;
        // const dirContainer = projectState.get("dirContainer");
        this.setState({ showAddModal: true });
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
                        <input type="text" placeholder="filter" className="kss-tree-filter-input"/>
                    </div>
                </div>
                <div className="kss-tree">
                    <div className="kss-tree-title">
                        {userInfo.name}님의 진행 중인 프로젝트
                        <span 
                            className="fas fa-plus-circle" 
                            data-toggle="modal" 
                            data-target="#projAddModal"
                            onClick={this.onAddClick}
                        ></span>
                    </div>
                    { this.drawTree(dirTrees, handleDirItemClick) }
                </div>
                <AddModal showModal={this.state.showAddModal}></AddModal>
            </div>
        );
    }
}

export default ProjTreeView;