create table body(
	id serial primary key,
	body_type varchar(15)
);

create table engine(
	id serial primary key,
	volume_engine float
);

create table transmission(
	id serial primary key,
	num_of_speed int
);

create table car(
	id serial primary key,
	name varchar(50),
	price int,
	body_id int references body(id),
	engine_id int references engine(id),
	transmission_id int references transmission(id)
);

insert into body(body_type) values('Сталь');
insert into body(body_type) values('Алюминий');
insert into body(body_type) values('Карбон');
insert into body(body_type) values('Пластик');

insert into engine(volume_engine) values('1.5');
insert into engine(volume_engine) values('2.0');
insert into engine(volume_engine) values('2.3');
insert into engine(volume_engine) values('3.0');

insert into transmission(num_of_speed) values('5');
insert into transmission(num_of_speed) values('6');
insert into transmission(num_of_speed) values('8');

insert into car(name, price, body_id, engine_id, transmission_id) values('Skoda', '700000', 1, 1, 1);
insert into car(name, price, body_id, engine_id, transmission_id) values('Audi', '1200000', 2, 3, 3);
insert into car(name, price, body_id, engine_id, transmission_id) values('ferrari', '5900000', 4, 4, 3);
insert into car(name, price, body_id, engine_id, transmission_id) values('Lada', '700000', 1, 1, 2);
insert into car(name, price, body_id, engine_id, transmission_id) values('F1', '700000', 3, 4, 3);

/* All car's with all parts that cars used */
select c.name, c.price, b.body_type, e.volume_engine, t.num_of_speed from car c
join body b on c.body_id = b.id
join engine e on c.engine_id = e.id
join transmission t on c.transmission_id = t.id

/* One car and one type of part that not used */
select c.name, b.body_type from car c left join body b on c.body_id != b.id where c.name = 'Skoda';
select c.name, e.volume_engine from car c left join engine e on c.engine_id != e.id where c.name = 'Skoda';
select c.name, t.num_of_speed from car c left join transmission t on c.transmission_id != t.id where c.name = 'Skoda';

/* All cars and one type of parts that not used */
select c.name, b.body_type as "Parts that not used" from car c
join body b on c.body_id != b.id
order by c.name;

select c.name, e.volume_engine as "Parts that not used" from car c
join engine e on c.engine_id != e.id
order by c.name;

select c.name, t.num_of_speed as "Parts that not used" from car c
join transmission t on c.transmission_id != t.id
order by c.name;