-- Insert categories
INSERT INTO categories (name, description, created_at, updated_at) VALUES
('Electronics', 'Electronic devices and gadgets', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Clothing', 'Clothes and fashion accessories', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Books', 'Books and literature', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Home & Garden', 'Home decor and garden supplies', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Insert products
INSERT INTO products (name, description, price, stock_quantity, category_id, created_at, updated_at) VALUES
('Smartphone', 'Latest model smartphone with advanced features', 699.99, 50, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Laptop', 'High-performance laptop for professionals', 1299.99, 25, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Headphones', 'Wireless noise-cancelling headphones', 199.99, 100, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('T-shirt', 'Cotton t-shirt with logo print', 19.99, 200, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Jeans', 'Classic blue denim jeans', 49.99, 150, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Java Programming', 'Comprehensive guide to Java programming', 39.99, 75, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Spring Boot in Action', 'Learn Spring Boot development', 44.99, 60, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Coffee Table', 'Modern wooden coffee table', 249.99, 15, 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Plant Pot', 'Ceramic pot for indoor plants', 18.99, 120, 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());