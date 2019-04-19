class KssTree {
    constructor(projects) {
        this.projects = projects;
        this.treeMap = {};
        this.projectMap = {};
        this.dirTrees = [];

        this.init();
    }

    init() {
        let projects = this.projects;
        let treeMap = this.treeMap;
        let projectMap = this.projectMap;
        
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
        this.buildDirTrees();
    }

    buildDirTrees() {
        
        let treeMap = this.treeMap;
        let dirTrees = this.dirTrees;
        let projectMap = this.projectMap;
        console.log("treeMap", treeMap);

        dirTrees.length = 0;
        Object.keys(treeMap).forEach(key => {
            treeMap[key].child.length = 0;
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