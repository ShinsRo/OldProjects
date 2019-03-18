## UPMUREPORT 프론트엔드 개발 프로젝트 설명

### 디렉토리 설명
```
src/
│  index.js
│  Root.js
│  serviceWorker.js
│
├─actions/
├─api/
├─components/
│  ├─Header/
│  │      Header.js
│  │      index.js
│  └─Sidebar/
│         index.js
│         Sidebar.js
│
├─containers/
│      App.js
│      Dashboard.js
│      DevTest.js
│      index.js
│      Login.js
│
├─reducers/
└─stores/
```
1. Root.js 가 시작점이라 생각하면 됨
2. actions, containers, reducers, stores 폴더는 redux를 위한 폴더
3. containers는 component보다 큰 틀이라고 (page 정도 단위의) 생각하면 됨
4. api는 비동기 통신 메서드를 모아두는 곳. 예제가 들어있으니 참조.