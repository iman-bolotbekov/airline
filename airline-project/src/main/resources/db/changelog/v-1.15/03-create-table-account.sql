-- changeset olga_alikulieva:1.15.3

CREATE TABLE account
(
    account_id            BIGSERIAL PRIMARY KEY,
    first_name               VARCHAR(128)    NOT NULL,
    last_name                VARCHAR(128)    NOT NULL,
    birth_date               date            NOT NULL,
    phone_number             VARCHAR(64)     NOT NULL,
    email                     VARCHAR(255)   NOT NULL,
    password                  VARCHAR(255)   NOT NULL,
    security_question         VARCHAR(255)   NOT NULL,
    answer_question           VARCHAR(255)   NOT NULL
);
CREATE TABLE roles
(
    role_id           BIGSERIAL PRIMARY KEY,
    role_name               VARCHAR(255)    NOT NULL
);

CREATE TABLE account_roles
(
    account_id  BIGINT REFERENCES account (account_id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id  BIGINT REFERENCES roles (role_id) ON UPDATE CASCADE
);
