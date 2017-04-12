--회원 테이블
create table account_member(
   id varchar2(50) primary key,
   password varchar2(50) not null,
   name varchar2(50) not null,
   total number default 0,
   limit number default 0,
   authority number default 0
);
-- 가계부 테이블
create table account_book(
   no varchar2(50) primary key,
   today date not null,
   detail varchar2(100) not null,
   income number default 0,
   spend number default 0,
   id varchar2(50) not null,
   constraint fk_id_account foreign key(id) references account_member(id)
);
-- 자유상담게시판 테이블
create table free_board(
	board_no number primary key,
   title varchar2(50) not null,
   id varchar2(50),
   content clob,
	time_posted date not null,
   constraint fk_id_board foreign key(id) references account_member(id)
);
--자유상담게시판 댓글 테이블
create table board_comment(
  com_no number primary key,
   content varchar2(1000) not null,
   depth number default 0,
   id varchar2(50) not null,
   parrent_com_no number default 0,
   board_no number,
   constraint fk_id_comment foreign key(id) references account_member(id),
   constraint fk_no_board foreign key(board_no) references free_board(board_no)
);

-- 게시판 시리얼 넘버, 코멘트 시리얼 넘버 시퀸스
drop sequence board_seq;
create sequence board_seq;
drop sequence com_seq;
create sequence com_seq;

-- 멤버, 가계부 시리얼 넘버 시퀸스
create sequence account_seq;
drop sequence account_seq;

-- drop
drop table board_comment;
drop table free_board;
drop table account_book;
drop table account_member;

-- 게시글 테스트 인서트
insert into free_board(board_no, title, id, content, time_posted)
	values(board_seq.nextval, 'test1', 'java', 'content1', sysdate);

-- 게시글 셀렉트
select * from free_board;
select board_no, title, id, content, time_posted 
	from free_board where board_no = 1;

delete from free_board where board_no=1;
-- 댓글 인서트 테스트
insert into board_comment(com_no, content, depth, id, board_no)
	values(com_seq.nextval, 'comment_test1', 0, 'java', 1);
insert into board_comment(com_no, content, depth, id, board_no, parrent_com_no)
	values(com_seq.nextval, 'comment_test1', 1, 'java', 1,1);
	
delete from board_comment where board_no = 1;
-- 댓글 셀렉트
select * from board_comment;
-- boardDAO SQL test
select A.* from (select row_number() over(order by board_no desc) as rnum,
board_no, title, to_char(time_posted, 'YY.MM.DD') as time_posted, id 
from free_board) A where rnum between 1 and 5
	-- getDetail
	select board_no, title, id, content,
		to_char(time_posted, 'YYYY/MM/DD HH24:MI:SS')
		from free_board where board_no=1
	select com_no, content, depth, id, parrent_com_no 
		from board_comment where board_no = 1;
-- 기존에 account_book table존재해서 지우고 생성합시다
insert into ACCOUNT_MEMBER(id,password,name,total,limit, authority) values('java','1234','임소영',10000,100000, 0)

insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,sysdate,'월급 ',100000,'java'); --income
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,sysdate,'용돈 ',100000,'java'); --income


insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,sysdate,'빠담 아메리카노',1000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,sysdate,'청년다방',5000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,sysdate,'자몽에이드',5000,'java');

insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/1 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',1000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/1 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',3000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/1 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',5000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/1 9:12:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출2',4000,'java');

insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,sysdate,'빠담 아메리카노',2000,'ppadam');
select * from ACCOUNT_BOOK;
delete from ACCOUNT_BOOK where id='ppadam';

--getAllList
select m.name,m.total,b.no,
to_char(b.today,'yyyy/mm/dd') as today,b.detail,b.income,b.spend,b.id
from ACCOUNT_MEMBER m, ACCOUNT_BOOK b 
where m.id = b.id and m.id='java';


--getDetailList
select m.name,m.total,b.no,
to_char(b.today,'yyyy/mm/dd') as today,to_char(b.today,'HH24:MI:SS') as time,b.detail,b.income,b.spend,b.id
from ACCOUNT_MEMBER m, ACCOUNT_BOOK b 
where m.id = b.id and m.id='java' and to_char(b.today,'yyyy/mm/dd')='2017/04/06';

--update

update ACCOUNT_BOOK set today=to_date('2017/1/1 9:12:10', 'yyyy/mm/dd hh24:mi:ss'), spend=5000,detail='애비로드' where no=1 and id='java'


select * from account_book
select * from ACCOUNT_MEMBER


update account_book set today=to_date('2017/04/08','yyyy/mm/dd hh24:mi:ss'), 
income=5000,detail='수정테스트'
where no='1'










