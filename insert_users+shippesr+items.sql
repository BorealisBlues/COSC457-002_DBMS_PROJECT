INSERT INTO User (user_name, email, phone_number, addr, rating)
VALUES 
	('Target', 'target@target.com', 4108234423, '1238 Putty Hill Ave, Towson, MD 21286', 3.5), 
	('WholeFoods', 'wholefoods@wholefoods.com', 4436526566, '300 Towson Row, Towson, MD 21204', 4.5); 
    

INSERT INTO Shipper (user_id, open_time, close_time)
VALUES (1, '09:00:00', '18:00:00'),  -- Store 1
       (2, '08:00:00', '20:00:00');  -- Store 2
       
-- Items for Store 1
INSERT INTO Item (item_id, name, description, price, category, in_stock, item_count, shipper_id)
VALUES 
	('item001', 'T-shirt', 'Small Red T-Shirt', 2.5, 'Clothing', TRUE, 100, 1),
	('item002', 'T-Shift', 'Medium Red T-Shirt', 3.8, 'Clothing', TRUE, 150, 1),
	('item003', 'T-Shift', 'Large Red T-Shirt', 4.3, 'Clothing', TRUE, 150, 1),
	('item004', 'Jeans', 'Medium Blue Jeans', 25.0, 'Clothing', TRUE, 80, 1),
    ('item005', 'Dress', 'Black Party Dress', 45.0, 'Clothing', TRUE, 60, 1),
    ('item006', 'Sneakers', 'White Sneakers Size 8m', 30.0, 'Footwear', TRUE, 100, 1),
    ('item007', 'Hoodie', 'Large Grey Hoodie', 35.0, 'Clothing', TRUE, 70, 1),
    ('item008', 'Jacket', 'Leather Jacket', 80.0, 'Clothing', TRUE, 50, 1),
    ('item009', 'Boots', 'Brown Leather Boots', 60.0, 'Footwear', TRUE, 90, 1), 
	('item010', 'Smartphone', 'Latest model smartphone', 699.99, 'Electronics', TRUE, 50, 1),
    ('item011', 'Laptop', 'Thin and lightweight laptop', 1299.99, 'Electronics', TRUE, 30, 1),
    ('item012', 'Headphones', 'Wireless noise-canceling headphones', 199.99, 'Electronics', TRUE, 80, 1),
    ('item013', 'Smartwatch', 'Fitness tracking smartwatch', 299.99, 'Electronics', TRUE, 60, 1),
    ('item014', 'Tablet', '10-inch touchscreen tablet', 499.99, 'Electronics', TRUE, 40, 1),
    ('item015', 'Bluetooth Speaker', 'Portable Bluetooth speaker', 79.99, 'Electronics', TRUE, 100, 1);

-- Items for Store 2
INSERT INTO Item (item_id, name, description, price, category, in_stock, item_count, shipper_id)
VALUES 
	('item100', 'Bread', 'Freshly baked bread', 2.0, 'Bakery', TRUE, 80, 2),
	('item101', 'Eggs', 'Organic eggs', 4.0, 'Dairy', TRUE, 120, 2),
	('item102', 'Tomatoes', 'Juicy tomatoes', 2.3, 'Produce', TRUE, 70, 2),
	('item103', 'Avocado', 'Ripe avocado', 1.99, 'Produce', TRUE, 90, 2),
    ('item104', 'Salmon', 'Fresh Atlantic salmon', 12.99, 'Seafood', TRUE, 40, 2),
    ('item105', 'Quinoa', 'Organic white quinoa', 5.99, 'Grains', TRUE, 60, 2),
    ('item106', 'Almond Milk', 'Unsweetened almond milk', 3.49, 'Dairy', TRUE, 80, 2),
    ('item107', 'Organic Kale', 'Fresh organic kale bunch', 2.49, 'Produce', TRUE, 70, 2),
    ('item108', 'Greek Yogurt', 'Non-fat Greek yogurt', 4.29, 'Dairy', TRUE, 50, 2),
    ('item109', 'Organic Strawberries', 'Fresh organic strawberries', 3.99, 'Produce', TRUE, 100, 2),
    ('item110', 'Whole Grain Bread', 'Whole grain bread loaf', 4.49, 'Bakery', TRUE, 60, 2),
    ('item111', 'Organic Free-Range Eggs', 'Organic free-range eggs', 5.49, 'Dairy', TRUE, 80, 2),
    ('item112', 'Artisanal Cheese', 'Assorted artisanal cheese', 9.99, 'Dairy', TRUE, 40, 2),
    ('item113', 'Organic Spinach', 'Fresh organic spinach', 2.99, 'Produce', TRUE, 70, 2),
    ('item114', 'Alaskan Cod Fillet', 'Wild-caught Alaskan cod fillet', 10.99, 'Seafood', TRUE, 30, 2);
