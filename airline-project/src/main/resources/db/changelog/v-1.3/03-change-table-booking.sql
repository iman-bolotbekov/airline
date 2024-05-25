-- changeset alekseenko_nikita:1.3.3.1
ALTER TABLE booking ALTER COLUMN booking_data_time SET NOT NULL;
ALTER TABLE booking ALTER COLUMN flight_id SET NOT NULL;
ALTER TABLE booking ALTER COLUMN category_id SET NOT NULL;
ALTER TABLE booking ALTER COLUMN passenger_id SET NOT NULL;