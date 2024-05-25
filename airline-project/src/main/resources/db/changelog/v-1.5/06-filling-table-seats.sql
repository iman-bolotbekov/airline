-- changeset aleksandr_netesov:1.5.6
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1A', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1B', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1C', false, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1D', false, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1A', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1B', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1C', false, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1D', false, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 4));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1E', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 5));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1F', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 5));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2A', true, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 5));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2B', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 5));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2C', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 5));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2A', true, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2B', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2C', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2D', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2E', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2F', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('11A', false, false, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('21F', false, false, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 6));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1A', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1B', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1C', false, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1D', false, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1A', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1B', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1C', false, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1D', false, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 7));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1E', false, true, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 8));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('1F', false, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 8));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2A', true, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 8));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2B', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 8));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2C', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 8));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2A', true, true, (SELECT category.id FROM category WHERE category.id = 2),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2B', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2C', true, true, (SELECT category.id FROM category WHERE category.id = 3),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2D', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2E', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('2F', true, true, (SELECT category.id FROM category WHERE category.id = 4),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 9));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('11A', false, false, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 10));
INSERT INTO seats (seat_number, is_near_emergency_exit, is_locked_back, category_id, aircraft_id)
VALUES ('21F', false, false, (SELECT category.id FROM category WHERE category.id = 1),
        (SELECT aircrafts.id FROM aircrafts WHERE aircrafts.id = 10));