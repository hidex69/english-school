--liquibase formatted sql

--changeset owo:1

create table if not exists blog(
                                   id serial primary key,
                                   text text,
                                   posting_date date not null,
                                   title varchar(50) not null
);

create table if not exists user_usr(
                                       id serial primary key,
                                       email varchar(50) not null,
                                       name varchar(50) not null,
                                       surname varchar(50) not null,
                                       user_role int not null,
                                       deleted boolean not null
);

create table if not exists blog_comment(
                                           id serial primary key,
                                           posting_date date not null,
                                           blog_id int not null,
                                           user_id int not null,
                                           text varchar(150),
                                           foreign key(user_id) references user_usr(id),
                                           foreign key(blog_id) references blog(id)
);

create table if not exists user_info(
    id serial primary key,
    user_id int not null unique,
    password varchar(150),
    foreign key(user_id) references user_usr(id)
);

create table if not exists notification(
                                           id serial primary key,
                                           text varchar(250) not null,
                                           recipient_id int not null,
                                           receiving_time date not null,
                                           foreign key(recipient_id) references user_usr(id)
);

create table if not exists group_gr(
                                       id serial primary key,
                                       name varchar(250) not null,
                                       teacher_id int not null,
                                       foreign key(teacher_id) references user_usr(id)
);

create table if not exists group_user(
                                         group_id int not null,
                                         user_id int not null,
                                         foreign key(group_id) references group_gr(id),
                                         foreign key(user_id) references user_usr(id)
);

create table if not exists timetable(
                                        id serial primary key,
                                        group_id int not null,
                                        start_date date not null,
                                        end_date date not null,
                                        days_of_week varchar(20) not null,
                                        lesson_start varchar(20) not null
);

create table if not exists hometask(
                                       id serial primary key,
                                       name varchar(50) not null,
                                       data bytea not null,
                                       content_type varchar(50) not null,
                                       end_date date not null
);

create table if not exists user_hometask(
                                            id serial primary key,
                                            hometask_id int not null,
                                            user_id int not null,
                                            mark int,
                                            is_done boolean not null,
                                            foreign key(hometask_id) references hometask(id),
                                            foreign key(user_id) references user_usr(id)
);

create table if not exists attachment(
                                          id serial primary key,
                                          data bytea not null,
                                          content_type varchar(50) not null,
                                          entity_type int not null,
                                          entity_id int not null,
                                          name varchar(50) not null
);

create table if not exists course_rating(
                                            id serial primary key,
                                            text text,
                                            user_id int not null,
                                            mark int not null,
                                            rating_date date not null,
                                            foreign key(user_id) references  user_usr(id)
);
