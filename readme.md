# Midas2018 Example Project
---
<!--
  최근 수정일 : 2018.05.20
  작성자 : 김승신
  버전 : 0.0
-->

토의에 도움도 될 겸 프로젝트 환경을 이렇게 말씀드려봐야지 하고 만들어봤어요. 부담없이 보셨으면 합니다. 버전이 다른 문제가 있을까봐 JDK1.8.0_171 이랑 mySQL, 커넥터를 최신 버전으로 받아서 구글 드라이브에 올려놓았어요. 아래 표 하단에 적혀있으니 필요하시담 받아가셔도 될 것 같습니다. (모두 윈도우용 파일들입니다.)
IntelliJ랑 스프링 부트가 익숙하지 않아 미숙한 부분이 많습니다. 아니다 싶은게 보이시면 말씀해주세요.

## 환경
| 영역 | 적용환경 | 비고 |
| :------------- | :------------- | :------------- |
| IDE   | IntelliJ |   |
| 데이터베이스 | mySQL 8.0.11 <br> myBatis <br>  | MySQL Connector/ODBC 8.0.11 (예비) <br> [임시 설정] <br> user : midas <br> password : challenge <br> database : midas <br> port : 3306 |
| JDK   | JDK  | jdk1.8.0_171  |
| 서버 프레임워크   | spring boot 2.0.2  <br> | devPort : 8888 <br> port : |
| 의존성   | maven  |    |
| 뷰 프레임워크   | vueJs <br> { vutifyJs 1.0.18 } | devPort : 8080 |
| 형상관리   | git  |  https://github.com/orgs/MidasChallenge2018/ |
| 공용 자료실   | Google Drive  | https://drive.google.com/open?id=1Jf3QmbvPREdtm1B6ImHDRl9D1lQRE6Y7 |

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

#### _demo 디렉토리는 임시 디렉토리입니다.
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
