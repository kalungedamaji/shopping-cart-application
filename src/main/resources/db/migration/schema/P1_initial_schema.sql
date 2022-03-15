create table product(
    id UUID PRIMARY KEY,
    image VARCHAR(100),
    name VARCHAR(100) NOT NULL,
    description text,
    price NUMERIC(10,2) CHECK(price>0) NOT NULL
);
CREATE DOMAIN EMAIL AS citext CHECK ( value ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$' );
create table customer(
    id UUID PRIMARY KEY,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(300) NOT NULL,
    email_id EMAIL NOT NULL,
    password VARCHAR(50) NOT NULL,
    UNIQUE(phone_number),
    UNIQUE(email_id)
);
create table cartItem (
    id UUID PRIMARY KEY,
    quantity SMALLINT NOT NULL DEFAULT 1,
    customerID UUID REFERENCES customer(id),
    productID UUID REFERENCES product(id),
    status boolean DEFAULT TRUE
);
CREATE TYPE PAYMENT_TYPE AS ENUM ('UPI', 'NET_BANKING', 'COD' , 'DEBIT_CARD');
CREATE TYPE PAYMENT_STATUS AS ENUM ('REJECTED', 'IN_PROGRESS', 'Completed' , 'Cancelled');
create table orders (
    id UUID PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    order_payment_type PAYMENT_TYPE NOT NULL,
    order_payment_status PAYMENT_STATUS DEFAULT 'IN_PROGRESS',
    customer_id UUID REFERENCES customer(id)
);
create table order_item(
    id UUID PRIMARY KEY,
    image VARCHAR(100),
    name VARCHAR(100) NOT NULL,
    description text,
    price NUMERIC(10,2) check(price>0) NOT NULL,
    qty SMALLINT check(qty>0) NOT NULL,
    order_id UUID REFERENCES orders(id)
);