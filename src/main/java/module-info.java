module com.example.javafx_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafx_1 to javafx.fxml;
    exports com.example.javafx_1;
    exports com.example.javafx_1.classes;
    opens com.example.javafx_1.classes to javafx.fxml;
}