insert into profile_thumbs(pic) values (LOAD_FILE('default_user_icon.png'));

insert into authorities(username,authority) values ('admin','ROLE_USER');
insert into authorities(username,authority) values ('admin','ROLE_ADMIN');

insert into authorities(username,authority) values ('ksza@itu.dk','ROLE_USER');

insert into authorities(username,authority) values ('ampi@itu.dk','ROLE_USER');

insert into users(username, password, enabled, name, address, zip, phone, thumb_id) values ('ksza@itu.dk', 'ksza', true, 'Karoly Szanto', 'user address', '1234', '12345678', 1);
insert into users(username, password, enabled, name, address, zip, phone, thumb_id) values ('ampi@itu.dk', 'ampi', true, 'Amin Piramoon', 'user address', '1234', '12345678', 1);
insert into users(username, password, enabled, name, address, zip, phone, thumb_id) values ('admin', 'admin', true, 'Administrator', 'admin address', '1234', '12345678', 1);

commit;