drop database if exists PlantMarket;
create database PlantMarket;
use PlantMarket;

create table roles(
role_id int auto_increment primary key,
role_name varchar(25) unique not null
);

create table users(
user_id char(36) primary key not null,
username varchar(50) not null unique,
password varchar(255) not null,
email varchar(100) not null unique,
full_name varchar(255),
avatar_url varchar(500),
phone varchar(15),
address varchar(255),
role_id int,
status boolean default 1 not null,
created_at datetime not null,
updated_at datetime not null,
foreign key (role_id) references roles(role_id)
);

create table categories(
category_id int auto_increment primary key,
category_name varchar(50) not null unique,
is_deleted boolean default 0,
description varchar(255)
);

create table products(
product_id int auto_increment primary key,
category_id int,
name varchar(50) not null,
description varchar(255),
price decimal(10,2) not null,
stock_quantity int not null check(stock_quantity >= 0),
image_url varchar(500) not null,
difficulty_level enum('easy','medium','hard') not null,
created_at datetime not null,
updated_at datetime not null,
is_deleted boolean default 0,
foreign key(category_id) references categories(category_id)
);

create table carts(
cart_id int auto_increment primary key,
user_id char(36) unique,
created_at datetime not null,
foreign key (user_id) references users(user_id)
);

create table cart_item(
cart_item_id int auto_increment primary key,
cart_id int,
product_id int,
quantity int not null,
foreign key (cart_id) references carts(cart_id),
foreign key (product_id) references products(product_id),
unique(cart_id,product_id)
);

create table orders(
order_id int auto_increment primary key,
user_id char(36),
total_amount decimal(10,2) not null,
status enum('PENDING','PAID','SHIPPED','DELIVERED','CANCELLED'),
shipping_address varchar(255) not null,
payment_method varchar(255) not null,
created_at datetime not null,
foreign key(user_id) references users(user_id)
);

create table order_details(
order_detail_id int auto_increment primary key,
order_id int,
product_id int,
quantity int not null,
price decimal(10,2) not null,
foreign key(order_id) references orders(order_id),
foreign key(product_id) references products(product_id),
unique(order_id,product_id)
);

create table posts(
post_id int auto_increment primary key,
user_id char(36),
content text not null,
is_deleted boolean default 0,
created_at datetime not null,
updated_at datetime not null,
foreign key (user_id) references users(user_id)
);

create table post_images(
image_id int auto_increment primary key,
post_id int,
image_url varchar(500) not null,
foreign key (post_id) references posts(post_id)
);

create table comments(
comment_id int auto_increment primary key,
post_id int,
user_id char(36),
parent_comment_id int null,
content text not null,
created_at datetime not null,
foreign key (post_id) references posts(post_id),
foreign key (user_id) references users(user_id)
);

create table notifications(
notification_id int auto_increment primary key,
user_id char(36),
content varchar(500) not null,
is_read boolean default 0,
created_at datetime not null,
foreign key (user_id) references users(user_id)
);

create table user_tokens(
token_id int auto_increment primary key,
user_id char(36),
refresh_token varchar(255) unique not null,
expires_at datetime not null,
is_revoked boolean default 0,
foreign key (user_id) references users(user_id)
);

create table payments(
payment_id int auto_increment primary key,
order_id int,
amount decimal(10,2) not null,
payment_status enum('PENDING','SUCCESS','FAILED') default 'PENDING',
payment_method enum('VNPAY') not null,
transaction_code varchar(255),
gateway_response text,
paid_at datetime,
created_at datetime not null,
foreign key (order_id) references orders(order_id)
);
