-- changeset aleksandr_netesov:1.5.3
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('VOZ-VKO', '2022-11-30 07:40:00', '2022-11-30 09:05:00', 'DELAYED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2),
        (SELECT destination.id FROM destination WHERE city_name = 'Воронеж'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('VKO-KRR', '2022-11-30 06:30:00', '2022-11-30 08:25:00', 'DEPARTED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 3),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Краснодар'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('AAK-OMS', '2022-11-30 11:25:00', '2022-11-30 15:30:00', 'CANCELED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6),
        (SELECT destination.id FROM destination WHERE city_name = 'Анапа'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('KGD-OMS', '2022-11-30 04:15:00', '2022-11-30 10:20:00', 'COMPLETED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 8),
        (SELECT destination.id FROM destination WHERE city_name = 'Калининград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('SVX-MQF', '2022-11-30 12:40:00', '2022-11-30 13:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'),
        (SELECT destination.id FROM destination WHERE city_name = 'Магнитогорск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('VOG-ARH', '2022-11-30 14:30:00', '2022-11-30 19:45:00', 'CANCELED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Архангельск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('KRR-VKO', '2022-11-30 17:30:00', '2022-11-30 19:25:00', 'COMPLETED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2),
        (SELECT destination.id FROM destination WHERE city_name = 'Краснодар'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('VKO-SVX', '2022-11-23 02:30:00', '2022-11-30 04:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 10),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('LPK-MQF', '2022-11-30 12:40:00', '2022-11-30 13:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1),
        (SELECT destination.id FROM destination WHERE city_name = 'Липецк'),
        (SELECT destination.id FROM destination WHERE city_name = 'Магнитогорск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('KZN-ARH', '2022-11-30 14:30:00', '2022-11-30 19:45:00', 'CANCELED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9),
        (SELECT destination.id FROM destination WHERE city_name = 'Казань'),
        (SELECT destination.id FROM destination WHERE city_name = 'Архангельск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('NAL-VKO', '2022-11-30 17:30:00', '2022-11-30 19:25:00', 'COMPLETED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2),
        (SELECT destination.id FROM destination WHERE city_name = 'Нальчик'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('GRV-SVX', '2022-11-16 02:30:00', '2022-11-30 04:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 10),
        (SELECT destination.id FROM destination WHERE city_name = 'Грозный'),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('SVX-KZN', '2022-11-30 12:40:00', '2022-11-30 13:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 1),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'),
        (SELECT destination.id FROM destination WHERE city_name = 'Казань'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('VOG-IJK', '2022-11-30 14:30:00', '2022-11-30 19:45:00', 'CANCELED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Ижевск'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('KUF-VKO', '2022-11-30 17:30:00', '2022-11-30 19:25:00', 'COMPLETED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2),
        (SELECT destination.id FROM destination WHERE city_name = 'Самара'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('KGD-SVX', '2022-12-07 02:30:00', '2022-12-07 04:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 10),
        (SELECT destination.id FROM destination WHERE city_name = 'Калининград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('KGD-VKO', '2022-11-30 17:30:00', '2022-11-30 19:25:00', 'COMPLETED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 2),
        (SELECT destination.id FROM destination WHERE city_name = 'Краснодар'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'));
INSERT INTO flights (code, arrival_date, departure_date, flight_status, aircraft_id, from_id, to_id)
VALUES ('NAL-SVX', '2022-12-14 02:30:00', '2022-12-14 04:50:00', 'ARRIVED',
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 10),
        (SELECT destination.id FROM destination WHERE city_name = 'Нальчик'),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'));