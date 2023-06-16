insert into users (email, name)
values ('user@email.ru', 'name'), ('user1@email.ru', 'name1'), ('user2@email.ru', 'name2');

insert into items (name, description, available, owner_id)
values ('item1', 'description1', true, 1), ('item2', 'description2', false, 1),
       ('item3', 'description3', true, 1), ('item4', 'description4', true, 1),
       ('item5', 'description5', true, 1);

insert into bookings (start_date, end_date, item_id, booker_id, status)
values ('2023-01-17 23:15:42', '2020-03-17 23:15:43', 1, 2, 'APPROVED');