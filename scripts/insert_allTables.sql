insert into role(level) values('Ученик');
insert into state(is_open) values('true');
insert into category(cat_name) values('Экзамен');
insert into users(name, age, role_id) values('Егор', '25', 1);
insert into rules(admin) values('false');
insert into role_rules(role_id, rules_id) values(1, 1);
insert into item(
	number, created_date, body, comments_id, user_id, category_id, state_id) 
	values('1', '2020-01-19 10:23:54', 'Здравствуйте', 1, 1, 1, 1);
insert into comments(created_date, body, item_id) values('2020-01-20 19:00:01', 'Добрый вечер', 1);
insert into attachs(link, item_id) values('www.google.com', 1);