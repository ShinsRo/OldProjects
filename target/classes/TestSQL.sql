-- DROP ALL
DROP TABLE APPLICATION;
DROP TABLE TRANSACTION;
DROP TABLE G_PRODUCT;
DROP TABLE LOC_COMMENT;
DROP TABLE G_BOARD;
DROP TABLE GD_MEMBER;
DROP TABLE REPORT;
DROP TABLE AUTOSEARCH;
-- SEQUENCES
DROP SEQUENCE B_SEQ;
DROP SEQUENCE T_SEQ;
DROP SEQUENCE P_SEQ;
DROP SEQUENCE A_SEQ;
DROP SEQUENCE C_SEQ;
DROP SEQUENCE CR_SEQ;
DROP SEQUENCE RE_SEQ;
-- B : BOARD / T : TRANSACTION / P : PRODUCT / A : APPLICATION
CREATE SEQUENCE B_SEQ;
CREATE SEQUENCE T_SEQ;
CREATE SEQUENCE P_SEQ;
CREATE SEQUENCE A_SEQ;
CREATE SEQUENCE C_SEQ;
CREATE SEQUENCE CR_SEQ;
CREATE SEQUENCE RE_SEQ;
-- 멤버 TEST SQL
INSERT INTO GD_MEMBER(ID,NAME,PASSWORD,ADDR,ADDR_DETAIL,TEL,JOB,ENABLED,IS_CONFIRMED)
VALUES('java','임진우','1234','경기도 광주시 회덕길','상세주소','01012341234','코스타',1,'NO');
INSERT INTO authorities(ID,AUTHORITY) VALUES('admin','ROLE_ADMIN');
-- 댓글 TEST SQL
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'일빠1',1,0,1);
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'이빠2',2,0,1);
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'삼빠3',3,0,1);
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,1,'새치기',1,1,2);


		select * from (select cno, title, hit,
		to_char(time_posted,'YYYY.MM.DD HH:mm:ss') as
		time_posted, addr, id,
		content, picno, row_number() over(order by cno desc) as rnum,
		(select count(rno) from LOC_COMMENT_REPLY lr where lr.cno = lc.cno) as reply_cnt 
		from
		loc_comment lc) where rnum between 1 and 5
select * from (select cno, title, hit, to_char(time_posted,'YYYY.MM.DD
		HH:mm:ss') as
		time_posted, addr, id, content, picno, row_number() over(order
		by cno desc) as rnum, 
		(select count(rno) from LOC_COMMENT_REPLY lr where lr.cno = lc.cno) as reply_cnt 
		from loc_comment lc where id = 'test') where rnum between 1 and 5
SELECT rno,cno,id,name,TO_CHAR(TIME_POSTED,'YYYY.MM.DD HH24:MI') as time_posted,
parent,content,gno,dept,order_no 
FROM LOC_COMMENT_REPLY WHERE cno=1 ORDER BY GNO ASC
select img_path from LOC_COMMENT_PICTURE where picno = 2 and pic_cursor = 1
-- SELECT
SELECT * FROM G_BOARD;
SELECT * FROM GD_MEMBER;
SELECT * FROM LOC_COMMENT;
SELECT * FROM LOC_COMMENT;
SELECT * FROM APPLICATION;
SELECT * FROM TRANSACTION;
SELECT * FROM DELIVERY;
select * from DELIVERY_MATCH;
select * from APPLICATION;
select * from G_BOARD;
select * from question;
select * from authorities;

-- TEST COMMENT
insert into LOC_COMMENT(CNO, TITLE, HIT, TIME_POSTED, ADDR, ID, CONTENT) VALUES(C_SEQ.nextval, 'test1', '0', sysdate, '경기도 성남시 분당구', 'java', '내용');
insert into LOC_COMMENT(CNO, TITLE, HIT, TIME_POSTED, ADDR, ID, CONTENT) VALUES(C_SEQ.nextval, 'test2', '0', sysdate, '경기도 광주시 회덕동', 'java', '내용');

select * from (select cno, title, hit, time_posted, addr, id, content, row_number() over(order by cno desc) as rnumber from
		loc_comment) where rnumber between 1 and 5;
SELECT * FROM LOC_COMMENT_REPLY;
select * from gd_member;

INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, depth, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'일빠1',1,0,1);

INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, depth, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'이빠2',2,0,1);

INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, depth, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'삼빠3',3,0,1);

INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, depth, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,1,'새치기',1,1,2);

SELECT rno,cno,id,name,TO_CHAR(TIME_POSTED,'YYYY.MM.DD HH24:MI') as time_posted,
parent,content,gno,depth,order_no 
FROM LOC_COMMENT_REPLY WHERE cno=1 ORDER BY GNO ASC;


-- 댓글 TEST SQL

INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'일빠1',1,0,1);
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'이빠2',2,0,1);
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,0,'삼빠3',3,0,1);
INSERT INTO LOC_COMMENT_REPLY(RNO, CNO, ID, NAME,TIME_POSTED, PARENT, CONTENT, GNO, DEPT, ORDER_NO)
VALUES(CR_SEQ.NEXTVAL,1,'java','딘딘',sysdate,1,'새치기',1,1,2);

SELECT rno,cno,id,name,TO_CHAR(TIME_POSTED,'YYYY.MM.DD HH24:MI') as time_posted,
parent,content,gno,dept,order_no 
FROM LOC_COMMENT_REPLY WHERE cno=1 ORDER BY GNO ASC


-- delivery matching
select * from application 
where is_selected='SELECTED' and is_delivery='YES' 
and is_done='NO';

select id,name,addr,tel from GD_MEMBER
where id = (
	select id from G_BOARD where bno=2
)

--신고
INSERT INTO REPORT(REPORT_NO, CATEGORY, RENO, ID, REPORTER, WHY, TIME_POSTED, PROCESS)
VALUES(RE_SEQ.NEXTVAL,'comment',1,'java','java00','못생겼음',SYSDATE, 'false');
select * from REPORT;

select * from REPORT where report_no=1;

SELECT CONCAT('s','u','m') total

update report set process=concat(to_char(sysdate,'YYYY.MM.DD HH24:MI'),' 수정') where report_no=1;


update report set process='수정' where report_no=1;
update report set process='반려' where report_no=1;


		
select a.bno,a.pnos,a.is_done,a.id,a.is_selected,m.state from APPLICATION a,DELIVERY_MATCH m
where a.bno = m.bno(+) and a.id=m.aid(+) and a.is_selected='SELECTED'
and m.state is NULL;


select * from DELIVERY_MATCH;
select * from APPLICATION;
select * from G_BOARD;

INSERT INTO AUTOSEARCH(KEYWORD)
VALUES ('가구');
INSERT INTO AUTOSEARCH(KEYWORD)
VALUES ('가지');
INSERT INTO AUTOSEARCH(KEYWORD)
VALUES ('가자미');
INSERT INTO AUTOSEARCH(KEYWORD)
VALUES ('가가어여우유');
INSERT INTO AUTOSEARCH(KEYWORD)
VALUES ('가ㅋㅋ');

insert into authorities(id, authority)  values('admin', 'ROLE_ADMIN')


select a.bno,a.pnos,a.is_done,a.id,a.is_selected,m.state
		from APPLICATION a,DELIVERY_MATCH m
		where a.bno = m.bno(+) and a.id=m.aid(+) and a.is_selected='SELECTED'
		and m.state is NULL
		
		select * from APPLICATION
		
		
select Q_SEQ.nextval from dual;

INSERT INTO QUESTION(qno, title, hit, time_posted, id, content,is_secret,re_ref,q_parent)
		VALUES(#{qno}, #{title}, 0, sysdate, #{id}, #{content},#{is_secret},#{re_ref},#{q_parent})
		
SELECT * FROM
		(
		SELECT ROWNUM AS rnum, data.*
		FROM
			(
				SELECT LEVEL as re_lev,qno,title,hit,
				time_posted, id,content,is_secret,re_ref,q_parent
				FROM QUESTION
				START WITH q_parent=0
				CONNECT BY PRIOR qno=q_parent
				ORDER SIBLINGS BY re_ref desc
			)
		data
		)
WHERE rnum>=1 and rnum<=10;


select count(*) from;


