-- changeset alekseenko_nikita:1.4.1.1
ALTER TABLE passengers ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE passengers ALTER COLUMN last_name SET NOT NULL;
ALTER TABLE passengers ALTER COLUMN birth_date SET NOT NULL;
ALTER TABLE passengers ALTER COLUMN phone_number SET NOT NULL;

-- changeset alekseenko_nikita:1.4.1.2
ALTER TABLE passengers ADD COLUMN security_question VARCHAR(255);
ALTER TABLE passengers ADD COLUMN answer_question VARCHAR(255);
ALTER TABLE admin ADD COLUMN security_question VARCHAR(255);
ALTER TABLE admin ADD COLUMN answer_question VARCHAR(255);
ALTER TABLE airline_manager ADD COLUMN security_question VARCHAR(255);
ALTER TABLE airline_manager ADD COLUMN answer_question VARCHAR(255);