ALTER TABLE payments ADD currency varchar(10);
ALTER TABLE payments ADD price numeric;

insert into payments (price, currency, payment_state) values (100, 'USD','CREATED')