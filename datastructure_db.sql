drop database datastructuredb;
drop user datastructure;
create user datastructure with password 'password';
create database datastructuredb with template=template0 owner = datastructure;
\connect datastructuredb;
alter default privileges grant all on tables to datastructure;
alter default privileges grant all on sequences to datastructure;

create table ds_users(
user_id integer primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null
);

create table ds_categories(
category_id integer primary key not null,
user_id integer not null,
title varchar(30) not null,
description varchar(50) not null
);
alter table ds_categories add constraint cat_users_fk
foreign key (user_id) references ds_users(user_id);

create table ds_transactions(
transaction_id integer primary key not null,
category_id integer not null,
user_id integer not null,
amounnt numeric(10, 2) not null,
note varchar(50) not null,
transaction_date bigint not null
);
alter table ds_transactions add constraint trans_cat_fk
foreign key (category_id) references ds_categories(category_id);
alter table ds_transactions add constraint trans_user_fk
foreign key (user_id) references ds_users(user_id);

create sequence ds_users_seq increment 1 start 1;
create sequence ds_categories_seq increment 1 start 1;
create sequence ds_transactions_seq increment 1 start 1000;