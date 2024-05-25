-- changeset olga_alikulieva:1.15.2
CREATE TABLE passengers
(
    id           BIGSERIAL PRIMARY KEY,
    email                    VARCHAR(255)    NOT NULL,
    first_name               VARCHAR(128)    NOT NULL,
    last_name                VARCHAR(128)    NOT NULL,
    middle_name              VARCHAR(128)    NOT NULL,
    birth_date               date            NOT NULL,
    gender                   VARCHAR(255)    NOT NULL,
    phone_number             VARCHAR(64)     NOT NULL,
    serial_number_passport   VARCHAR(255)    NOT NULL,
    passport_issuing_country VARCHAR(255)    NOT NULL,
    passport_issuing_date    date            NOT NULL
);
