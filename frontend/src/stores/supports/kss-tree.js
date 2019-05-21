/** 
 * 디렉토리 트리 자료구조 정의
 * 
 * KssTree는 손쉽게 디렉토리 트리를 관리하고 저장하기 위한 클래스이다.
 * 또한 임시 저장을 통해 간단한 트랜젝션을 보조한다.
 * 각 자료 (Project, Directory) 는 Map으로 관리할 수 있다.
 * Directory는 Array 형태의 트리구조로 표현한다.
 * 
 * 2019.05.13
 * @file 이 파일은 KssTree를 정의한다.
 * @author 김승신
 */

class KssTree {
    constructor() {
        this.projects = [];             // 원본 프로젝트(projectDto) 데이터
        this.projectMap = {};           // 프로젝트 ID를 키 값으로 한 맵
        this.treeMap = {};              // 디렉토리 ID를 키 값으로 한 맵
        this.dirTrees = [];             // 디렉토리 트리, 최종 반환 값
        this.tempProjectData = {};      // 트랜젝션을 위한 임시 저장 데이터
        this.filterKeyword = '';        // 필터링을 위한 current 키워드
        this.filter = SORTBY.ASCENT;    // 필터링 기준

        this.addProject = this.addProject.bind(this);
    }

    setProjects(projects) {
        this.projects = projects;
    }

    /**
     * Array 형태 DateTime 포맷(스프링5 잭슨 바인딩이 아닌 경우)을 
     * "yyyy-mm-dd'T'hh:MM:ss" 형태 스트링으로 포맷팅한다.
     * 빈 Array가 주어질 경우 빈 문자열을 반환한다.
     * 
     * @param {Array} arrDate 
     */
    arrayDateFormatter(arrDate) {
        if (!arrDate) return '';

        arrDate = arrDate.map(e => {
            if (!e) e = '0';

            if (e < 10) {
                e = '0' + e;
            }

            return e;
        });
        const year  = arrDate[0];
        const month = arrDate[1];
        const day   = arrDate[2];
        const hour  = arrDate[3];
        const min   = arrDate[4];
        const mill  = arrDate[5] || '00';
        
        // "yyyy-mm-dd'T'hh:MM:ss" 형태로 반환한다.
        return `${year}-${month}-${day}T${hour}:${min}:${mill}`;
    }

    /**
     * 원본 프로젝트 dto 배열을 이용해 프로젝트 맵을 초기화한다.
     * 이때 DateTime 들은 Date 객체로 관리함을 주의해야한다.
     * 또 모든 프로젝트 dto에 isOrigin 프로퍼티를 추가하여
     * 클라이언트가 서버와 같은 프로젝트 정보를 가지고 있는가를 표현하고 있다.
     */
    init() {
        let projects = this.projects;
        projects.forEach(this.addProject);

        // 디렉토리 트리를 제작
        this.buildDirTrees();
    }

    /**
     * 클라이언트 상 자료구조에 새로운 프로젝트를 추가한다.
     * 
     * @param {Object} project 서버로부터의 원본 프로젝트 
     */
    addProject(project) {
        let projectMap = this.projectMap;

        project.stDate = new Date(project.stDate);
        project.edDate = new Date(project.edDate);
        project.cdate =  new Date(project.cdate);
        project.udate =  new Date(project.udate);
        
        projectMap[project.pid] = project;          // 매핑
        projectMap[project.pid].isOrigin = true;    // 프로젝트 동기 상태 프로퍼티

        // projectDto가 지닌 원본 디렉토리 객체들을 
        // 알맞게 변형하고, 이를 treeMap에 추가한다.
        project.dirs.forEach(dir => {
            this.addOne(dir);
        });
    }

    /**
     * 클라이언트 상 프로젝트를 변경한다.
     * 
     * 이 메서드는 프로젝트의 변동사항 Fetch에 사용할 수 있는 메서드로,
     * 파라미터의 project가 서버와 동기화한 데이터임이 보장되어야 한다.
     * 또 이 작업은 디렉토리 및 디렉토리 트리의 변동에 전혀 무관하다.
     * 따라서 프로젝트의 내용 변경이 있을 경우에만 사용해야한다.
     * 
     * @param {Object} project
     */
    correctProject(project) {
        // project가 새로운 프로젝트인 경우 (타인의 초대)
        if (!this.projectMap.hasOwnProperty(project.pid)) {
            this.addProject(project);
            this.buildDirTrees();
            return;
        }

        const tempDirs = this.projectMap[project.pid].dirs;     // 이전 프로젝트의 하위 디렉토리 배열

        this.projectMap[project.pid] = project;
        
        this.projectMap[project.pid].dirs = tempDirs;           // 이전 프로젝트의 하위 디렉토리를 새로운 프로젝트에 연결
        this.projectMap[project.pid].stDate = new Date(project.stDate);
        this.projectMap[project.pid].edDate = new Date(project.edDate);
        this.projectMap[project.pid].cdate =  new Date(project.cdate);
        this.projectMap[project.pid].udate =  new Date(project.udate);

        this.projectMap[project.pid].isOrigin = true;
        this.tempProjectData = {};

        this.buildDirTrees();
    }

    // deleteProject(project) {
    //     return;
    // }

    /**
     * 원본 디렉토리 객체를 트리 구조에 맞게 재구성하고 추가한다.
     * 
     * @param {Object} dir 원본 디렉토리 dto
     */
    addOne(dir) {
        let treeMap = this.treeMap;

        treeMap[dir.did] = {
            id: dir.did,            // 디렉토리 아이디
            pid: dir.pid,           // 상위 프로젝트 아이디
            parent: dir.parentDid,  // 상위 디렉토리 아이디, 최상위일 경우 'root'
            title: dir.dname,       // 디렉토리 이름
            child: [],              // 하위 디렉토리 객체 배열
            isLeaf: true,           // 최하위 디렉토리 플래그
            isOpen: false,          // 개폐 여부 플래그
            filter: true,           // 필터링 적용 여부 플래그
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

    /**
     * 트리 내에서 키워드를 검색할 수 있는 디렉토리 이름을 가진
     * 디렉토리를 찾아내어 이를 포함한 상위 디렉토리들을 모두 열고
     * 필터링 플래그를 true로 변경하여 사용자가 볼 수 있도록 한다.
     * 
     * @param {Array} dirTrees 필터링할 디렉토리 트리
     * @param {Array} path 현재 트리에 도달하기까지 지나온 부모 디렉토리 경로
     */
    __filterDFS(dirTrees, path) {
        if (!dirTrees) return;
        
        const keyword = this.filterKeyword;

        dirTrees.forEach(dir => {
            // 키워드를 디렉토리 이름에서 검색한다.
            dir.filter = dir.title.indexOf(keyword) !== -1;
            
            // 검색에 성공하면 지나온 부모 디렉토리를 모두 열고 보이도록 설정한다.
            if (dir.filter) {
                path.forEach(dir => { dir.filter = true; dir.isOpen = true; });
                path = [];
            }

            path.push(dir);
            this.__filterDFS(dir.child, path);
            path.pop();
        });
    }

    /**
     * 키워드에 따라 트리를 필터링한다.
     * 이 때 필터링 결과는 filter 프로퍼티를 조정함으로써 표현한다.
     *  
     */
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

    /**
     * 미리 지정한 정렬 타입에 따라 디렉토리 트리를 정렬한다.
     * (Default : ASCENT)
     * 
     * @param {Array} dirTrees 소팅할 디렉토리 트리
     */
    sortDirTree(dirTrees) {
        if (!dirTrees) return dirTrees;

        dirTrees.forEach(dir => {
            dir.child = this.sortDirTree(dir.child);
        });
        
        dirTrees.sort(this.compare);
        return dirTrees;
    }

    /**
     * 초기화한 디렉토리 맵과 프로젝트 맵에 따라 디렉토리 트리를 구성한다.
     * 따라서 프로젝트맵과 디렉토리 맵에 초기화되었음이 보장되어야한다.
     * 
     */
    buildDirTrees() {
        let projectMap = this.projectMap;       // 초기화한 프로젝트 맵
        let treeMap = this.treeMap;             // 초기화한 디렉토리 맵
        let dirTrees = this.dirTrees;           // 이전 or 초기화할 디렉토리 트리

        /* 디렉토리 트리 및 하위 트리 비우기 */
        dirTrees.length = 0;                    
        Object.keys(treeMap).forEach(key => {
            treeMap[key].child.length = 0;
        });
        /* 디렉토리 트리 및 하위 트리 비우기 끝 */

        /* 각 디렉토리의 부모-자식 관계 연결 */        
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
        /* 각 디렉토리의 부모-자식 관계 연결 끝 */        

        /* 소팅 기준에 따른 비교함수 등록과 소팅 */
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
        /* 소팅 기준에 따른 비교함수 등록과 소팅 끝 */
    }

    /**
     * 디렉토리 트리를 반환한다.
     * 
     */
    getDirTree() {
        return this.dirTrees;
    }

    /**
     * 타겟 디렉토리를 열거나 닫는다.
     * 
     * @param {String} did 타겟 디렉토리 아이디
     */
    toggleIsOpen(did) {
        this.treeMap[did].isOpen = !this.treeMap[did].isOpen;
    }
}

export default KssTree;

/* 소팅 관련 정의 */
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
/* 소팅 관련 정의 끝 */

