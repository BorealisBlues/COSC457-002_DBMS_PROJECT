import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

public class ChatHandler {
    PreparedStatement pstmt;
    public ChatHandler(Connection con) throws SQLException{
        // constructor for chatHandler
        pstmt = con.prepareStatement("INSERT INTO MESSAGES('To','From','TimeStamp','Content') VALUES(?,?,?,?)");
        
    }

    public void sendMessage(String to, String from, String content) throws SQLException{
        long now = System.currentTimeMillis();
        pstmt.setString(1, to);
        pstmt.setString(2, from);
        pstmt.setTimestamp(3, new Timestamp(now));
        pstmt.setString(4, content);
        pstmt.executeUpdate(); 
    }
    
}
