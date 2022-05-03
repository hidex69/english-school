--liquibase formatted sql

--changeset owo:2


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
