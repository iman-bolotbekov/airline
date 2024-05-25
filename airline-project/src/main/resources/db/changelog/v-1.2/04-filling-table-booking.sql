-- changeset aleksandr_pekarskiy:1.2.4.1
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, flight_seat_id)
VALUES ('SV-221122', TO_DATE('2022/11/22', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 4),
        (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 2));
INSERT INTO booking (booking_number, booking_data_time, passenger_id, flight_id, flight_seat_id)
VALUES ('CK-231122', TO_DATE('2022/11/23', 'YYYY/MM/DD'),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 5),
        (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 3));