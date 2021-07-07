create table meet(
id serial primary key,
name varchar(25)
);

create table users(
id serial primary key,
name varchar(25)
);

create table status(
id serial primary key,
name varchar(25)
);

create table schedule(
id serial primary key,
schedule_meet int references meet(id),
schedule_user int references users(id),
schedule_status int references status(id)
);

insert into meet (name) values('work');
insert into meet (name) values('game');
insert into meet (name) values('lesson');

insert into users (name) values('Semen');
insert into users (name) values('Ivan');
insert into users (name) values('Vasiliy');
insert into users (name) values('Artem');

insert into status (name) values('accept');
insert into status (name) values('decline');

insert into schedule (schedule_meet, schedule_user, schedule_status) values(1, 1, 1);
insert into schedule (schedule_meet, schedule_user, schedule_status) values(1, 2, 1);
insert into schedule (schedule_meet, schedule_user, schedule_status) values(1, 3, 2);
insert into schedule (schedule_meet, schedule_user, schedule_status) values(1, 4, 1);

insert into schedule (schedule_meet, schedule_user, schedule_status) values(2, 1, 2);
insert into schedule (schedule_meet, schedule_user, schedule_status) values(2, 2, 2);
insert into schedule (schedule_meet, schedule_user, schedule_status) values(2, 3, 2);
insert into schedule (schedule_meet, schedule_user, schedule_status) values(2, 4, 2);

select m.name as meet_name, count(schedule_user) as num_of_members
from schedule sh
right join meet m on (sh.schedule_meet = m.id and sh.schedule_status = 1)
group by m.name
order by num_of_members desc;

select m.name as meet_name
from schedule sh
right join meet m on (sh.schedule_meet = m.id and sh.schedule_status = 1)
group by m.name
having count(schedule_user) < 1;