
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
        final String SERVER = "jdbc:mysql://127.0.0.1:3306/?user=root"; //server location goes here

        try{
            Connection con = DriverManager.getConnection(SERVER, ID, PW);

            if (con != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to make connection!");
            }

            Statement stmt = con.createStatement(); // creates a statement object

            ResultSet rs = stmt.executeQuery("Select <> From <> Where <>"); //Statement.executeQuery returns a ResultSet Object
            
            while (rs.next()){
                String bNO = rs.getString("BranchNo");
            }
        } catch(SQLException e){
            System.out.println(e);
        }
    }



    public void displayAvailableShipmentsToCarrier() throws SQLException{
        // Implimenting one of the expected queries
        Statement stmt = con.createStatement(); // create a new statement object
        ResultSet rs = stmt.executeQuery("SELECT * FROM Shipments WHERE 'pickup_time' IS NULL");
        ResultSetMetaData rsmt = rs.getMetaData();
        // this part lists all the results from the query, can be trimmed later for only relavent information
        while(rs.next()){
            for (int i = 1; i <= rsmt.getColumnCount(); i++){
                if (i > 1) System.out.print(", ");
                System.out.print(rs.getString(i) + " " + rsmt.getColumnName(i));
            }
            System.out.println("");
        }
    }

    public String getShipperAddressByShipmentID(int shipmentID ) throws SQLException{
        // the goal of this is to return the 
        Statement stmt = con.createStatement(); // create a new statement object
        ResultSet rs = stmt.executeQuery("SELECT addr FROM User WHERE user_id = " + shipmentID);
        return rs.getString(0); // this will be the first response, no check to ensure its the only one
    
    }

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

    public void addItemToShipperInventory(String shipper, String itemName, Float price) throws SQLException{
        PreparedStatement findShipperID = con.prepareStatement(
            "SELECT UID FROM SHIPPER WHERE 'name' = ?");
        findShipperID.setString(1, shipper);
        ResultSet rs = findShipperID.executeQuery();
        int shipperID = rs.getInt("UID");
        PreparedStatement addItem = con.prepareStatement(
            "INSET INTO ITEM(shipped_by,'name',price) VALUES(?,?,?)");
        addItem.setInt(1, shipperID);
        addItem.setString(2, itemName);
        addItem.setFloat(3, price);
        addItem.executeUpdate();
    }

    
}
