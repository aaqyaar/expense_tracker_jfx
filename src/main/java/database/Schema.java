package database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Schema {
    ConnectionDB conn = new ConnectionDB();

    public Schema(String type) {
        if (type.equals("createDB")) {
            createDatabase();
            initializeUser();
            initializeExpenses();
            initializeIncome();
        }
    }

    private void createDatabase() {
        try {
            Connection connection = conn.getConnectionWithoutDB();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS expense_tracker");
        } catch (SQLException ex) {
            Logger.getLogger(Schema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeUser() {
        try {
            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("USE expense_tracker");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "            `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "            `name` varchar(255) NOT NULL,\n" +
                    "            `username` varchar(255) NOT NULL,\n" +
                    "            `password` varchar(255) NOT NULL,\n" +
                    "    PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        } catch (SQLException ex) {
            Logger.getLogger(Schema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initializeExpenses() {
        try {
            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("USE expense_tracker");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `expenses` (\n" +
                    "    `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "    `user_id` int(11) NOT NULL,\n" +
                    "    `type` int(11) NOT NULL,\n" +
                    "    `amount` int(11) NOT NULL,\n" +
                    "    `date_spent` varchar(255) NOT NULL,\n" +
                    "    `description` varchar(255) NOT NULL,\n" +
                    "    PRIMARY KEY (`id`),\n" +
                    "    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        } catch (SQLException ex) {
            Logger.getLogger(Schema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeIncome() {
        try {
            Connection connection = conn.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("USE expense_tracker");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `income` (\n" +
                    "    `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "    `user_id` int(11) NOT NULL,\n" +
                    "    `amount` int(11) NOT NULL,\n" +
                    "    `date_received` varchar(255) NOT NULL,\n" +
                    "    `description` varchar(255) NOT NULL,\n" +
                    "    PRIMARY KEY (`id`),\n" +
                    "    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        } catch (SQLException ex) {
            Logger.getLogger(Schema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
