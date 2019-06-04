# 📊 UPMUREPORT-WEB 

## 🔗 목차

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [🔗 목차](#목차)
* [🍈 UPMUREPORT-WEB 둘러보기(가이드)](#upmureport-web-둘러보기가이드)
	* [1. 프로젝트 관리하기](#1-프로젝트-관리하기)
		* [프로젝트의 생성](#프로젝트의-생성)
		* [프로젝트의 수정](#프로젝트의-수정)
		* [프로젝트의 삭제](#프로젝트의-삭제)
	* [2. 디렉토리 관리하기](#2-디렉토리-관리하기)
		* [디렉토리의 생성](#디렉토리의-생성)
		* [디렉토리의 수정](#디렉토리의-수정)
		* [디렉토리의 삭제](#디렉토리의-삭제)
	* [3. 업무 관리하기](#3-업무-관리하기)
		* [임시 소제목](#임시-소제목)
	* [4. 첨부파일 관리하기](#4-첨부파일-관리하기)
		* [임시 소제목](#임시-소제목-1)
	* [5. 로그 사용법](#5-로그-사용법)
		* [임시 소제목](#임시-소제목-2)
	* [6. 관리자 기능 사용하기](#6-관리자-기능-사용하기)
		* [임시 소제목](#임시-소제목-3)
* [📣 운영/개발 시 유의사항](#운영개발-시-유의사항)
	* [1. 운영 OS에 DB관련 환경변수는 필수입니다!](#1-운영-os에-db관련-환경변수는-필수입니다)
	* [2. 개발 IDE에도 환경변수 추가하는 것을 잊지마세요!](#2-개발-ide에도-환경변수-추가하는-것을-잊지마세요)

<!-- /code_chunk_output -->

<br/>
<br/>

## 🍈 UPMUREPORT-WEB 둘러보기(가이드)

<table>
    <tr>
        <td colspan="2">
            <img src="./docs/images/1.0_intro/MainPage.png">
        </td>
    </tr>
	<tr>
		<td>
			<img src="./docs/images/1.0_intro/LoginPage.png">
		</td>
		<td>
			<img src="./docs/images/1.0_intro/MainPage2.png">
		</td>
	</tr>
</table>

 **UPMUREPORT-WEB**은 기존 upmureport.exe (C# 프로그램)의 **핵심 기능을 그대로 보전**하면서 **더 나은 인터페이스를 제공**하기 위해 만들어진 웹 앱입니다. **앱을 이용해 더 쉽고, 간편하게 업무를 관리**해보세요! 😄 

 **UPMUREPORT-WEB**이 처음이시라면, 아이디를 관리자로부터 발급받고 로그인한 뒤 아래의 가이드를 따르세요.
<br/>

### 1. 프로젝트 관리하기

#### 프로젝트의 생성

#### 프로젝트의 수정
 
#### 프로젝트의 삭제

### 2. 디렉토리 관리하기

#### 디렉토리의 생성

#### 디렉토리의 수정

#### 디렉토리의 삭제

### 3. 업무 관리하기

#### 임시 소제목

### 4. 첨부파일 관리하기

#### 임시 소제목

### 5. 로그 사용법

#### 임시 소제목

### 6. 관리자 기능 사용하기

#### 임시 소제목

## 📣 운영/개발 시 유의사항

빌드 시 따로 프론트엔드 코드를 빌드하실 필요가 없습니다! maven 설정에 따라 maven package 시 prepare phase에서 react 코드가 빌드됩니다. 따라서 maven package만 실행하여 패키징하시면 되겠습니다. 단 스프링의 프로파일을 prod로 설정하고, 프론트엔드 코드 내의 API_CONSTANT에서 BASE_URL을 서버 URL로 설정한 뒤 패키징하셔야합니다.

### 1. 운영 OS에 DB관련 환경변수는 필수입니다!

UPMUREPORT-WEB은 아래와 같은 OS의 환경변수를 사용합니다.

    0. SERVER_PORT              // 서버 포트
    1. DATASOURCE_URL           // 운영DB 주소
    2. DATASOURCE_USERNAME      // 운영DB USERNAME
    3. DATASOURCE_PASSWORD      // 운영DB PASSWORD

따라서 OS 환경변수에 위 변수를 반드시 정의해야합니다.

### 2. 개발 IDE에도 환경변수 추가하는 것을 잊지마세요!

마찬가지로 개발 IDE에도 환경변수를 추가하셔야합니다. 아래는 Eclipse IDE의 Run Config 예시 화면과 환경 변수 목록입니다.

    0. SERVER_PORT              // 서버 포트
    1. DATASOURCE_URL           // 운영DB 주소
    2. DATASOURCE_USERNAME      // 운영DB USERNAME
    3. DATASOURCE_PASSWORD      // 운영DB PASSWORD
    4. DATASOURCE_URL_DEV
    5. DATASOURCE_USERNAME_DEV
    6. DATASOURCE_PASSWORD_DEV

<table>
    <tr>
        <td colspan="2">
            <img src="./docs/images/2.x/RunConfig.png">
        </td>
    </tr>
</table>

재개발 시 application.yml 의 프로파일을 'dev'로 변경하는 것과 frontend의 BASE_URL을 local로 바꾸는 것을 잊지마세요!