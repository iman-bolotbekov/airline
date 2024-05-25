CREATE TABLE depart_flights
(
    search_result_id BIGINT NOT NULL,
    flight_id        BIGINT NOT NULL
);

ALTER TABLE depart_flights
    ADD CONSTRAINT "FKackk24u1x4elaj1mr804g32tr" FOREIGN KEY (search_result_id) REFERENCES search_results (id);

ALTER TABLE depart_flights
    ADD CONSTRAINT "FKp0r5apxm8wmxbckeh5pktsxqn" FOREIGN KEY (flight_id) REFERENCES flights (id);