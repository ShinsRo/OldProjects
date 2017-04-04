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
   inAndOut number default 0,
   id varchar2(50) not null,
   constraint fk_id_account foreign key(id) references account_member(id)
)

create sequence account_seq

drop table account_book;

-- 기존에 account_book table존재해서 지우고 생성합시다
insert into ACCOUNT_MEMBER(id,password,name,total) values('java','1234','임소영',0)
insert into ACCOUNT_BOOK(no,today,detail,inAndOut,id) values(account_seq.nextval,sysdate,'빠담 아메리카노',1000,'java')
select * from ACCOUNT_BOOK

select *
from ACCOUNT_MEMBER m, ACCOUNT_BOOK b
where m.id = b.id;