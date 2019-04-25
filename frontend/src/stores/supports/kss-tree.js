const SORTBY = {
    ASCENT: 1,
    DESCENT: 2,
}

const titleAscent = (a, b) => { 
    const aTitle = a.title.toUpperCase();
    const bTitle = b.title.toUpperCase();
    if (aTitle < bTitle) {
        return -1;
    }
    if (aTitle > bTitle) {
        return 1;
    }
    return 0;
};

const titleDescent = (a, b) => { titleAscent(b, a) };

class KssTree {
    constructor(projects) {
        this.projects = projects;
        this.treeMap = {};
        this.projectMap = {};
        this.dirTrees = [];
        this.tempProjectData = {};
        this.filterKeyword = '';

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
            filter: true,
        };
    }

    delete(dirs) {
        dirs.forEach(dir => {
            delete this.treeMap[dir.did];
        });
    }

    setChildVisble(did) {
        this.treeMap[did].child.forEach(dir => {
            dir.filter = true;
            this.setChildVisble(dir.id);
        });
    }

    __filterDFS(dirTrees, path) {
        if (!dirTrees) return;
        
        const keyword = this.filterKeyword;

        dirTrees.forEach(dir => {
            dir.filter = dir.title.indexOf(keyword) !== -1;

            if (dir.filter) {
                path.forEach(dir => { dir.filter = true; dir.isOpen = true; });
                path = [];
            }

            path.push(dir);
            this.__filterDFS(dir.child, path);
            path.pop();
        });
    }
    filterTree() {
        const keyword = this.filterKeyword;
        const treeMap = this.treeMap;

        if (!keyword) {
            Object.keys(treeMap).forEach(key => {
                treeMap[key].filter = true;
            });
        } else {
            this.__filterDFS(this.dirTrees, []);
        }
    }

    sortDirTree(dirTrees) {
        if (!dirTrees) return dirTrees;

        dirTrees.forEach(dir => {
            dir.child = this.sortDirTree(dir.child);
        });
        
        dirTrees.sort(this.compare);
        return dirTrees;
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

        const _ = SORTBY;
        switch (this.filter) {
            case _.DESCENT: 
                this.compare = titleDescent;
                break;
            
            case _.ASCENT: default:
                this.compare = titleAscent;
                break;
        }

        this.dirTrees = this.sortDirTree(this.dirTrees);
    }

    getDirTree() {
        return this.dirTrees;
    }

    toggleIsOpen(did) {
        this.treeMap[did].isOpen = !this.treeMap[did].isOpen;
    }
}

export default KssTree;