-- changeset aleksandr_netesov:1.5.5
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Калининград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 3);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Краснодар'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 3);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Воронеж'),
        (SELECT destination.id FROM destination WHERE city_name = 'Архангельск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Анапа'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Магнитогорск'),
        (SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Воронеж'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 3);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        (SELECT destination.id FROM destination WHERE city_name = 'Краснодар'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Анапа'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Калининград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Омск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 3);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'),
        (SELECT destination.id FROM destination WHERE city_name = 'Магнитогорск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Архангельск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Краснодар'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Липецк'),
        (SELECT destination.id FROM destination WHERE city_name = 'Магнитогорск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 3);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Казань'),
        (SELECT destination.id FROM destination WHERE city_name = 'Архангельск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Нальчик'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Грозный'),
        (SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 3);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Екатеринбург'),
        (SELECT destination.id FROM destination WHERE city_name = 'Казань'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Волгоград'),
        (SELECT destination.id FROM destination WHERE city_name = 'Ижевск'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 1);
INSERT INTO route (destination_from_id, destination_to_id, departure_date, number_of_passengers)
VALUES ((SELECT destination.id FROM destination WHERE city_name = 'Самара'),
        (SELECT destination.id FROM destination WHERE city_name = 'Москва'),
        TO_DATE('2022/11/30', 'YYYY/MM/DD'), 2);