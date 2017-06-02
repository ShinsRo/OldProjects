-- DROP ALL
DROP TABLE APPLICATION;
DROP TABLE TRANSACTION;
DROP TABLE G_PRODUCT;
DROP TABLE LOC_COMMENT;
DROP TABLE G_BOARD;
DROP TABLE GD_MEMBER;

-- SEQUENCES
DROP SEQUENCE B_SEQ;
DROP SEQUENCE T_SEQ;
DROP SEQUENCE P_SEQ;
DROP SEQUENCE A_SEQ;

-- B : BOARD / T : TRANSACTION / P : PRODUCT / A : APPLICATION
CREATE SEQUENCE B_SEQ;
CREATE SEQUENCE T_SEQ;
CREATE SEQUENCE P_SEQ;
CREATE SEQUENCE A_SEQ;

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
-- SELECT
SELECT * FROM GD_MEMBER;
SELECT * FROM LOC_COMMENT;
select * from LOC_COMMENT;
select * from APPLICATION;
select * from TRANSACTION;

-- TRANSACTION, APPLICATION TEST SQL
select * from TRANSACTION;
SELECT ANO,REASON,ID FROM APPLICATION;

-- TEST MEMBER
INSERT INTO GD_MEMBER(ID, NAME, PASSWORD, ADDR, ADDR_DETAIL, TEL, JOB) VALUES('java', '딘딘', '1234', '경기도 성남시 분당구', '삼평동 670','01012345678','취준생');

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

--방문자

SELECT * from visit;
drop table visit
select * from visit where date=sysdate;
select count(*) from visit where date=sysdate;
select to_char(sysdate,'YYYY.MM.DD') as sdate from dual;
select * from DATE_TEST


select sdate from (select to_char(sysdate,'YYYY.MM.DD') as sdate, row_number() over(order by sysdate desc) as rnum from DATE_TEST) where rnum=1
select count(*) from visit where day='2017.05.31';
insert into visit(day) values('2017.05.31');
update visit set count=count+1 where day='2017.05.31';
select count from visit where day='2017.05.31';

insert into visit(id, day, count) values('java','2017.05.31', 0)
select * from visit
select * from visit where id='java' and day='2017.05.31'
update visit set count=count+1 where id='java' and day='2017.05.31'
select count(*) from visit where day='2017.05.31'

--
select ANO,BNO,REASON,PNOS,IS_SELECTED,ID from APPLICATION where id='spring';
