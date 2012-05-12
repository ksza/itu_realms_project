
insert into authorities(username,authority) values ('admin','ROLE_USER');
insert into authorities(username,authority) values ('admin','ROLE_ADMIN');

insert into authorities(username,authority) values ('ksza@itu.dk','ROLE_USER');

insert into authorities(username,authority) values ('ampi@itu.dk','ROLE_USER');

insert into users(username, password, name, enabled) values ('ksza@itu.dk', 'ksza', 'Karoly Szanto', true);
insert into users(username, password, name, enabled) values ('mortenq@itu.dk', 'mortenq', 'Morten Esbensen', true);

insert into phoneusers(username, password, fullname) value ('morten', 'morten', 'morten');

insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm1', 'First realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm2', 'Second realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm3', 'Third realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm4', '4th realm', 'near Copenhagen', 55.676097,12.568337, 1000);
insert into realm(name, description, location_description, latitude, longitude, radius) values ('realm5', 'Fifth realm', 'near Copenhagen', 55.676097,12.568337, 1000);

insert into users_realms(user_id, realm_id) values (1, 1);
insert into users_realms(user_id, realm_id) values (1, 3);
insert into users_realms(user_id, realm_id) values (1, 5);

commit;