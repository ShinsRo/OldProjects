import React from 'react';
// import jQuery from 'jquery';
// window.$ = window.jQuery = jQuery;

class ProjTreeView extends React.Component {

    constructor(props) {
        super(props);
        this.drawTree = this.drawTree.bind(this);
        this.onDirClick = this.onDirClick.bind(this);
        
        this.state = {
            treeMap: {},
            projectMap: {},
            dirTrees: [],
        };
    }

    drawTree(trees, handleDirItemClick) {
        const selectedDirId = this.props.projectState.get('selectedDirId');
        
        if (!trees) return (<></>);
        else {
            return trees.map((dir, idx) => {
                return (<>
                    <div key={dir.id} className="kss-tree-item">
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
                        })()
                        }                     
                        {dir.title}
                        </div>
                    </div>
                    {(() => {
                        if (dir.isOpen) 
                            return (
                                <div key={dir.id + 'childs'} className="kss-tree-branch-wspace">
                                    {this.drawTree(dir.child, handleDirItemClick)}
                                </div>
                            );
                    })()}
                </>);
            });
        }
    }

    onDirClick(did) {
        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");
        dirContainer.treeMap[did].isOpen = !dirContainer.treeMap[did].isOpen;
        this.props.handleDirItemClick(did);
    }

    render() {
        const { projectState, handleDirItemClick } = this.props;
        const dirContainer = projectState.get("dirContainer");
        if (!dirContainer) return (<></>);
        
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
                    <div className="kss-tree-title"><h6>PROJECTS</h6></div>
                    { this.drawTree(dirTrees, handleDirItemClick) }
                </div>
            </div>
        );
    }
}

export default ProjTreeView;