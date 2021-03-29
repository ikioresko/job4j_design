create table course(
    id serial primary key,
    num int
 );

create table students(
	id serial primary key,
    name varchar(100),
	age int,
    course_id int references course(id)
 );

insert into course(num) values ('1');
insert into course(num) values ('2');
insert into course(num) values ('3');
insert into course(num) values ('4');
insert into course(num) values ('5');

insert into students(name, age, course_id) values('Tom', 19, 1);
insert into students(name, age, course_id) values('Ron', 20, 2);
insert into students(name, age, course_id) values('Dan', 23, 5);
insert into students(name, age, course_id) values ('Tim', 17, 1);
insert into students(name, age, course_id) values ('Sam', 21, 3);

insert into students(name) values ('Jane');

select * from students join course c on students.course_id = c.id; 
select s.name, c.num from students as s join course as c on s.course_id = c.num;
select s.name as Имя, c.num as Курс from students as s join course as c on s.course_id = c.num;
select s.name as Имя, s.age as Возраст, c.num as Курс from students as s join course as c on s.course_id = c.num;