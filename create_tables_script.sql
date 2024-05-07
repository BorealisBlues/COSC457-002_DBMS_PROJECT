USE database_project_schema;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY, 
    user_name VARCHAR(100),
    email VARCHAR(100),
    phone_number BIGINT, 
    addr VARCHAR(255), 
    rating FLOAT
);

CREATE TABLE Carrier (
	user_id INT PRIMARY KEY, 
    is_available BOOLEAN, 
    make VARCHAR(100), 
    model VARCHAR(100), 
    year INT(4), 
    color VARCHAR(100), 
    plate VARCHAR(10), 
	FOREIGN KEY (user_id) REFERENCES User(user_id)
); 

CREATE TABLE Shipper (
	user_id INT PRIMARY KEY, 
	open_time TIME, 
    close_time TIME, 
	FOREIGN KEY (user_id) REFERENCES User(user_id)
); 
    
CREATE TABLE Receiver (
	user_id INT PRIMARY KEY, 
	FOREIGN KEY (user_id) REFERENCES User(user_id)
);
    
CREATE TABLE Item (
	item_id VARCHAR(50) PRIMARY KEY, 
    name VARCHAR(100), 
    description VARCHAR(250), 
    price FLOAT,
    category VARCHAR(50), 
    in_stock BOOLEAN,
    item_count INT, 
    shipper_id INT,
    FOREIGN KEY (shipper_id) REFERENCES Shipper(user_id)
);

CREATE TABLE Message ( 
    message_content TEXT, 
    sender_id INT, 
    receiver_id INT, 
    time_sent TIMESTAMP, 
    PRIMARY KEY (sender_id, receiver_id, time_sent), 
    FOREIGN KEY (sender_id) REFERENCES User(user_id), 
    FOREIGN KEY (receiver_id) REFERENCES User(user_id)
);

CREATE TABLE Shipment( 
	shipment_id INT AUTO_INCREMENT PRIMARY KEY, 
    status VARCHAR(50), 
    current_location VARCHAR (100), 
    pickup_time TIMESTAMP, 
    dropoff_time TIMESTAMP, 
    price FLOAT, 
    shipper_id INT,
    carrier_id INT, 
    receiver_id INT, 
    FOREIGN KEY (shipper_id) REFERENCES Shipper(user_id), 
    FOREIGN KEY (carrier_id) REFERENCES Carrier(user_id), 
    FOREIGN KEY (receiver_id) REFERENCES Receiver(user_id)
);

CREATE TABLE Transaction(
	transaction_id INT AUTO_INCREMENT PRIMARY KEY, 
    amount FLOAT,
    time_paid TIMESTAMP,  
    shipment_id INT, 
    FOREIGN KEY (shipment_id) REFERENCES Shipment(shipment_id)
);

CREATE TABLE Contains(
	item_id VARCHAR(50),
    shipment_id INT, 
    amount FLOAT, 
    PRIMARY KEY (item_id, shipment_id), 
    FOREIGN KEY (item_id) REFERENCES Item(item_id), 
    FOREIGN KEY (shipment_id) REFERENCES Shipment(shipment_id)
);