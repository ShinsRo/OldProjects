import React from 'react';

class ProjTreeView extends React.Component {
    
    render() {
        const {dirs, project} = this.props;
        console.log(dirs);
        const dirList = (dirs && dirs.get(`${project.projId}`)) || [];
        
        const dirTree = convertDirListToTree(dirList);

        return (
            <div className="table-responsive">
            {dirList.map((dir, idx) => {
                return (<div>{dir.dirName}</div>);
            })}
            </div>
            
        )
    }
}

function convertDirListToTree(dirList) {
    const tempMap = {};
    const dirTree = [];
    dirList.forEach((dir, idx) => {
        tempMap[dir.dirId] = {
            id: dir.dirId,
            text: dir.dirName,
            children:[],
            parent: dir.parentDirId
        }
    });

    Object.keys(tempMap).forEach( key => {
        const parent = tempMap[key].parent;
        if (parent !== 'root') {
            tempMap[parent].children.push(tempMap[key]);
        } else {
            dirTree.push(tempMap[key]);
        }
    });
    
    console.log(dirTree);
}

export default ProjTreeView;