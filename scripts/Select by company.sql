CREATE TABLE company(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values(1, 'IBM');
insert into company(id, name) values(2, 'Apple');
insert into company(id, name) values(3, 'Samsung');
insert into company(id, name) values(4, 'NASA');
insert into company(id, name) values(5, 'Facebook');


insert into person(id, name, company_id) values(1, 'Sam', 1);
insert into person(id, name, company_id) values(2, 'Tom', 1);
insert into person(id, name, company_id) values(3, 'Jane', 1);
insert into person(id, name, company_id) values(4, 'Tim', 1);
insert into person(id, name, company_id) values(5, 'Sam', 2);
insert into person(id, name, company_id) values(6, 'Ilon', 2);
insert into person(id, name, company_id) values(7, 'Jenny', 2);
insert into person(id, name, company_id) values(8, 'Peter', 3);
insert into person(id, name, company_id) values(9, 'Ron', 5);
insert into person(id, name, company_id) values(10, 'Danny', 5);

select p.name, c.name from person p
join company c on c.id = p.company_id
where p.company_id != 5;

select c.name, count(p) from person p
join company c on c.id = p.company_id
group by c.name
order by count(p) desc
fetch first row only;
