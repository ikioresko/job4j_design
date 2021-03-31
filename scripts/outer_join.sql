create table departments(
	id serial primary key,
	name varchar(50)
);

create table employees(
	id serial primary key,
	name varchar(50),
	departments_id int
);

insert into departments(name) values('IT');
insert into departments(name) values('HR');
insert into departments(name) values('Manage');
insert into departments(name) values('Marketing');
insert into departments(name) values('Sales');
insert into departments(name) values('Shipping');


insert into employees(name, departments_id) values('Семен', '1');
insert into employees(name, departments_id) values('Василий', '1');
insert into employees(name, departments_id) values('Артем', '2');
insert into employees(name, departments_id) values('Михаил', '3');
insert into employees(name, departments_id) values('Вадим', '4');
insert into employees(name, departments_id) values('Алексей', '1');
insert into employees(name, departments_id) values('Владимир', '4');
insert into employees(name, departments_id) values('Николай', '1');
insert into employees(name) values('Леонид');

select * from departments d left join employees e on d.id = e.departments_id;
select * from departments d right join employees e on d.id = e.departments_id;
select * from departments d full join employees e on d.id = e.departments_id;
select * from departments cross join employees;

select * from departments d left join employees e on d.id = e.departments_id where e.name is null;
select * from employees e right join departments d on d.id = e.departments_id where e.name is null;

create table teens(
	id serial primary key,
	name varchar(50) unique,
	gender varchar(1)
);

insert into teens(name, gender) values('Sam', 'm');
insert into teens(name, gender) values('Tim', 'm');
insert into teens(name, gender) values('Tom', 'm');
insert into teens(name, gender) values('Jane', 'f');
insert into teens(name, gender) values('Sara', 'f');

select (t.name||' + '||w.name) as pair from teens t
cross join teens w
where t.gender != w.gender;