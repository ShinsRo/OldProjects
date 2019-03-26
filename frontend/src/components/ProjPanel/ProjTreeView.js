import React from 'react';
import TreeView from 'deni-react-treeview';

class ProjTreeView extends React.Component {

    onRenderItem(item, treeview) {
        return (
            <div className="treeview-item-example">
                <span className="treeview-item-example-text">{item.text}</span>
                <span className="actionButton trash" onClick={this.deleteItemClick.bind(this, item.id)}><i>2</i></span>
                <span className="actionButton edit" onClick={this.editItemClick.bind(this)}><i>1</i></span>
            </div>
        )
    }
    
    deleteItemClick(id) {
    this.refs.treeview.api.removeItem(id);
    }
    
    editItemClick(id) {
    alert('editing routine here...')
    }

    render() {
        const {dirs, project} = this.props;
        const dirList = (dirs && dirs.get(`${project.projId}`)) || [];
        
        const dirTree = convertDirListToTree(dirList);
        
        
        return (
            <div className="">
                <TreeView 
                    items={ dirTree }
                    selectRow={ true }
                    onRenderItem={ this.onRenderItem.bind(this) }
                    ></TreeView>
            </div>
            
        )
    }
}

function convertDirListToTree(dirList) {
    const tempMap = {};
    const dirTree = [];
    console.log(">>>>", dirList);
    
    dirList.forEach((dir, idx) => {
        tempMap[dir.dirId] = {
            id: dir.dirId,
            text: dir.dirName,
            children:[],
            parent: dir.parentDirId,
            isLeaf: false
        }
    });
    
    console.log(tempMap);
    
    Object.keys(tempMap).forEach( key => {
        const parent = tempMap[key].parent;
        // tempMap[key].isLeaf = tempMap[key].children.length === 0;
        
        if (parent !== 'root') {
            tempMap[parent].children.push(tempMap[key]);
        } else {
            dirTree.push(tempMap[key]);
        }
    });
    return dirTree;
}

export default ProjTreeView;