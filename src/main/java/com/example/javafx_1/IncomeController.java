package com.example.javafx_1;

import com.example.javafx_1.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class IncomeController {

    @FXML
    private Button btnPassword;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUsername;

    @FXML
    void handleCreate(ActionEvent event) {

    }

    @FXML
    void handleDelete(ActionEvent event) {

    }

    @FXML
    void handleFilter(ActionEvent event) {

    }

    @FXML
    void handleUpdate(ActionEvent event) {

    }

}
