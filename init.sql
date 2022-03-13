drop database if exists english_school;
create database english_school with encoding utf8;

\c english_school;

drop table if exists user_table;
create table if not exists user_table(
    id serial primary key,
    name varchar(50) not null,
    surname varchar(50) not null,
    email varchar(50) not null
);

drop table if exists blog_comment;
create table if not exists blog_comment(
    id serial primary key,
    text varchar(150)
);

drop table if exists blog;
create table if not exists blog(
    id serial primary key,
    comment_id int unique,
    data text,
    foreign key (comment_id) references blog_comment(id)
);