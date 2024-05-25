insert into aircrafts(id, aircraft_number, model, model_year, flight_range) values
(1, '17000012', 'Embraer E170STD', 2002, 3800),
(2, '5134', 'Airbus A320-200', 2011, 4300);

insert into category(id, category_type) values
(1, 'BUSINESS'),
(2, 'ECONOMY');

insert into seats(id, seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id) values
(1,'2A', FALSE, TRUE, 1, 1),
(2, '3D', TRUE, FALSE, 2, 2);