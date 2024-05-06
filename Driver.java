
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Driver {

    Connection con;
    /* TODO:
     *      - Double-Check Library for hosting SQL Database 
     *      - waiting on library to download, its gonna be big unhappy w/ me for rn
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


    public void addNewUser(String type, String name, String email, String phone) throws SQLException{
        // checking to make sure that we recieve a valid type
        if((type.toLowerCase() == "reciever") || (type.toLowerCase() == "carrier") || (type.toLowerCase() == "shipper")){
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + type.toUpperCase() +"('Name','Email','Phone') VALUES(?,?,?)");
            //Assuming we use  auto-increment for UID because it's,,, so much easier and nicer
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
        }
        else{
            // in case of invalid type entered
            System.out.println("Please try again and enter a vlid user type! {reciever, carrier, shipper}");
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
