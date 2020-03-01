

insert into member(mid, eid, name, birth, phoneNum)
	values('1111', '1111', 'nas', '김승신', '인턴');
	
insert into user(userId, userPass, userName, posi, dept, deleteFlag)
	values('1112', 'nas', '김윤상', '인턴', '인턴부', false);
	
insert into user(userId, userPass, userName, posi, dept, deleteFlag)
	values('1113', 'nas', '마규석', '인턴', '인턴부', false);
	
insert into user(userId, userPass, userName, posi, dept, deleteFlag)
	values('111138', 'nas', '김팀장', '팀장', '연구소', false);

insert into user(userId, userPass, userName, posi, dept, deleteFlag)
	values('111123', 'nas', '김사원', '사원', '연구소', false);
	
insert into project(
		projId, projProgress, 
		projName, projSubject, projDesc, 
		projCaleGubun, deleteFlag)
	values(300, 20, '일일업무보고 프로젝트', '일일업무보고 체계 도입', '도입을 통한 생산성 향상', '주기성', false);
	
insert into project(
		projId, projProgress, 
		projName, projSubject, projDesc, 
		projCaleGubun, deleteFlag)
	values(400, 100, '업무리포트 웹 버전 프로젝트', '업무리포트 웹 버전 이전', '버전 이전을  통한 생산성 향상', '비주기성', false);

insert into project(
		projId, projProgress, 
		projName, projSubject, projDesc, 
		projCaleGubun, deleteFlag)
	values(500, 60, '프로젝트 알파', '코딩계 알파고 빌드', '코딩 머신~', '비주기성', false);

insert into userProject(
		userProjectId, projId, userId, 
		projStat, deleteFlag)
	values(300, 300, 1111, 1, false);
	
insert into userProject(
		userProjectId, projId, userId, 
		projStat, deleteFlag)
	values(100, 400, 1111, 5, false);
	
insert into userProject(
		userProjectId, projId, userId, 
		projStat, deleteFlag)
	values(200, 500, 1111, 0, false);

# 디렉토리
insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(1001, '1111', 300, null, '2019년 업무보고', false);

insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(10011, '1111', 300, 1001, '1월 업무보고', false);

insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(10012, '1111', 300, 1001, '2월 업무보고', false);
	
insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(10013, '1111', 300, 1001, '3월 업무보고', false);
	
insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(1002, '1111', 300, null, '2018년 업무보고', false);
	
insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(10021, '1112', 300, 1002, '3월 업무보고', false);
	
insert into dir(
		dirId, userId, parentProjId, parentDirId, 
		dirName, deleteFlag)
	values(1003, '1112', 400, null, '웹 리소스 폴더', false);
	
delete from dir;
select dirId from dir where parentProjId = 32 and ;
select * from authinfo where username = 'm1111';
select * from project where ;