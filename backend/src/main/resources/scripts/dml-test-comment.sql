insert into roles (name) values ('ROLE_USER');
insert into roles (name) values ('ROLE_INSTRUCTOR');
-- owner
insert into users (id) values('cb71df0c-5df9-4252-9840-e35330158645');
-- instructor
insert into users (id) values('27f8bb93-1a40-4f71-97b5-34c6c32fd200');
-- stranger
insert into users (id) values('70a1fc72-ff2d-45c0-a607-f880f2902f46');

insert into user_role (user_id, role_id) values('cb71df0c-5df9-4252-9840-e35330158645', 1);
insert into user_role (user_id, role_id) values('27f8bb93-1a40-4f71-97b5-34c6c32fd200', 1);
insert into user_role (user_id, role_id) values('70a1fc72-ff2d-45c0-a607-f880f2902f46', 1);
insert into user_role (user_id, role_id) values('27f8bb93-1a40-4f71-97b5-34c6c32fd200', 2);

insert into courses (name, description) values('course1', 'description');

insert into listener_on_course (course_id, listener_Id) values(1, 'cb71df0c-5df9-4252-9840-e35330158645');

insert into course_instructor (course_id, instructor_id) values(1, '27f8bb93-1a40-4f71-97b5-34c6c32fd200');

insert into lesson (name, description, course_id) values('lesson1', 'description', 1);

insert into homeworks (lesson_id, user_id) values(1, 'cb71df0c-5df9-4252-9840-e35330158645');