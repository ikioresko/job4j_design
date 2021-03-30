create table devices(
    id serial primary key,
    name varchar(255),
    price float
);

create table people(
    id serial primary key,
    name varchar(255)
);

create table devices_people(
    id serial primary key,
    device_id int references devices(id),
    people_id int references people(id)
);

insert into devices(name, price) values('HTC', '15000.95');
insert into devices(name, price) values('Samsung', '25000.05');
insert into devices(name, price) values('Apple', '30000.99');
insert into devices(name, price) values('Motorola', '5000');

insert into people(name) values('Семен');
insert into people(name) values('Аркадий');
insert into people(name) values('Василий');
insert into people(name) values('Иван');
insert into people(name) values('Денис');
insert into people(name) values('Инокентий');

insert into devices_people(people_id, device_id) values(1, 1);
insert into devices_people(people_id, device_id) values(1, 2);
insert into devices_people(people_id, device_id) values(1, 3);

insert into devices_people(people_id, device_id) values(2, 3);
insert into devices_people(people_id, device_id) values(2, 3);
insert into devices_people(people_id, device_id) values(2, 1);

insert into devices_people(people_id, device_id) values(3, 1);
insert into devices_people(people_id, device_id) values(3, 4);

insert into devices_people(people_id, device_id) values(4, 2);

insert into devices_people(people_id, device_id) values(5, 2);
insert into devices_people(people_id, device_id) values(5, 4);

select avg(price) from devices;

select p.name, avg(d.price)
from devices d 
join devices_people dp
on dp.device_id = d.id 
join people p 
on dp.people_id = p.id
group by p.name;
having avg(d.price) > 15000;
