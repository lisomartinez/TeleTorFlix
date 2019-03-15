insert into public.days_of_week(day)
values ('Monday');
insert into public.days_of_week(day)
values ('Tuesday');
insert into public.days_of_week(day)
values ('Wednesday');
insert into public.days_of_week(day)
values ('Thursday');
insert into public.days_of_week(day)
values ('Friday');
insert into public.days_of_week(day)
values ('Saturday');
insert into public.days_of_week(day)
values ('Sunday');

insert into public.users(username, password, registration_date, active, email)
values ('John', '$2a$10$npMmnubC.ftf1fntKGBJ.eXkxCreV4Y5RFyfqKmgdGO9XOUb9kWOi', '2019-03-12T20:09:11.429834', TRUE,
        'JohnDow@gmail.com');

insert into public.users_roles(user_id, role)
VALUES (1, 'ADMIN');