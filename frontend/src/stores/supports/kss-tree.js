class KssTree {
    constructor(projects) {
        this.projects = projects;
        this.treeMap = {};
        this.projectMap = {};
        this.dirTrees = [];

        this.tempProjectData = {};
        this.init();
    }

    init() {
        let projects = this.projects;
        let projectMap = this.projectMap;
        
        projects.forEach(project => {
            project['isOrigin'] = true;
            projectMap[project.pid] = project;
            project.dirs.forEach(dir => {
                this.addOne(dir);
            });
        });
        this.buildDirTrees();
    }

    addProject(project) {
        let projectMap = this.projectMap;
        project['isOrigin'] = true;
        projectMap[project.pid] = project;
        project.dirs.forEach(dir => {
            this.addOne(dir);
        });
    }

    deleteProject(project) {
        return;
    }

    addOne(dir) {
        let treeMap = this.treeMap;

        treeMap[dir.did] = {
            id: dir.did,
            pid: dir.pid,
            parent: dir.parentDid,
            title: dir.dname,
            child: [],
            isLeaf: true,
            isOpen: false,
        };
    }

    delete(dirs) {
        dirs.forEach(dir => {
            delete this.treeMap[dir.did];
        });
    }

    buildDirTrees() {
        
        let treeMap = this.treeMap;
        let dirTrees = this.dirTrees;
        let projectMap = this.projectMap;

        dirTrees.length = 0;
        Object.keys(treeMap).forEach(key => {
            treeMap[key].child.length = 0;
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