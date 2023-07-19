package com.example.javafx_1;
import com.example.javafx_1.classes.CustomAlert;
import com.example.javafx_1.classes.Income;
import database.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginController {
    ConnectionDB conn = new ConnectionDB();

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            CustomAlert.showAlert(Alert.AlertType.ERROR, "Error", "Empty Fields", "Please fill all the fields");
            return;
        }
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            if (preparedStatement.executeQuery().next()) {
                CustomAlert.showAlert(Alert.AlertType.INFORMATION, "Success", "Login Successful", "Welcome " + username);
                Pane root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Scene scene  = new Scene(root);
                Stage stage  = new Stage();
                stage.setScene(scene);
                stage.setTitle("Dashboard");
//                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();

            } else {
                CustomAlert.showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "Invalid username or password");
            }
        } catch (SQLException e) {
            System.out.println(e);
            CustomAlert.showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "An error occurred while logging in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
