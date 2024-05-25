-- changeset alekseenko_nikita:1.3.1.1
ALTER TABLE booking DROP COLUMN flight_seat_id;

-- changeset alekseenko_nikita:1.3.1.2
ALTER TABLE booking ADD COLUMN category_id BIGINT REFERENCES category (id);

-- changeset alekseenko_nikita:1.3.1.3
UPDATE booking SET category_id = 2 WHERE id = 1;
UPDATE booking SET category_id = 3 WHERE id = 2;