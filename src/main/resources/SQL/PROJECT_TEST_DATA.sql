insert into user(userId, suerPass, userName, posi, dept, deleteFlag)
	values('1111', 'nas', '김승신', '인턴', '인턴부', false);
	
insert into user(userId, suerPass, userName, posi, dept, deleteFlag)
	values('1112', 'nas', '김윤상', '인턴', '인턴부', false);
	
insert into user(userId, suerPass, userName, posi, dept, deleteFlag)
	values('1113', 'nas', '마규석', '인턴', '인턴부', false);
	
	
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
		projId, userId, 
		projStat, deleteFlag)
	values(300, 1111, 1, false);
	
insert into userProject(
		projId, userId, 
		projStat, deleteFlag)
	values(400, 1111, 5, false);
	
insert into userProject(
		projId, userId, 
		projStat, deleteFlag)
	values(500, 1111, 0, false);
