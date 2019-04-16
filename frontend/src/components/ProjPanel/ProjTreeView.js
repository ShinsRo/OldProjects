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
        if (!trees) return (<></>);
        return trees.map((dir, idx) => {
            return (<>
                <div key={dir.id} className="kss-tree-item">
                    <span className="kss-tree-icon" onClick={() => { this.toggleFold(dir.id) }}>
                        {(() => {
                            if (dir.child.length === 0) return (<>&nbsp;</>);
                            else if (dir.isOpen) return '-';
                            else return '+';
                        })()} 
                    </span>
                    <div className="overlay" onClick={() => { this.onDirClick(dir.id) }}></div>
                    <div key={dir.id} onClick={() => { this.onDirClick(dir.id) }} className="kss-tree-item-title">
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
        })
    }

    onDirClick(did) {
        const { projectState } = this.props;
        const dirContainer = projectState.get("dirContainer");
        dirContainer.treeMap[did].isOpen = !dirContainer.treeMap[did].isOpen;
        this.props.handleDirItemClick(did);
    }

    toggleFold(did) {
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
                </div>
                <div className="kss-tree">
                { 
                    this.drawTree(dirTrees, handleDirItemClick)
                }
                </div>
            </div>
            
        );
    }
}

export default ProjTreeView;