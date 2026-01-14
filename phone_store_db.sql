

CREATE TABLE admin (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         brand VARCHAR(50) NOT NULL,
                         price DECIMAL(12,2) NOT NULL,
                         stock INT NOT NULL
);


CREATE TABLE customer (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          phone VARCHAR(20),
                          email VARCHAR(100) UNIQUE,
                          address VARCHAR(255)
);

CREATE TABLE invoice (
                         id SERIAL PRIMARY KEY,
                         customer_id INT REFERENCES customer(id) , 
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
                         total_amount DECIMAL(12,2) NOT NULL
);

CREATE TABLE invoice_details (
                                 id SERIAL PRIMARY KEY,
                                 invoice_id INT REFERENCES invoice(id) ON DELETE CASCADE, 
                                 product_id INT REFERENCES product(id),
                                 quantity INT NOT NULL,
                                 unit_price DECIMAL(12,2) NOT NULL 
);

INSERT INTO admin (username, password) VALUES
 ('admin', '123456'),
('manager', '123456');

INSERT INTO product (name, brand, price, stock) VALUES
('Laptop Dell XPS 13', 'Dell', 25000000, 10),
('iPhone 15 Pro', 'Apple', 30000000, 20),
('Chuột Logitech G502', 'Logitech', 1000000, 50),
('Bàn phím cơ Keychron', 'Keychron', 2500000, 30),
('Tai nghe Sony WH-1000XM5', 'Sony', 8000000, 15);

INSERT INTO customer (name, phone, email, address) VALUES
('Nguyen Van A', '0901234567', 'vana@example.com', 'Hà Nội'),
('Tran Thi B', '0912345678', 'btran@example.com', 'TP.HCM'),
('Le Van C', '0987654321', NULL, 'Đà Nẵng');

INSERT INTO invoice (customer_id, created_at, total_amount) VALUES
(1, '2023-10-25 10:00:00', 26000000),
(2, '2023-10-26 14:30:00', 60000000);

INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 25000000),
(1, 3, 1, 1000000);

INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES
(2, 2, 2, 30000000);