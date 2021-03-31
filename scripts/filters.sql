create table type(
 	id serial primary key,
	name varchar(50)
);

create table product(
 	id serial primary key,
	name varchar(50), 
	expired_date date,
	price float,
	type_id int references type(id)
);

insert into type(name) values('Сыр');
insert into type(name) values('Молоко');
insert into type(name) values('Мороженое');

insert into product(name, expired_date, price, type_id) values('Сыр Пармезан', '05.04.2021', '250.49', 1);
insert into product(name, expired_date, price, type_id) values('Сыр Пармезан', '25.04.2021', '250.49', 1);
insert into product(name, expired_date, price, type_id) values('Сыр Пармезан', '15.04.2021', '250.49', 1);
insert into product(name, expired_date, price, type_id) values('Сыр Адыгейский', '15.04.2021', '229.99', 1);
insert into product(name, expired_date, price, type_id) values('Сыр Голландский', '01.05.2021', '190.4', 1);
insert into product(name, expired_date, price, type_id) values('Сыр Голландский', '01.05.2021', '190.4', 1);

insert into product(name, expired_date, price, type_id) values('Молоко Простоквашино', '14.04.2021', '40.29', 2);
insert into product(name, expired_date, price, type_id) values('Молоко Домик в деревне', '23.04.2021', '50.39', 2);
insert into product(name, expired_date, price, type_id) values('Молоко Данон', '09.08.2021', '70.34', 2);

insert into product(name, expired_date, price, type_id) values('Мороженое Пломбир', '21.07.2021', '50', 3);
insert into product(name, expired_date, price, type_id) values('Мороженое Весовое', '30.06.2021', '530', 3);
insert into product(name, expired_date, price, type_id) values('Мороженое Рожок', '09.05.2021', '170', 3);

select * from product p join type t on t.id = p.type_id where t.name = 'Сыр';
select * from product where name like '%Мороженое%';
select * from product where date_part('month', expired_date) = date_part('month', current_date) + 1;
select * from product where price = (select max(price) from product);

select t.name, count(t.id) from type t
join product p on t.id = p.type_id
group by t.name;

select * from product p
join type t on t.id = p.type_id
where t.name in ('Сыр', 'Молоко');

select t.name, count(t.id) from type t
join product p on t.id = p.type_id
group by t.name
having count(t.id) < 10;

select * from product p join type t on p.type_id = t.id