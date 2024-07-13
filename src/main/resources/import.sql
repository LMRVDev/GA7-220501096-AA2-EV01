INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Carlos Pérez', '3111234567', 'Calle Falsa 123', 'La Floresta', 'Bogotá');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Ana Gómez', '3229876543', 'Avenida Siempre Viva 742', 'El Poblado', 'Medellín');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Luis Martínez', '3135555555', 'Carrera 45 #12-34', 'Chapinero', 'Bogotá');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('María Rodríguez', '3146666666', 'Calle 8 #5-67', 'San Antonio', 'Cali');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Jorge Ramírez', '3157777777', 'Carrera 15 #23-45', 'Santa Bárbara', 'Cartagena');

INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Laura Fernández', '3168888888', 'Calle 7 #6-78', 'Alameda', 'Bucaramanga');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Sofía González', '3179999999', 'Avenida Las Palmas 555', 'Los Colores', 'Medellín');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Andrés Castro', '3181231234', 'Carrera 18 #24-56', 'Granada', 'Cali');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Diana Ruiz', '3192342345', 'Calle 12 #34-56', 'Ciudad Jardín', 'Barranquilla');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Fernando López', '3203453456', 'Carrera 30 #40-70', 'San Fernando', 'Cartagena');

INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Claudia Mejía', '3214564567', 'Avenida El Dorado 100', 'Teusaquillo', 'Bogotá');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Paula Moreno', '3225675678', 'Calle 9 #8-90', 'Laureles', 'Medellín');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Juan Torres', '3236786789', 'Carrera 22 #11-22', 'El Peñón', 'Cali');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Natalia Sánchez', '3247897890', 'Calle 6 #7-11', 'Boston', 'Barranquilla');
INSERT INTO customer (name, phone_number, address, neighborhood, city) VALUES ('Roberto Hernández', '3258908901', 'Avenida Colombia 200', 'Manga', 'Cartagena');


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

-- Insertar Producto 1
INSERT INTO product (name, price, description) VALUES ('Proteína Whey', 34.99, 'Proteína de suero de leche para desarrollo muscular');

-- Insertar Producto 2
INSERT INTO product (name, price, description) VALUES ('Multivitamínico Completo', 24.99, 'Suplemento diario con vitaminas y minerales esenciales');

-- Insertar Producto 3
INSERT INTO product (name, price, description) VALUES ('Omega 3', 15.99, 'Ácidos grasos esenciales para la salud cardiovascular');

-- Insertar Producto 4
INSERT INTO product (name, price, description) VALUES ('BCAA', 29.99, 'Aminoácidos de cadena ramificada para recuperación muscular');

-- Insertar Producto 5
INSERT INTO product (name, price, description) VALUES ('Colágeno Hidrolizado', 19.99, 'Suplemento para mejorar la salud de la piel y las articulaciones');

-- Insertar Producto 6
INSERT INTO product (name, price, description) VALUES ('Creatina Monohidratada', 27.99, 'Suplemento para mejorar el rendimiento en el entrenamiento de fuerza');

-- Insertar Producto 7
INSERT INTO product (name, price, description) VALUES ('Vitamina D3', 12.99, 'Suplemento esencial para la salud ósea y el sistema inmunológico');

-- Insertar Producto 8
INSERT INTO product (name, price, description) VALUES ('Pre-Entreno', 39.99, 'Suplemento para aumentar la energía y el enfoque antes del ejercicio');

-- Insertar Producto 9
INSERT INTO product (name, price, description) VALUES ('Maca Andina', 14.99, 'Suplemento natural para mejorar la energía y la vitalidad');

-- Insertar Producto 10
INSERT INTO product (name, price, description) VALUES ('Cloruro de Magnesio', 10.99, 'Suplemento para la salud muscular y nerviosa');

-- Insertar Producto 11
INSERT INTO product (name, price, description) VALUES ('Espino Blanco', 11.99, 'Suplemento para la salud cardiovascular');

-- Insertar Producto 12
INSERT INTO product (name, price, description) VALUES ('Ashwagandha', 22.99, 'Adaptógeno para reducir el estrés y mejorar la energía');

-- Insertar Producto 13
INSERT INTO product (name, price, description) VALUES ('Ginseng Siberiano', 18.99, 'Suplemento para mejorar la resistencia y el bienestar general');

-- Insertar Producto 14
INSERT INTO product (name, price, description) VALUES ('Probióticos', 25.99, 'Suplemento para mejorar la salud digestiva');

-- Insertar Producto 15
INSERT INTO product (name, price, description) VALUES ('Extracto de Té Verde', 16.99, 'Suplemento antioxidante para la salud general y el control de peso');


-- Insertar Orden 1
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-10', 'Tarjeta de Crédito', 94.97, 1);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 1, 2);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 5, 1);


-- Insertar Orden 2
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-11', 'Efectivo', 44.98, 2);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 2, 1);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 3, 3);

-- Insertar Orden 3
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-12', 'PayPal', 27.99, 3);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 6, 1);

-- Insertar Orden 4
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-13', 'Tarjeta de Débito', 62.97, 4);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 4, 1);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 8, 1);

-- Insertar Orden 5
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-14', 'Contado', 39.98, 5);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 9, 2);

-- Insertar Orden 6
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-15', 'Transferencia Bancaria', 51.97, 6);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 7, 2);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 12, 1);

-- Insertar Orden 7
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-16', 'Efectivo', 77.97, 7);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 10, 3);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 13, 1);

-- Insertar Orden 8
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-17', 'Tarjeta de Crédito', 32.98, 8);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 11, 2);

-- Insertar Orden 9
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-18', 'PayPal', 41.98, 9);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 14, 2);

-- Insertar Orden 10
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-19', 'Tarjeta de Débito', 50.97, 10);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 15, 3);

-- Insertar Orden 11
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-20', 'Tarjeta de Crédito', 80.95, 11);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 1, 1);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 2, 1);

-- Insertar Orden 12
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-21', 'Efectivo', 55.98, 12);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 3, 2);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 4, 1);

-- Insertar Orden 13
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-22', 'PayPal', 94.97, 13);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 5, 1);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 6, 2);

-- Insertar Orden 14
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-23', 'Tarjeta de Débito', 99.96, 14);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 7, 4);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 8, 1);

-- Insertar Orden 15
INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-24', 'Contado', 89.97, 15);

SET @orderId = LAST_INSERT_ID();

INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 9, 3);
INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 10, 2);

-- -- Insertar Orden 16
-- INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-25', 'Transferencia Bancaria', 68.97, 1);

-- SET @orderId = LAST_INSERT_ID();

-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 11, 1);
-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 12, 2);

-- -- Insertar Orden 17
-- INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-26', 'Efectivo', 75.97, 2);

-- SET @orderId = LAST_INSERT_ID();

-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 13, 2);
-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 14, 1);

-- -- Insertar Orden 18
-- INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-27', 'Tarjeta de Crédito', 50.97, 3);

-- SET @orderId = LAST_INSERT_ID();

-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 15, 3);

-- -- Insertar Orden 19
-- INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-28', 'PayPal', 104.97, 4);

-- SET @orderId = LAST_INSERT_ID();

-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 1, 1);
-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 2, 1);
-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 3, 1);

-- -- Insertar Orden 20
-- INSERT INTO orders (order_date, payment_mode, total_amount, customer_id) VALUES ('2024-01-29', 'Tarjeta de Débito', 129.96, 5);

-- SET @orderId = LAST_INSERT_ID();

-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 4, 2);
-- INSERT INTO order_detail (order_id, product_id, quantity) VALUES (@orderId, 5, 2);
