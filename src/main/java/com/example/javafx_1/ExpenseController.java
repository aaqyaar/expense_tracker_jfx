package com.example.javafx_1;
import database.ConnectionDB;
import com.example.javafx_1.classes.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class ExpenseController implements Initializable {
    ConnectionDB conn = new ConnectionDB();
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSearch;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    public void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumns();
        loadUsers();
        tableView.setItems(userList);

        // Bind the search filter to the TableView
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> filterUsers(newValue));
    }

    private void initializeTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
    }

    private void loadUsers() {
        userList.clear();
        String sql = "SELECT * FROM users";
        try (PreparedStatement pst = conn.getConnection().prepareStatement(sql);
             ResultSet res = pst.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String username = res.getString("username");
                String password = res.getString("password");
                userList.add(new User(id, name, username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        String name = txtName.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String sql = "INSERT INTO users(name, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conn.getConnection().prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Data Inserted", "User data inserted successfully.");
            loadUsers();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Data Not Inserted", "An error occurred while inserting user data.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
            String name = txtName.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            String sql = "UPDATE users SET name = ?, username = ?, password = ? WHERE id = ?";
            try (PreparedStatement pst = conn.getConnection().prepareStatement(sql)) {
                pst.setString(1, name);
                pst.setString(2, username);
                pst.setString(3, password);
                pst.setInt(4, id);
                pst.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Success", "User Updated", "User data updated successfully.");
                loadUsers();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Data Not Updated", "An error occurred while updating user data.");
            }
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement pst = conn.getConnection().prepareStatement(sql)) {
                pst.setInt(1, id);
                pst.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Success", "User Deleted", "User data deleted successfully.");
                loadUsers();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Data Not Deleted", "An error occurred while deleting user data.");
            }
        }
    }

    @FXML
    private void onClear(ActionEvent event) {
        clearFields();
        tableView.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        txtName.clear();
        txtUsername.clear();
        txtPassword.clear();
    }
    @FXML
    void handleFilter(ActionEvent event) {
        filterUsers(txtSearch.getText());
    }
    private void filterUsers(String searchText) {
        if (searchText.isEmpty()) {
            tableView.setItems(userList);
        } else {
            ObservableList<User> filteredList = FXCollections.observableArrayList();
            for (User user : userList) {
                if (user.getName().toLowerCase().contains(searchText.toLowerCase())
                        || user.getUsername().toLowerCase().contains(searchText.toLowerCase())
                        || user.getPassword().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(user);
                }
            }
            tableView.setItems(filteredList);
        }
    }
}
