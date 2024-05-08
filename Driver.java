
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
// import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*; 

public class Driver {

    Connection con;
    /* TODO:
     * 
     */

    public static void main(String args[]){
        try{ //check that the driver is installed
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            System.out.println(e);
        }

        final String ID = "root"; // Login ID goes Here
        final String PW = "HTparking2002!"; //Login Password goes here
        final String SERVER = "jdbc:mysql://127.0.0.1:3306/DB_Project?user=root"; //server location goes here

        try{
            Connection con = DriverManager.getConnection(SERVER, ID, PW);

            if (con != null) {
                System.out.println("Connection successful!");

                Driver driver = new Driver();

                try {
                    driver.createAccount();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } else {
                System.out.println("Failed to make connection!");
            }

            // Statement stmt = con.createStatement(); // creates a statement object

        } catch(SQLException e){
            System.out.println(e);
        }


    }

    // SHARED FUCNTION 
    
    public void createAccount() throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Already have an ccount? (yes/no)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Receiver or Carrier? (receiver/carrier)");
            String type = scanner.nextLine().toLowerCase();
            if (type.equalsIgnoreCase("receiver")) {
                displayShippersAndSelect();
            } else if (type.equalsIgnoreCase("carrier")) {
                displayAvailableShipmentsToCarrier();
            } else {
                System.out.println("Invalid type. Please enter either 'receiver' or 'carrier'.");
            }
            scanner.close();
            return;
        } else if (!response.equalsIgnoreCase("no")) {
            System.out.println("Invalid response. Please enter either 'yes' or 'no'.");
            scanner.close();
            return;
        } 



        System.out.println("Enter your name:");
        String user_name = scanner.nextLine();

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        System.out.println("Enter your phone number:");
        String phone_number = scanner.nextLine();

        System.out.println("Enter your address:");
        String addr = scanner.nextLine();

        System.out.println("Enter your rating:");
        float rating = scanner.nextFloat();
        scanner.nextLine();  // Consume newline left-over

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO User (user_name, email, phone_number, addr, rating) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, user_name);
        pstmt.setString(2, email);
        pstmt.setString(3, phone_number);
        pstmt.setString(4, addr);
        pstmt.setFloat(5, rating);
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int userId = rs.getInt(1);

            System.out.println("Are you a carrier or a receiver (receiver/carrier):");
            String type = scanner.nextLine().toLowerCase();

            switch(type){
                case "receiver":
                    pstmt = con.prepareStatement("INSERT INTO Receiver (user_id) VALUES(?)");
                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();

                    System.out.println("You have successfully created a receiver account with user ID " + userId);

                    displayShippersAndSelect();

                    break;
                case "carrier":
                    System.out.println("Enter your vehicle make:");
                    String make = scanner.nextLine();

                    System.out.println("Enter your vehicle model:");
                    String model = scanner.nextLine();

                    System.out.println("Enter your vehicle year:");
                    int year = scanner.nextInt();
                    scanner.nextLine();  // Consume newline left-over

                    System.out.println("Enter your vehicle color:");
                    String color = scanner.nextLine();

                    System.out.println("Enter your vehicle plate:");
                    String plate = scanner.nextLine();

                    pstmt = con.prepareStatement("INSERT INTO Carrier (user_id, make, model, year, color, plate) VALUES(?,?,?,?,?,?)");
                    pstmt.setInt(1, userId);
                    pstmt.setString(2, make);
                    pstmt.setString(3, model);
                    pstmt.setInt(4, year);
                    pstmt.setString(5, color);
                    pstmt.setString(6, plate);
                    pstmt.executeUpdate();

                    System.out.println("You have successfully created a carrier account with user ID " + userId);
                    break;
                default:
                    System.out.println("Invalid type. Please enter either 'carrier' or 'receiver'.");
            }
        }

        scanner.close();
    }

    // RECEIVER FUCNTIONS


    // display all the shippes and allow user to select a shipper; return the shipper's ID
    public void displayShippersAndSelect() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Shipper");
    
        while(rs.next()){
            System.out.println("Shipper ID: " + rs.getInt("user_id") + ", Open Time: " + rs.getTime("open_time") + ", Close Time: " + rs.getTime("close_time"));
        }
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the shipper you want to select:");
        String selectedShipperId = scanner.nextLine();
        scanner.close();
    
        displayShipperItemsAndCreateShipment(selectedShipperId);
    }

    // display all the items of the selected shipper
    public void displayShipperItemsAndCreateShipment(String shipperId) throws SQLException {
        // Insert a new Shipment record
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the receiver ID for the new shipment:");
        int receiverId = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
        scanner.close();

        PreparedStatement insertShipment = con.prepareStatement("INSERT INTO Shipment (status, shipper_id, receiver_id) VALUES (?, ?, ?)");
        insertShipment.setString(1, "adding items");
        insertShipment.setString(2, shipperId);
        insertShipment.setInt(3, receiverId);
        insertShipment.executeUpdate();

        System.out.println("A new shipment has been created with status 'adding items'.");


        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Item WHERE shipper_id = ?");
        pstmt.setString(1, shipperId);
        ResultSet rs = pstmt.executeQuery();
    
        while(rs.next()){
            System.out.println("Item ID: " + rs.getInt("item_id") + ", Name: " + rs.getString("name") + ", Price: " + rs.getFloat("price"));
        }
    }

    // select items for shipment and add them to the Contains table
    public void selectItemsForShipment(int shipment_id) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String item_id;
        int quantity;
    
        while (true) {
            System.out.println("Enter the item_id or 'pay' to finish:");
            item_id = scanner.nextLine();
    
            if (item_id.equalsIgnoreCase("pay")) {
                sumShipmentPriceAndCreateTransaction(shipment_id);
                break;
            }
    
            System.out.println("Enter the quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over
    
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Contains (item_id, shipment_id, amount) VALUES (?, ?, ?)");
            pstmt.setString(1, item_id);
            pstmt.setInt(2, shipment_id);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
    
            subtractQuantityFromItems(item_id, quantity);
        }
    
        scanner.close();
    }
    
    // subtract item_count from Item table
    public void subtractQuantityFromItems(String item_id, int quantity) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("UPDATE Item SET item_count = item_count - ? WHERE item_id = ?");
        pstmt.setInt(1, quantity);
        pstmt.setString(2, item_id);
        pstmt.executeUpdate();
    }

    public void sumShipmentPriceAndCreateTransaction(int shipment_id) throws SQLException {
        float sum = 0.0f;
    
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Contains WHERE shipment_id = ?");
        pstmt.setInt(1, shipment_id);
        ResultSet rs = pstmt.executeQuery();
    
        while (rs.next()) {
            String item_id = rs.getString("item_id");
            int quantity = rs.getInt("amount");
    
            PreparedStatement itemStmt = con.prepareStatement("SELECT price FROM Item WHERE item_id = ?");
            itemStmt.setString(1, item_id);
            ResultSet itemRs = itemStmt.executeQuery();
    
            if (itemRs.next()) {
                float price = itemRs.getFloat("price");
                sum += price * quantity;
            }
        }
    
        PreparedStatement updateStmt = con.prepareStatement("UPDATE Shipment SET price = ?, status = 'paid' WHERE shipment_id = ?");
        updateStmt.setFloat(1, sum);
        updateStmt.setInt(2, shipment_id);
        updateStmt.executeUpdate();
    
        PreparedStatement insertTransaction = con.prepareStatement("INSERT INTO Transaction (amount, shipment_id) VALUES (?, ?)");
        insertTransaction.setFloat(1, sum);
        insertTransaction.setInt(2, shipment_id);
        insertTransaction.executeUpdate();
    }

    // CARRIER FUNCTIONS
    // display all the shipments that are available to the carrier (status of shipment is paid)
    public void displayAvailableShipmentsToCarrier() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Shipment WHERE status = 'paid'");
    
        while(rs.next()){
            System.out.println("Shipment ID: " + rs.getInt("shipment_id") + ", Price: " + rs.getFloat("price"));
        }
    
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the shipment you want to select:");
        int selectedShipmentId = scanner.nextInt();

        System.out.println("Enter your carrier ID:");
        int carrierId = scanner.nextInt();

        scanner.close();
    
        displayShipmentItemsAndSelect(selectedShipmentId, carrierId);
    }

    // display all the items of the selected shipment and the location of the shipper 
    public void displayShipmentItemsAndSelect(int shipmentId, int carrierId) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Contains WHERE shipment_id = ?");
        pstmt.setInt(1, shipmentId);
        ResultSet rs = pstmt.executeQuery();
    
        while(rs.next()){
            System.out.println("Item ID: " + rs.getInt("item_id") + ", Amount: " + rs.getInt("amount"));
        }
    
        // Display the location of the shipper
        pstmt = con.prepareStatement("SELECT * FROM Shipper WHERE user_id = (SELECT shipper_id FROM Shipment WHERE shipment_id = ?)");
        pstmt.setInt(1, shipmentId);
        rs = pstmt.executeQuery();
    
        if (rs.next()) {
            System.out.println("Shipper ID: " + rs.getInt("user_id") + ", Open Time: " + rs.getTime("open_time") + ", Close Time: " + rs.getTime("close_time"));
        }
    
        System.out.println("Do you want to accept this shipment? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        scanner.close();
    
        if (response.equalsIgnoreCase("yes")) {
            // Update the carrier_id in the Shipment table
            pstmt = con.prepareStatement("UPDATE Shipment SET carrier_id = ? WHERE shipment_id = ?");
            pstmt.setInt(1, carrierId);
            pstmt.setInt(2, shipmentId);
            pstmt.executeUpdate();
    
            updateShipmentStatus(shipmentId);
        }
    }

    // pick up the shipment and update the status of the shipment
    public void updateShipmentStatus(int shipmentId) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("UPDATE Shipment SET status = ?, pickup_time = ? WHERE shipment_id = ?");
        pstmt.setString(1, "picked up");
        pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        pstmt.setInt(3, shipmentId);
        pstmt.executeUpdate();

        System.out.println("Shipment " + shipmentId + " has been picked up.");

        deliverShipment(shipmentId);
    }


    // deliver the shipment and update the status of the shipment
    public void deliverShipment(int shipmentId) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("UPDATE Shipment SET status = ?, dropoff_time = ? WHERE shipment_id = ?");
        pstmt.setString(1, "delivered");
        pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        pstmt.setInt(3, shipmentId);
        pstmt.executeUpdate();

        System.out.println("Shipment " + shipmentId + " has been delivered.");
    }

    // public void displayAvailableShipmentsToCarrier() throws SQLException{
    //     // Implimenting one of the expected queries
    //     Statement stmt = con.createStatement(); // create a new statement object
    //     ResultSet rs = stmt.executeQuery("SELECT * FROM Shipments WHERE 'pickup_time' IS NULL");
    //     ResultSetMetaData rsmt = rs.getMetaData();
    //     // this part lists all the results from the query, can be trimmed later for only relavent information
    //     while(rs.next()){
    //         for (int i = 1; i <= rsmt.getColumnCount(); i++){
    //             if (i > 1) System.out.print(", ");
    //             System.out.print(rs.getString(i) + " " + rsmt.getColumnName(i));
    //         }
    //         System.out.println("");
    //     }
    // }

    // public String getShipperAddressByShipmentID(int shipmentID ) throws SQLException{
    //     // the goal of this is to return the 
    //     Statement stmt = con.createStatement(); // create a new statement object
    //     ResultSet rs = stmt.executeQuery("SELECT addr FROM User WHERE user_id = " + shipmentID);
    //     return rs.getString(0); // this will be the first response, no check to ensure its the only one
    
    // }

    public void addNewUser(String type, String name, String email, String phone) throws SQLException{
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO ? ('Name','Email','Phone') VALUES(?,?,?)");
        pstmt.setString(0, "User");
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, phone);
        pstmt.executeUpdate();
        // checking to make sure that we recieve a valid type
        switch(type.toLowerCase()){
            case "reciever","carrier","shipper":
            pstmt.setString(0, type.toLowerCase());
            pstmt.executeUpdate();
            
            default:
                System.out.println("Please try again and enter a valid user type! {reciever, carrier, shipper}");
        }
    }

    // public void addItemToShipperInventory(String shipper, String itemName, Float price) throws SQLException{
    //     PreparedStatement findShipperID = con.prepareStatement(
    //         "SELECT UID FROM SHIPPER WHERE 'name' = ?");
    //     findShipperID.setString(1, shipper);
    //     ResultSet rs = findShipperID.executeQuery();
    //     int shipperID = rs.getInt("UID");
    //     PreparedStatement addItem = con.prepareStatement(
    //         "INSET INTO ITEM(shipped_by,'name',price) VALUES(?,?,?)");
    //     addItem.setInt(1, shipperID);
    //     addItem.setString(2, itemName);
    //     addItem.setFloat(3, price);
    //     addItem.executeUpdate();
    // }

    
}
