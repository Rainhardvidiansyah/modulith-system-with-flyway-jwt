-- hapus seed lama dulu
DELETE FROM products;

-- insert ulang dengan id eksplisit
INSERT INTO products (id, name, description, price)
VALUES
(gen_random_uuid(), 'Laptop ThinkPad X1', 'Laptop bisnis dengan performa tangguh dan desain tipis.', 18500000.00),
(gen_random_uuid(), 'Mechanical Keyboard K2', 'Keyboard mekanikal wireless dengan lampu RGB.', 950000.00),
(gen_random_uuid(), 'Monitor 4K 27 inch', 'Monitor dengan resolusi tinggi untuk kebutuhan desain grafis.', 5200000.00),
(gen_random_uuid(), 'Logitech Mouse MX3', 'Mouse ergonomis dengan presisi tinggi.', 1300000.00);