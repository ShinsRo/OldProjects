drop table test1;

create table test1(
	id INT,
	val varchar(30),
	CONSTRAINT PK_test1 PRIMARY KEY (id)
);

insert into test1(id, val) values(1, 'test');
insert into test1(id, val) values(2, 'test2');
insert into test1(id, val) values(3, 'test3');

select * from test1 where id = 0;