class KssTree {
    constructor(projects) {
        this.treeMap = {};
        this.projectMap = {};
        this.dirTrees = [];

        let treeMap = this.treeMap;
        let projectMap = this.projectMap;
        let dirTrees = this.dirTrees;
        
        projects.forEach(project => {
            projectMap[project.pid] = project;
            project.dirs.forEach(dir => {
                treeMap[dir.did] = {
                    id: dir.did,
                    pid: dir.pid,
                    parent: dir.parentDid,
                    title: dir.dname,
                    child: [],
                    isLeaf: true,
                    isOpen: false,
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
    }

    getDirTree() {
        return this.dirTrees;
    }

    toggleIsOpen(did) {
        this.treeMap[did].isOpen = !this.treeMap[did].isOpen;
    }
}

export default KssTree;