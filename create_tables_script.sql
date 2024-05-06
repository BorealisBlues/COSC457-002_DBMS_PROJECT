USE database_project_schema;

CREATE TABLE User (
    user_id VARCHAR(10) PRIMARY KEY,
    user_name VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    location VARCHAR(255), -- Assuming location is a string (e.g., "123 Main St, City, State, Zip")
    rating FLOAT
);

CREATE TABLE Carrier (
	user_id VARCHAR(10) PRIMARY KEY, 
    is_available BOOLEAN, 
    make VARCHAR(100), 
    model VARCHAR(100), 
    year VARCHAR(100), 
    color VARCHAR(100), 
    plate VARCHAR(10), 
	FOREIGN KEY (user_id) REFERENCES user(user_id)
); 

CREATE TABLE Shipper (
	user_id VARCHAR(10) PRIMARY KEY, 
	open_time TIME, 
    close_time TIME, 
	FOREIGN KEY (user_id) REFERENCES user(user_id)
); 
    
CREATE TABLE Receiver (
	user_id VARCHAR(10) PRIMARY KEY, 
	FOREIGN KEY (user_id) REFERENCES user(user_id)
);
    
CREATE TABLE Item (
	item_id VARCHAR(50) PRIMARY KEY, 
    name VARCHAR(100), 
    description VARCHAR(250), 
    price DOUBLE, 
    category VARCHAR(50), 
    in_stock BOOLEAN,
    item_count INT, 
    shipper_id VARCHAR(10),
    FOREIGN KEY (shipper_id) REFERENCES Shipper(user_id)
);

CREATE TABLE Message (
	message_id VARCHAR(50) PRIMARY KEY, 
    message_content TEXT, 
    sender_id VARCHAR(10), 
    receiver_id VARCHAR(10), 
    date_sent DATE, 
    time_sent TIME, 
    FOREIGN KEY (sender_id) REFERENCES User(user_id), 
    FOREIGN KEY (receiver_id) REFERENCES User(user_id)
);

CREATE TABLE Shipment(
	shipment_id VARCHAR(50) PRIMARY KEY, 
    status VARCHAR(50), 
    current_location VARCHAR (100), 
    pickup_date DATE, 
    dropoff_date DATE, 
    estimated_dropoff_time TIME, 
    pickup_time TIME, 
    dropoff_time TIME, 
    shipper_id VARCHAR(10),
    carrier_id VARCHAR(10), 
    receiver_id VARCHAR(10), 
    FOREIGN KEY (shipper_id) REFERENCES Shipper(user_id), 
    FOREIGN KEY (carrier_id) REFERENCES Carrier(user_id), 
    FOREIGN KEY (receiver_id) REFERENCES Receiver(user_id)
);

CREATE TABLE Transaction(
	transaction_id VARCHAR(50) PRIMARY KEY, 
    amount DOUBLE, 
    date_paid DATE, 
    time_paid TIME, 
    status VARCHAR(100), 
    payment_method VARCHAR(50),
    shipment_id VARCHAR(50), 
    FOREIGN KEY (shipment_id) REFERENCES Shipment(shipment_id)
);

CREATE TABLE Contains(
	item_id VARCHAR(50),
    shipment_id VARCHAR(10), 
    amount INT, 
    PRIMARY KEY (item_id, shipment_id), 
    FOREIGN KEY  (item_id) REFERENCES Item(item_id), 
    FOREIGN KEY (shipment_id) REFERENCES Shipment(user_id)
);