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





