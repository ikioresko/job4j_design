create table role(
	id serial primary key,
	level varchar(200)
);

create table state(
	id serial primary key,
	is_open bool
);

create table category(
	id serial primary key,
	cat_name text
);

create table users(
	id serial primary key,
	name varchar(100),
	age int,
	role_id int references role(id)
);

create table rules(
	id serial primary key,
	admin bool
);

create table role_rules(
	id serial primary key,
	role_id int references rules(id),
	rules_id int references role(id)
);

create table item(
	id serial primary key,
	number int,
	created_date timestamp,
	body text,
	comments_id int,
	user_id int references users(id),
	category_id int references category(id),
	state_id int references state(id)
);

create table comments(
	id serial primary key,
	created_date timestamp,
	body text,
	item_id int references item(id)
);

create table attachs(
	id serial primary key,
	link text,
	item_id int references item(id)
);