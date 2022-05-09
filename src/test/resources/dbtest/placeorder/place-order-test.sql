insert into customer (id, first_name, last_name, phone_number, address, email_id, password) values ('edb9b593-757e-4bf1-82a2-6d73495f1020' ,'Parth' , 'Deshmukh' , '9999999909', 'India', 'abc@1234.com', 'abc123' );

insert into cart_item (id, quantity, customer_id, image, name, price) values (uuid_generate_v4() ,5 , 'edb9b593-757e-4bf1-82a2-6d73495f1020' , 'image', 'Mug', 10.00);
