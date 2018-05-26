use midas;

drop table if exists user;
create table user (
	email varchar(128) primary key not null,
    password varchar(512) not null,
    name varchar(128) default 'Unknown',
    isAccountNonExpired boolean, 
    isAccountNonLocked boolean, 
    isCredentialsNonExpired boolean, 
    isEnabled boolean 
);

drop table if exists authority;
create table authority_t (
	email varchar(128),
	constraint fk_user_email foreign key(email) references user(email),
    authority_name varchar(128)
);

insert into user values('admin@midas.com', 'midas', 'admin', true, true, true, true);