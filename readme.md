# Midas2018 Example Project
---
<!--
  최근 수정일 : 2018.05.20
  작성자 : 김승신
  버전 : 0.0
-->
## 환경
| 영역 | 적용환경 | 비고 |
| :------------- | :------------- | :------------- |
| IDE   | IntelliJ |   |
| 데이터베이스 | mySQL 8.0.11 <br> myBatis <br>  | MySQL Connector/ODBC 8.0.11 (예비) <br> [임시 설정] <br> user : midas <br> password : challenge <br> database : midas <br> port : 3306 |
| JDK   | JDK  | jdk1.8.0_171  |
| 서버 프레임워크   | spring boot 2.0.2  <br> | devPort : 8888 <br> port : |
| 의존성   | maven  |    |
| 뷰 프레임워크   | vueJs <br> vutifyJs 1.0.18 | devPort : 8080 |
| 형상관리   | git  |  https://github.com/orgs/MidasChallenge2018/ |
| 공용 자료실   | Google Drive  |   |

---

## 프로젝트 디렉토리 구조

```c
root.
/* 프론트 엔드 디렉토리
*********************/
+---frontend            
|   +---build
|   +---config
|   +---node_modules
|   +---src
|   |   +---assets
|   |   +---components
|   |   \---router
|   \---static
/* 백 엔드 디렉토리
*********************/
+---src
|   +---main
|   |   +---java
|   |   |   \---com
|   |   |       \---midas2018
|   |   |           \---root
|   |   |               +---index
|   |   |               \---_demo
|   |   |                   \---domain
|   |   \---resources
|   |       +---static
|   |       \---templates
|   \---test
|       \---java
|           \---com
|               \---midas2018
|                   \---root
```

#### _demo 디렉토리
```cpp
+---root
|   +---index
|   |   \---IndexController.java   
|   \---_demo
|       +---DemoUserController.java
|       +---DemoUserMapper.java
|       \---domain
|           \---DemoUserVO.java
```

데이터 베이스 접근 테스트를 위한 임시 디렉토리입니다. MyBatis를 어노테이션 스타일로 연동했습니다. 간단한 getById를 구현해서 넣어놓았습니다.
