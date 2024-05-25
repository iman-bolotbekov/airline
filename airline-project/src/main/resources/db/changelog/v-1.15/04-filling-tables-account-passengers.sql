INSERT INTO account (first_name, last_name, birth_date, phone_number, email, password, security_question, answer_question, account_id)
VALUES ('Admin4ik', 'Adminov', TO_DATE('1999/09/09', 'YYYY/MM/DD'), '79777777777',  'admin@mail.ru',
        '$2a$10$gH.ED3q0X4RZz2Pcm9/IU.Cz88JM7t6A6Nw9Y5.e8gRcl8GZHz20y', 'вопрос 1', 'ответ 1', 1);

INSERT INTO account (first_name, last_name, birth_date, phone_number, email, password, security_question, answer_question, account_id)
VALUES ('Манагер', 'Манагеров', TO_DATE('2000/02/02', 'YYYY/MM/DD'), '79666666666', 'manager@mail.ru',
        '$2a$10$tvW02anRGLpXpLYZTx8UAeaUh3KwVukLsPWIckGhdcL41TwdYss1W', 'вопрос 2', 'ответ 2', 2);

INSERT INTO roles (role_name, role_id)
VALUES ('ROLE_ADMIN', 1);
INSERT INTO roles (role_name, role_id)
VALUES ('ROLE_MANAGER', 2);


INSERT INTO account_roles(account_id, role_id)
VALUES ((SELECT account.account_id FROM account WHERE account.email = 'admin@mail.ru'),
        (SELECT roles.role_id FROM roles WHERE roles.role_name = 'ROLE_ADMIN'));

INSERT INTO account_roles(account_id, role_id)
VALUES ((SELECT account.account_id FROM account WHERE account.email = 'manager@mail.ru'),
        (SELECT roles.role_id FROM roles WHERE roles.role_name = 'ROLE_MANAGER'));


INSERT INTO passengers (first_name, last_name, middle_name, birth_date, gender, email, phone_number,
                        serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('John', 'Simons', 'J', TO_DATE('2003/11/09', 'YYYY/MM/DD'), 'MALE', 'passenger@mail.ru', '79111111111',
     '0000 000000', TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'Россия', 3);

INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Пётр', 'Петров', 'Петрович', TO_DATE('1986/01/11', 'YYYY/MM/DD'), 'MALE', 'petrov@mail.ru', '79111111111',
         '1111 111111', TO_DATE('2006/01/11', 'YYYY/MM/DD'), 'Россия', 4);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Иван', 'Иванов', 'Иванович', TO_DATE('1986/02/22', 'YYYY/MM/DD'), 'MALE', 'ivanov@mail.ru', '79222222222',
         '2222 222222', TO_DATE('2006/02/22', 'YYYY/MM/DD'), 'Россия', 5);
INSERT INTO passengers(first_name, last_name, middle_name, birth_date, gender, email, phone_number,
                       serial_number_passport, passport_issuing_date, passport_issuing_country, id)
VALUES ('Екатерина', 'Сидоровна', 'Сидорова', TO_DATE('1986/03/30', 'YYYY/MM/DD'), 'FEMALE', 'sidorova@mail.ru',
        '79333333333', '3333 333333', TO_DATE('2006/03/30', 'YYYY/MM/DD'), 'Россия', 6);
