


package com.example.javafx_1;
        import com.example.javafx_1.classes.User;
        import database.ConnectionDB;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;

        import java.net.URL;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

        import javafx.scene.control.Alert;
        import javafx.scene.control.Alert.AlertType;


public class UserController implements Initializable {

    ConnectionDB connection = new ConnectionDB();
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnRead;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;



    @FXML
    private TableView<User> tableView;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    public void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    @FXML
    void onSubmit(ActionEvent event) {

        connection.getConnection();
        String sql = "INSERT INTO `users`(`name`,`username`, `password`) VALUES ('"+txtName.getText()+"', '"+txtUsername.getText()+"','"+txtPassword.getText()+"')";
        try {
            connection.getConnection().createStatement().execute(sql);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Data Inserted", "User data inserted successfully.");
            load();
            clearFields();
        } catch (Exception e) {
            System.out.println(e);
            showAlert(Alert.AlertType.ERROR, "Error", "Data Not Inserted", "An error occurred while inserting user data.");
        }
    }



    @FXML
    void onDeleteUser(ActionEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
            String sql = "DELETE FROM users WHERE id = ?";
            try {
                PreparedStatement pst = connection.getConnection().prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                load();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Data Deleted", "User deleted successfully.");

            } catch (SQLException e) {
                System.out.println(e);
                showAlert(Alert.AlertType.ERROR, "Error", "Data Not Deleted", "An error occurred while deleting user data.");
            }
        } else {
            showAlert(Alert.AlertType.NONE, "None", "Data Not selected", "Data not selected");

        }
    }
    @FXML
    void onClear(ActionEvent event) {
        clearFields();
        tableView.getSelectionModel().clearSelection();
    }

    public void clearFields() {
        txtName.clear();
        txtUsername.clear();
        txtPassword.clear();
    }
    @FXML
    void onEditUser(ActionEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
            String sql = "UPDATE users SET name = ?, username = ?, password = ? WHERE id = ?";
            try {
                PreparedStatement pst = connection.getConnection().prepareStatement(sql);
                pst.setString(1, txtName.getText());
                pst.setString(2, txtUsername.getText());
                pst.setString(3, txtPassword.getText());
                pst.setInt(4, id);
                pst.executeUpdate();
                load();
                clearFields();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            showAlert(Alert.AlertType.NONE, "None", "Data Not selected", "Data not selected");
        }
    }
    @FXML
    void onRead(ActionEvent event) throws SQLException {
        load();
    }

    public void load() throws SQLException {
        // Clear the listbox
        tableView.getItems().clear();
        connection.getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement pst = connection.getConnection().prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            System.out.println(id);
            String name = res.getString("name");
            String username = res.getString("username");
            String password = res.getString("password");
            User user = new User(id, name, username, password);
            tableView.getItems().addAll(user);
        }
        // Set cell value factories for existing columns
        TableColumn<User, Integer> idColumn = (TableColumn<User, Integer>) tableView.getColumns().get(0);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> nameColumn = (TableColumn<User, String>) tableView.getColumns().get(1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> usernameColumn = (TableColumn<User, String>) tableView.getColumns().get(2);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> passwordColumn = (TableColumn<User, String>) tableView.getColumns().get(3);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            load();
            tableView.setOnMouseClicked(event -> {
                User selectedUser = tableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    txtName.setText(selectedUser.getName());
                    txtUsername.setText(selectedUser.getUsername());
                    txtPassword.setText(selectedUser.getPassword());
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
