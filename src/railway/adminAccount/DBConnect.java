package railway.adminAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DBConnect {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/rrs";
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,"root","");
        } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Unable To Connect To The Database Server");
        }
        return conn;
    }
   }