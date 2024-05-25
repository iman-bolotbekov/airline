-- liquibase formatted sql

-- changeset alex_evdokimov:1.0.2
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');
INSERT INTO roles (name)
VALUES ('ROLE_PASSENGER');
INSERT INTO roles (name)
VALUES ('ROLE_MANAGER');

-- changeset alex_evdokimov:1.0.3.1
INSERT INTO admin (email, password, id)
VALUES ('admin@mail.ru', '$2a$10$gH.ED3q0X4RZz2Pcm9/IU.Cz88JM7t6A6Nw9Y5.e8gRcl8GZHz20y', 1);

-- changeset alex_evdokimov:1.0.3.2
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT admin.id FROM admin WHERE admin.email = 'admin@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_ADMIN'));

-- changeset alex_evdokimov:1.0.4.1
INSERT INTO passengers (first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                        serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('John', 'Simons', 'J', TO_DATE('2003/11/09', 'YYYY/MM/DD'), 'MALE', 'passenger@mail.ru', '79111111111',
        '$2a$10$NWSGtFxjwv4.vXvmJ4XYb.Iw9YE09bncik1FxBB02WLBjq9Ky3cX.', '0000 000000',
        TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'Россия', 3);

-- changeset alex_evdokimov:1.0.4.2
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'passenger@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));

-- changeset alex_evdokimov:1.0.5.1
INSERT INTO airline_manager (email, password, id)
VALUES ('manager@mail.ru', '$2a$10$tvW02anRGLpXpLYZTx8UAeaUh3KwVukLsPWIckGhdcL41TwdYss1W', 2);

-- changeset alex_evdokimov:1.0.5.2
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT airline_manager.id FROM airline_manager WHERE airline_manager.email = 'manager@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_MANAGER'));

--changeset alex_evdokimov:1.0.6
INSERT INTO destination (airport_code, airport_name, city_name, country_name, timezone)
VALUES ('VKO', 'Внуково', 'Москва', 'GMT +3', 'Россия');
INSERT INTO destination (airport_code, airport_name, city_name, country_name, timezone)
VALUES ('VOG', 'Гумрак', 'Волгоград', 'GMT +3', 'Россия');
INSERT INTO destination (airport_code, airport_name, city_name, country_name, timezone)
VALUES ('MQF', 'Магнитогорск', 'Магнитогорск', 'GMT +5', 'Россия');
INSERT INTO destination (airport_code, airport_name, city_name, country_name, timezone)
VALUES ('OMS', 'Омск', 'Омск', 'GMT +6', 'Россия');


-- changeset alex_evdokimov:1.0.7
INSERT INTO category (category_type)
VALUES ('FIRST');
INSERT INTO category (category_type)
VALUES ('BUSINESS');
INSERT INTO category (category_type)
VALUES ('PREMIUM_ECONOMY');
INSERT INTO category (category_type)
VALUES ('ECONOMY');

-- changeset alex_evdokimov:1.0.8
INSERT INTO aircrafts (aircraft_number, model, model_year, flight_range)
VALUES ('17000012', 'Embraer E170STD', 2002, 3800);
INSERT INTO aircrafts (aircraft_number, model, model_year, flight_range)
VALUES ('5134', 'Airbus A320-200', 2011, 4300);
INSERT INTO aircrafts (aircraft_number, model, model_year, flight_range)
VALUES ('35283', 'Boeing 737-800', 2008, 5765);


-- changeset alex_evdokimov:1.0.9
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1A', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1B', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1C', false, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1D', false, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1E', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1F', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2A', true, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2B', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2C', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2D', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 3));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2E', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 3));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2F', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 3));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('11A', false, false, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 3));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('21F', false, false, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 3));


-- changeset alex_evdokimov:1.0.10
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('MSKOMSK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ON_TIME',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('MSKVLG', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DELAYED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград'));

-- changeset alex_evdokimov:1.0.11.1
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Пётр', 'Петров', 'Петрович', TO_DATE('1986/01/11', 'YYYY/MM/DD'), 'MALE', 'petrov@mail.ru', '79111111111',
        '$2a$10$T6BsLlx63setzCLXgftVHO4gZTWtPdS3LgBAcIv37OsxvFRuo.dqG', '1111 111111',
        TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'Россия', 4);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Иван', 'Иванов', 'Иванович', TO_DATE('1986/02/22', 'YYYY/MM/DD'), 'MALE', 'ivanov@mail.ru', '79222222222',
        '$2a$10$Im4zfF/3IweHa8so7YIpOu3KjTiEmHPO7V51RidAq5gNs6.4b.FfK', '2222 222222',
        TO_DATE('2006/02/22', 'YYYY/MM/DD'), 'Россия', 5);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number, password,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Екатерина', 'Сидоровна', 'Сидорова', TO_DATE('1986/03/30', 'YYYY/MM/DD'), 'FEMALE', 'sidorova@mail.ru',
        '79333333333',
        '$2a$10$xxeZv2D4GRgY2Cswq9o7VeilkMArCM3e.kMAW8ukPSwkoLKooVaIe', '3333 333333',
        TO_DATE('2006/03/30', 'YYYY/MM/DD'), 'Россия', 6);

-- changeset alex_evdokimov:1.0.11.2
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'petrov@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'ivanov@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));
INSERT INTO user_roles(users_id, roles_id)
VALUES ((SELECT passengers.id FROM passengers WHERE passengers.email = 'sidorova@mail.ru'),
        (SELECT roles.id FROM roles WHERE roles.name = 'ROLE_PASSENGER'));

-- changeset alex_evdokimov:1.0.12
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);

-- changeset alex_evdokimov:1.0.13
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (500, true, false, (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT seats.id FROM seats WHERE seats.id = 1));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (600, true, true, (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT seats.id FROM seats WHERE seats.id = 2));
INSERT INTO flight_seats (fare, is_registered, is_sold, flight_id, seat_id)
VALUES (650, true, true, (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT seats.id FROM seats WHERE seats.id = 3));

-- changeset alex_evdokimov:1.0.14
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('SD-2222', (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 4),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 2));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('ZX-3333', (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 5),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 3));