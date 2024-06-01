INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Cliente 1', '1111111111', 'Dirección Cliente 1', 'Barrio 1', 'Ciudad 1');

INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Cliente 2', '2222222222', 'Dirección Cliente 2', 'Barrio 2', 'Ciudad 2');

INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Cliente 3', '3333333333', 'Dirección Cliente 3', 'Barrio 3', 'Ciudad 3');

INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Cliente 4', '4444444444', 'Dirección Cliente 4', 'Barrio 4', 'Ciudad 4');

INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Cliente 5', '5555555555', 'Dirección Cliente 5', 'Barrio 5', 'Ciudad 5');

-- Insertar Producto 1
INSERT INTO product (name, price, description) VALUES ('Producto A', 29.99, 'Descripción del Producto A');

-- Insertar Producto 2
INSERT INTO product (name, price, description) VALUES ('Producto B', 49.99, 'Descripción del Producto B');

-- Insertar Producto 3
INSERT INTO product (name, price, description) VALUES ('Producto C', 9.99, 'Descripción del Producto C');

-- Insertar Producto 4
INSERT INTO product (name, price, description) VALUES ('Producto D', 39.99, 'Descripción del Producto D');

-- Insertar Producto 5
INSERT INTO product (name, price, description) VALUES ('Producto E', 19.99, 'Descripción del Producto E');


INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-09', 'Contado', 59.99, 1);

INSERT INTO order_detail (order_id, product_id, quantity) SELECT (SELECT id_order FROM orders ORDER BY id_order DESC LIMIT 1), 1, 1;

INSERT INTO order_detail (order_id, product_id, quantity) SELECT (SELECT id_order FROM orders ORDER BY id_order DESC LIMIT 1), 3, 3;


