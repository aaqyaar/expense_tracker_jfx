package com.example.javafx_1;

import com.example.javafx_1.LoaderController;
import com.example.javafx_1.classes.User;
import database.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HelloController {

    @FXML
    private Pane centerPane;

    @FXML
    private Pane sidePane;

    @FXML
    private Button userPageBtn;

    @FXML
    void handleDashboard(ActionEvent event) {
    }

    @FXML
    void handleUsers(ActionEvent event) {
        LoaderController object = new LoaderController();
        Pane view = object.getPage("users");
        centerPane.getChildren().clear();
        centerPane.getChildren().addAll(view);
    }
    @FXML
    void handleExpenses(ActionEvent event) {
        LoaderController object = new LoaderController();
        Pane view = object.getPage("expenses");
        centerPane.getChildren().clear();
        centerPane.getChildren().addAll(view);
    }
}