drop database if exists PlantMarket;
create database PlantMarket;
use PlantMarket;

create table roles
(
    role_id   int auto_increment primary key,
    role_name varchar(25) unique not null
);

create table users
(
    user_id    char(36) primary key not null,
    username   varchar(50)          not null unique,
    password   varchar(255)         not null,
    email      varchar(100)         not null unique,
    full_name  varchar(255),
    avatar_url varchar(500),
    phone      varchar(15),
    address    varchar(255),
    role_id    int,
    status     boolean default 1    not null,
    created_at datetime             not null,
    updated_at datetime             not null,
    foreign key (role_id) references roles (role_id)
);

create table categories
(
    category_id   int auto_increment primary key,
    category_name varchar(50) not null unique,
    is_deleted    boolean default 0,
    description   varchar(255)
);

create table products
(
    product_id       int auto_increment primary key,
    category_id      int,
    name             varchar(50)                   not null,
    description      varchar(255),
    price            decimal(10, 2)                not null,
    is_best_seller   boolean default 0,
    stock_quantity   int                           not null check (stock_quantity >= 0),
    image_url        varchar(500)                  not null,
    difficulty_level enum ('EASY','MEDIUM','HARD') not null,
    created_at       datetime                      not null,
    updated_at       datetime                      not null,
    is_deleted       boolean default 0,
    foreign key (category_id) references categories (category_id)
);

create table carts
(
    cart_id    int auto_increment primary key,
    user_id    char(36) unique,
    created_at datetime not null,
    foreign key (user_id) references users (user_id)
);

create table cart_item
(
    cart_item_id int auto_increment primary key,
    cart_id      int,
    product_id   int,
    quantity     int not null,
    foreign key (cart_id) references carts (cart_id),
    foreign key (product_id) references products (product_id),
    unique (cart_id, product_id)
);

create table orders
(
    order_id         int auto_increment primary key,
    user_id          char(36),
    total_amount     decimal(10, 2) not null,
    status           enum ('PENDING','PAID','SHIPPED','DELIVERED','CANCELLED'),
    shipping_address varchar(255)   not null,
    payment_method   varchar(255)   not null,
    created_at       datetime       not null,
    foreign key (user_id) references users (user_id)
);

create table order_details
(
    order_detail_id int auto_increment primary key,
    order_id        int,
    product_id      int,
    quantity        int            not null,
    price           decimal(10, 2) not null,
    foreign key (order_id) references orders (order_id),
    foreign key (product_id) references products (product_id),
    unique (order_id, product_id)
);

create table posts
(
    post_id    int auto_increment primary key,
    user_id    char(36),
    content    text     not null,
    is_deleted boolean default 0,
    created_at datetime not null,
    updated_at datetime not null,
    foreign key (user_id) references users (user_id)
);

create table post_images
(
    image_id  int auto_increment primary key,
    post_id   int,
    image_url varchar(500) not null,
    foreign key (post_id) references posts (post_id)
);

create table comments
(
    comment_id        int auto_increment primary key,
    post_id           int,
    user_id           char(36),
    parent_comment_id int      null,
    content           text     not null,
    created_at        datetime not null,
    foreign key (post_id) references posts (post_id),
    foreign key (user_id) references users (user_id)
);

create table notifications
(
    notification_id int auto_increment primary key,
    user_id         char(36),
    content         varchar(500) not null,
    is_read         boolean default 0,
    created_at      datetime     not null,
    foreign key (user_id) references users (user_id)
);

create table user_tokens
(
    token_id      int auto_increment primary key,
    user_id       char(36),
    refresh_token varchar(255) unique not null,
    expires_at    datetime            not null,
    is_revoked    boolean default 0,
    foreign key (user_id) references users (user_id)
);

create table payments
(
    payment_id       int auto_increment primary key,
    order_id         int,
    amount           decimal(10, 2) not null,
    payment_status   enum ('PENDING','SUCCESS','FAILED') default 'PENDING',
    payment_method   enum ('VNPAY') not null,
    transaction_code varchar(255),
    gateway_response text,
    paid_at          datetime,
    created_at       datetime       not null,
    foreign key (order_id) references orders (order_id)
);


INSERT INTO roles (role_name)
VALUES ('ADMIN'),
       ('USER');


INSERT INTO users (user_id, username, password, email, full_name, avatar_url, phone, address, role_id, status,
                   created_at, updated_at)
VALUES (UUID(), 'admin1', '123', 'admin1@example.com', 'Admin One', 'https://example.com/avatar1.png', '0123456789',
        '123 Admin Street', 1, 1, NOW(), NOW()),
       (UUID(), 'user1', '123', 'user1@example.com', 'User One', 'https://example.com/avatar2.png', '0987654321',
        '456 User Avenue', 2, 1, NOW(), NOW()),
       (UUID(), 'user2', '123', 'user2@example.com', 'User two', 'https://example.com/avatar3.png', '0112233445',
        '789 Mod Lane', 2, 1, NOW(), NOW());

-- Categories
INSERT INTO categories (category_name, is_deleted, description)
VALUES ('Indoor Plant Kits', 0, 'Complete kits for growing indoor plants at home.'),
       ('Ornamental Plant Kits', 0, 'Kits for decorative plants to beautify your space.'),
       ('Succulent & Cactus Kits', 0, 'Kits for growing low-maintenance succulents and cacti.'),
       ('Bonsai Kits', 0, 'Artistic bonsai kits for beginners and enthusiasts.'),
       ('Herb Kits', 0, 'Kits for growing culinary and aromatic herbs.');

-- Products
INSERT INTO products
(category_id, name, description, price, is_best_seller, stock_quantity, image_url, difficulty_level, created_at,
 updated_at, is_deleted)
VALUES
    -- Indoor Plant Kits
    (1, 'Monstera Plant Kit',
     'Complete kit to grow a Monstera indoors, including soil, pot, and starter seedling.',
     25000, 0, 20, '/images/image.png', 'EASY', NOW(), NOW(), 0),

    (1, 'Snake Plant Kit',
     'Easy-care Snake Plant kit that includes soil, pot, and a healthy starter plant. Great for air purification.',
     15000, 1, 30, '/images/image.png', 'EASY', NOW(), NOW(), 0),

    -- Ornamental Plant Kits
    (2, 'Fiddle Leaf Fig Kit',
     'Premium kit to grow a stylish Fiddle Leaf Fig tree indoors, perfect for home decor.',
     38000, 0, 10, '/images/image.png', 'MEDIUM', NOW(), NOW(), 0),

    (2, 'Peace Lily Kit',
     'Complete Peace Lily growing kit that blooms beautifully and thrives in low light.',
     20000, 0, 25, '/images/image.png', 'EASY', NOW(), NOW(), 0),

    -- Succulent & Cactus Kits
    (3, 'Aloe Vera Kit',
     'Beginner-friendly Aloe Vera growing kit with essential materials included.',
     12000, 1, 40, '/images/image.png', 'EASY', NOW(), NOW(), 0),

    (3, 'Golden Barrel Cactus Kit',
     'Complete kit to grow a Golden Barrel Cactus, ideal for decorative indoor setups.',
     18000, 0, 15, '/images/image.png', 'MEDIUM', NOW(), NOW(), 0),

    -- Bonsai Kits
    (4, 'Maple Bonsai Kit',
     'Artistic Maple Bonsai kit for creating a beautiful miniature tree.',
     60000, 0, 5, '/images/image.png', 'HARD', NOW(), NOW(), 0),

    (4, 'Juniper Bonsai Kit',
     'Juniper Bonsai kit with shaping tools and starter materials, perfect for bonsai hobbyists.',
     45000, 0, 8, '/images/image.png', 'HARD', NOW(), NOW(), 0),

    -- Herb Kits
    (5, 'Lavender Herb Kit',
     'Fragrant Lavender growing kit that includes high-quality seeds and organic soil.',
     22000, 1, 18, '/images/image.png', 'MEDIUM', NOW(), NOW(), 0),

    (5, 'Mint Herb Kit',
     'Easy-to-grow Mint kit ideal for fresh herbs, tea, and cooking.',
     9000, 0, 35, '/images/image.png', 'EASY', NOW(), NOW(), 0);

-- Cart cho user1
INSERT INTO carts (user_id, created_at)
SELECT user_id, NOW()
FROM users
WHERE username = 'user1';

-- Cart cho user2
INSERT INTO carts (user_id, created_at)
SELECT user_id, NOW()
FROM users
WHERE username = 'user2';

INSERT INTO cart_item (cart_id, product_id, quantity)
VALUES ((SELECT cart_id
         FROM carts c
                  JOIN users u ON c.user_id = u.user_id
         WHERE u.username = 'user1'),
        2,
        2),
       ((SELECT cart_id
         FROM carts c
                  JOIN users u ON c.user_id = u.user_id
         WHERE u.username = 'user1'),
        5,
        1);

INSERT INTO cart_item (cart_id, product_id, quantity)
VALUES ((SELECT cart_id
         FROM carts c
                  JOIN users u ON c.user_id = u.user_id
         WHERE u.username = 'user2'),
        1,
        1),
       ((SELECT cart_id
         FROM carts c
                  JOIN users u ON c.user_id = u.user_id
         WHERE u.username = 'user2'),
        10,
        3),
       ((SELECT cart_id
         FROM carts c
                  JOIN users u ON c.user_id = u.user_id
         WHERE u.username = 'user2'),
        9,
        1);

ALTER TABLE orders
    MODIFY COLUMN payment_method ENUM('COD', 'PAYOS') NOT NULL;

ALTER TABLE payments
    MODIFY COLUMN payment_method ENUM('COD', 'PAYOS') NOT NULL;

ALTER TABLE orders
    ADD COLUMN full_name VARCHAR(255) NOT NULL AFTER user_id,
    ADD COLUMN phone_number VARCHAR(20) NOT NULL AFTER full_name,
    ADD COLUMN email_address VARCHAR(100) AFTER phone_number,
    ADD COLUMN notes TEXT AFTER payment_method;

ALTER TABLE orders
    MODIFY COLUMN status ENUM('PENDING', 'PAID', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING';


ALTER TABLE orders
    ADD COLUMN shipping_fee DECIMAL(10, 2) NOT NULL DEFAULT 30000.00 AFTER total_amount;

ALTER TABLE orders
    ADD COLUMN order_code BIGINT UNIQUE AFTER order_id;

