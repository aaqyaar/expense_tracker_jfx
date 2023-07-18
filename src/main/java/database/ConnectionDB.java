package database;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionDB {
    public Connection getConnection() {
        String dbName = "expense_tracker";
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public Connection getConnectionWithoutDB() {
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
