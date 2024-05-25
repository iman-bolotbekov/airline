-- changeset aleksandr_netesov:1.5.8
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('AA-1111', (SELECT flights.id FROM flights WHERE flights.id = 1),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 4),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 2));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('AX-1112', (SELECT flights.id FROM flights WHERE flights.id = 2),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 5),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 3));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('AD-1113', (SELECT flights.id FROM flights WHERE flights.id = 3),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 6),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 2));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('BB-1111', (SELECT flights.id FROM flights WHERE flights.id = 4),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 7),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 3));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('BA-1111', (SELECT flights.id FROM flights WHERE flights.id = 5),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 8),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 2));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('CC-1111', (SELECT flights.id FROM flights WHERE flights.id = 6),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 9),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 3));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('CA-1112', (SELECT flights.id FROM flights WHERE flights.id = 7),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 10),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 2));
INSERT INTO tickets (ticket_number, flight_id, passenger_id, flight_seat_id)
VALUES ('DA-1113', (SELECT flights.id FROM flights WHERE flights.id = 8),
        (SELECT passengers.id FROM passengers WHERE passengers.id = 5),
        (SELECT flight_seats.id FROM flight_seats WHERE flight_seats.id = 3));