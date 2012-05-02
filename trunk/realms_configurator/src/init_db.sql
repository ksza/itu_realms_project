insert into profile_thumbs(pic) values (LOAD_FILE('default_user_icon.png'));

insert into authorities(username,authority) values ('admin','ROLE_USER');
insert into authorities(username,authority) values ('admin','ROLE_ADMIN');

insert into authorities(username,authority) values ('ksza@itu.dk','ROLE_USER');

insert into authorities(username,authority) values ('ampi@itu.dk','ROLE_USER');

insert into users(username, password, enabled, name, address, zip, phone, thumb_id) values ('ksza@itu.dk', 'ksza', true, 'Karoly Szanto', 'user address', '1234', '12345678', 1);
insert into users(username, password, enabled, name, address, zip, phone, thumb_id) values ('ampi@itu.dk', 'ampi', true, 'Amin Piramoon', 'user address', '1234', '12345678', 1);
insert into users(username, password, enabled, name, address, zip, phone, thumb_id) values ('admin', 'admin', true, 'Administrator', 'admin address', '1234', '12345678', 1);

insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm1', 'First realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm2', 'Second realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm3', 'Third realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm4', '4th realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm5', 'Fifth realm', 'near Copenhagen', 55.676097,12.568337, 1000);

insert into users_realms(user_id, realm_id) values (1, 1);
insert into users_realms(user_id, realm_id) values (1, 3);
insert into users_realms(user_id, realm_id) values (1, 5);

commit;