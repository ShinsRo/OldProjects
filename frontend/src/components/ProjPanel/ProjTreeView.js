import React from 'react';
// import jQuery from 'jquery';
// window.$ = window.jQuery = jQuery;

export const ProjTreeView = ({ projectState, handleDirItemClick }) => {
    const projects = projectState.get("projects");
    const dirTrees = toTree(projects);
    return (
        <div>
            <div className="row">
                {/* filter */}
            </div>
            <div className="kss-tree">
            { 
                drawTree(dirTrees, handleDirItemClick)
            }
                    {/* dirTrees.map((dirs, idx) => {
                        return (
                            <div key={idx} className="kss-tree-branch-wspace">{dirs.title}
                            </div>
                        );
                    }) */}
            </div>
        </div>
        
    );
}

function drawTree(trees, handleDirItemClick) {
    if (!trees) return (<></>);
    return trees.map((dir, idx) => {
        return (<>
            <div key={dir.id} className="kss-tree-branch-wspace" onClick={() => { handleDirItemClick(dir.id) }}>
                {dir.title}
                {drawTree(dir.child, handleDirItemClick)}    
            </div>
        </>);
    })
}

function toTree(projects) {
    let treeMap = {};
    let projectMap = {};
    let dirTrees = [];
    
    projects.forEach(project => {
        projectMap[project.pid] = {
            pname : project.pname,
        }
        project.dirs.forEach(dir => {
            treeMap[dir.did] = {
                id: dir.did,
                pid: dir.pid,
                parent: dir.parentDid,
                title: dir.dname,
                child: [],
                isLeaf: true,
                isActivated: false,
            }
        });
    });

    Object.keys(treeMap).forEach(key => {
        if ( treeMap[key].parent === 'root' ) {
            const rootDir = treeMap[key];
            
            rootDir.title = projectMap[rootDir.pid].pname;
            dirTrees.push(treeMap[key]);
        } else {
            const child = treeMap[key];
            treeMap[child.parent].isLeaf = false;
            treeMap[child.parent].child.push(child);
        }
    });

    return dirTrees;
}
