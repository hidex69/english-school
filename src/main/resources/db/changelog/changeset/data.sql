--liquibase formatted sql

--changeset owo:2

insert into user_usr(email, name, surname, user_role, deleted) values
('test1@mail.ru', 'Ivan', 'Petrov', 1, false),
('test2@mail.ru', 'Ivan', 'Petrov', 1, false),
('test3@mail.ru', 'Ivan', 'Petrov', 1, false),
('test4@mail.ru', 'Ivan', 'Petrov', 1, false),
('test5@mail.ru', 'Ivan', 'Petrov', 1, false),
('test6@mail.ru', 'Ivan', 'Petrov', 1, false),
('test7@mail.ru', 'Ivan', 'Petrov', 1, false),
('test8@mail.ru', 'Ivan', 'Petrov', 1, false),
('test9@mail.ru', 'Ivan', 'Petrov', 1, false),
('test10@mail.ru', 'Ivan', 'Petrov', 1, false);


insert into user_usr(email, name, surname, user_role, deleted) values
('student1@mail.ru', 'Ivan', 'Petrov', 2, false),
('student2@mail.ru', 'Ivan', 'Petrov', 2, false),
('student3@mail.ru', 'Ivan', 'Petrov', 2, false),
('student4@mail.ru', 'Ivan', 'Petrov', 2, false),
('student5@mail.ru', 'Ivan', 'Petrov', 2, false),
('student6@mail.ru', 'Ivan', 'Petrov', 2, false),
('student7@mail.ru', 'Ivan', 'Petrov', 2, false),
('student8@mail.ru', 'Ivan', 'Petrov', 2, false),
('student9@mail.ru', 'Ivan', 'Petrov', 2, false),
('student10@mail.ru', 'Ivan', 'Petrov', 2, false);



insert into group_gr(name, teacher_id) values
('group1', 1),
('group2', 2);

insert into group_user(group_id, user_id) values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8);
