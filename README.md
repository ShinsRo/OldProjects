### 직접 배포 설정 메모

#### 1. maven으로 직접 배포

##### eclipse 내 maven build를 통한 jar 빌드

```프로젝트 루트/frontend``` 에서 ```yarn run build```를 통해 프론트엔드 프로젝트를 빌드합니다.

이후 eclipse 에서 다음과 같은 순서를 따릅니다.
1. 프로젝트 우클릭, Run 클릭
2. Maven Build... 클릭
3. goals 란에 ```clean install spring-boot:repackage```를 입력
4. apply 클릭, Run

```프로젝트 루트/target``` 아래 생성된 ```upmureport-VERSION.jar``` 파일이 나타납니다.

##### Jenkins 자동 배포를 통한 빌드
