USE database_project_schema;

CREATE TABLE User (
    user_id INT AUTO_INCEMENT PRIMARY KEY, --changed type to int, made auto-incrementing
    user_name VARCHAR(100),
    email VARCHAR(100),
    phone_number INT(10), -- changed type from VARCHAR to int(10) ###-###-####
    addr VARCHAR(255), -- changed name from keyword location to addr(ess)
    rating FLOAT
);

CREATE TABLE Carrier (
	user_id INT PRIMARY KEY, 
    is_available BOOLEAN, 
    make VARCHAR(100), 
    model VARCHAR(100), 
    year INT(4), --changed from VARCHAR to INT(4)
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
    price FLOAT, --changed type from double to float
    category VARCHAR(50), 
    in_stock BOOLEAN,
    item_count INT, 
    shipper_id INT,
    FOREIGN KEY (shipper_id) REFERENCES Shipper(user_id)
);

CREATE TABLE Message ( --removed messageID attribute, as attrs sender_id, reciever_id & time_sent are sufficent
    message_content TEXT, 
    sender_id INT, 
    receiver_id INT, 
    time_sent TIMESTAMP, -- consolidated date & time attributes into single timestamp attribute
    PRIMARY KEY (sender_id, reciever_id, time_sent) -- altered primary key to reflect
    FOREIGN KEY (sender_id) REFERENCES User(user_id), 
    FOREIGN KEY (receiver_id) REFERENCES User(user_id)
);

CREATE TABLE Shipment( -- strictly speaking this table does not fit ideal normal forms because
	shipment_id INT AUTO_INCREMENT PRIMARY KEY, --combo of pickup_time, shipper_id, carrier_id, & reciever_id are also a candidate key
    status VARCHAR(50), -- also made shipment ID auto-incrementing
    current_location VARCHAR (100), 
    pickup_time TIMESTAMP, -- consolidated date & time attrs into single attr of type TIMESTAMP
    dropoff_time TIMESTAMP, -- removed estimated dropoff time for simplicity
    shipper_id INT,
    carrier_id INT, 
    receiver_id INT, 
    FOREIGN KEY (shipper_id) REFERENCES Shipper(user_id), 
    FOREIGN KEY (carrier_id) REFERENCES Carrier(user_id), 
    FOREIGN KEY (receiver_id) REFERENCES Receiver(user_id)
);

CREATE TABLE Transaction(
	transaction_id INT AUTO_INCREMENT PRIMARY KEY, --changed type from varchar to int
    amount FLOAT, --changed type from double to float 
    -- removed STATUS attr as status can be inferred from whether the time_paid attr is or is not null
    time_paid TIMESTAMP,  --consolidated into single timestamp
    payment_method VARCHAR(50), --might remove this one later as it seems superfluous to our goal
    shipment_id INT, 
    FOREIGN KEY (shipment_id) REFERENCES Shipment(shipment_id)
);

CREATE TABLE Contains(
	item_id VARCHAR(50),
    shipment_id INT, 
    amount FLOAT, -- changed from int to float
    PRIMARY KEY (item_id, shipment_id), 
    FOREIGN KEY (item_id) REFERENCES Item(item_id), 
    FOREIGN KEY (shipment_id) REFERENCES Shipment(user_id)
);