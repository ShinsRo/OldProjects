create table account_member(
   id varchar2(50) primary key,
    password varchar2(50) not null,
   name varchar2(50) not null,
   total number default 0
)

create table account_book(
   no varchar2(50) primary key,
   today date not null,
   detail varchar2(100) not null,
   income number default 0,
   spend number default 0,
   id varchar2(50) not null,
   constraint fk_id_account foreign key(id) references account_member(id)
)


create sequence account_seq
drop sequence account_seq;
drop table account_book;

-- 기존에 account_book table존재해서 지우고 생성합시다
insert into ACCOUNT_MEMBER(id,password,name,total) values('java','1234','임소영',0)
insert into ACCOUNT_MEMBER(id,password,name,total) values('ppadam','1234','정현지',0)
insert into ACCOUNT_MEMBER(id,password,name,total) values('qwe','1234','오남준',0)
2017/04/06
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













insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/1 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',1000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/2 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',3000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/3 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',5000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/4 9:12:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출2',4000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/5 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',1000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/6 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',3000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/7 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',5000,'java');

insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/8 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',10000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/9 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',40000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/10 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',30000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/11 9:12:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출2',50000,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/12 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',80000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/13 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',10000,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/14 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',80000,'java');

insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/15 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',1200,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/16 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',3200,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/17 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',5200,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/18 9:12:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출2',4200,'java');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/19 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',1200,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/20 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',2200,'java');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/21 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',5200,'java');

insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/22 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',30000,'qwer');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/23 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',30000,'qwer');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/24 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',80000,'qwer');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/25 9:12:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출2',10000,'qwer');
insert into ACCOUNT_BOOK(no,today,detail,spend,id) values(account_seq.nextval,to_date('2017/1/26 9:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 지출1',60000,'qwer');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/27 10:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입1',20000,'qwer');
insert into ACCOUNT_BOOK(no,today,detail,income,id) values(account_seq.nextval,to_date('2017/1/28 18:00:10', 'yyyy/mm/dd hh24:mi:ss'),'이번 날 수입2',20000,'qwer');

