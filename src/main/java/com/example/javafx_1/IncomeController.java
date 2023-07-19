package com.example.javafx_1;
import com.example.javafx_1.classes.CustomAlert;
import com.example.javafx_1.classes.User;
import database.ConnectionDB;
import com.example.javafx_1.classes.Income;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class IncomeController implements Initializable {
    ConnectionDB conn = new ConnectionDB();
    @FXML
    private Button btnPassword;

    @FXML
    private ComboBox<String> cmbUser;

    @FXML
    private TableView<Income> tableView;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadIncome();
            getUsers();
            tableView.setOnMouseClicked(event -> {
                Income selectedUser = tableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    cmbUser.setValue(selectedUser.getUser());
                    txtAmount.setText(selectedUser.getAmount());
                    txtDescription.setText(selectedUser.getDescription());
                    txtDate.setValue(LocalDate.parse(selectedUser.getDate()));
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//
//
    public void loadIncome() throws SQLException {
        tableView.getItems().clear();
        conn.getConnection();
        String sql = "SELECT * FROM income INNER JOIN users ON income.user_id = users.id";
        PreparedStatement pst = conn.getConnection().prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            String user_id = res.getString("name");
            String amount = res.getString("amount");
            String dateReceived = res.getString("date_received");
            String description = res.getString("description");
            Income income = new Income(id, user_id, amount, description, dateReceived);
            tableView.getItems().addAll(income);
        }
        initializeColumns();
    }

    public void initializeColumns() {
        // Set cell value factories for existing columns
        TableColumn<Income, Integer> idColumn = (TableColumn<Income, Integer>) tableView.getColumns().get(0);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Income, String> nameColumn = (TableColumn<Income, String>) tableView.getColumns().get(1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableColumn<Income, String> amountColumn = (TableColumn<Income, String>) tableView.getColumns().get(2);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Income, String> descriptionColumn = (TableColumn<Income, String>) tableView.getColumns().get(3);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Income, String> dateColumn = (TableColumn<Income, String>) tableView.getColumns().get(4);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    public void getUsers() throws SQLException {
        cmbUser.getItems().clear();
        conn.getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement pst = conn.getConnection().prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            System.out.println(res.getString("name"));
            int id = res.getInt("id");
            String name = res.getString("name");
            cmbUser.getItems().add(String.valueOf(name));
        }
    }

    @FXML
    void handleCreate(ActionEvent event) {
        String _id = getUserByName();
        conn.getConnection();
        System.out.println(_id);
        String sql = "INSERT INTO income (user_id, amount, description, date_received) VALUES ('"+_id + "', '" + txtAmount.getText() + "', '" + txtDescription.getText() + "', '" + txtDate.getValue() + "')";
        try {
            conn.getConnection().createStatement().execute(sql);
            CustomAlert.showAlert(Alert.AlertType.INFORMATION, "Success", "Data Inserted", "User data inserted successfully.");
            loadIncome();
        } catch (Exception e) {
            System.out.println(e);
            CustomAlert.showAlert(Alert.AlertType.ERROR, "Error", "Data Not Inserted", "An error occurred while inserting user data.");
        }
    }


    @FXML
    void handleDelete(ActionEvent event) {

    }

    @FXML
    void handleFilter(KeyEvent event) throws SQLException {
        tableView.getItems().clear();
        conn.getConnection();
        String sql = "SELECT * FROM income INNER JOIN users ON income.user_id = users.id WHERE users.name LIKE '%" + txtSearch.getText() + "%'" + " OR income.amount LIKE '%" + txtSearch.getText() + "%'" + " OR income.description LIKE '%" + txtSearch.getText() + "%'" + " OR income.date_received LIKE '%" + txtSearch.getText() + "%'";
        PreparedStatement pst = conn.getConnection().prepareStatement(sql);
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            String user_id = res.getString("name");
            String amount = res.getString("amount");
            String dateReceived = res.getString("date_received");
            String description = res.getString("description");
            Income income = new Income(id, user_id, amount, description, dateReceived);
            tableView.getItems().addAll(income);
        }
        initializeColumns();
    }

    @FXML
    void handleUpdate(ActionEvent event) {

    }

    @FXML
    void chooseUser(ActionEvent event) {
        getUserByName();
    }


    public String getUserByName() {
        Income income = new Income();
        String selectedUser = cmbUser.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            // No user is selected, handle this case if necessary
            return "";
        }
        conn.getConnection();
        String sql = "SELECT * FROM users where name = ?";
        try {
            PreparedStatement pst = conn.getConnection().prepareStatement(sql);
            pst.setString(1, selectedUser);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                income.setUser_id(String.valueOf(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return income.getUser_id();
    }
}
