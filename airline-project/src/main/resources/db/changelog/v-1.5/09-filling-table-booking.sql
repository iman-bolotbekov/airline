-- changeset aleksandr_netesov:1.5.9
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00001', TO_DATE('2022/11/24', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 4),
        (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT category.id FROM category WHERE category.id = 2));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00002', TO_DATE('2022/11/25', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 5),
        (SELECT flights.id FROM flights WHERE flights.id = 2),
        (SELECT category.id FROM category WHERE category.id = 3));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00003', TO_DATE('2022/11/25', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 6),
        (SELECT flights.id FROM flights WHERE flights.id = 3),
        (SELECT category.id FROM category WHERE category.id = 2));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00004', TO_DATE('2022/11/25', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 7),
        (SELECT flights.id FROM flights WHERE flights.id = 4),
        (SELECT category.id FROM category WHERE category.id = 3));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00005', TO_DATE('2022/11/26', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 8),
        (SELECT flights.id FROM flights WHERE flights.id = 5),
        (SELECT category.id FROM category WHERE category.id = 2));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00006', TO_DATE('2022/11/27', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 9),
        (SELECT flights.id FROM flights WHERE flights.id = 6),
        (SELECT category.id FROM category WHERE category.id = 1));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00007', TO_DATE('2022/11/27', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 4),
        (SELECT flights.id FROM flights WHERE flights.id = 7),
        (SELECT category.id FROM category WHERE category.id = 2));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, category_id)
VALUES ('BK-00008', TO_DATE('2022/11/28', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 10),
        (SELECT flights.id FROM flights WHERE flights.id = 8),
        (SELECT category.id FROM category WHERE category.id = 4));