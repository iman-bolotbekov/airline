CREATE TABLE return_flights
(
    search_result_id BIGINT NOT NULL,
    flight_id     BIGINT NOT NULL
);

ALTER TABLE return_flights
    ADD CONSTRAINT "FKaldyovf1312e0hkof32f22b0o" FOREIGN KEY (flight_id) REFERENCES flights (id);

ALTER TABLE return_flights
    ADD CONSTRAINT "FKn073qn76elyf8c1fj6k96d4lm" FOREIGN KEY (search_result_id) REFERENCES search_results (id);