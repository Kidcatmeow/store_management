package com.example.store_management_app;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.*;

public class StaffController {

    @FXML
    private TextField inputID;

    @FXML
    private TextField inputFirstName;

    @FXML
    private TextField inputLastName;

    @FXML
    private TextField inputGender;

    @FXML
    private TextField inputPhone;

    @FXML
    private TextField inputAddress;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputBirthdate;

    @FXML
    private Button addEmployeeBtn;

    @FXML
    private Button deleteEmployeeBtn;

    @FXML
    private Button updateEmployeeBtn;

    @FXML
    private Button clearFieldBtn;

    @FXML
    private TableView<DataRow> employeeTableView;

    @FXML
    private TableColumn<DataRow, String> idColumn;

    @FXML
    private TableColumn<DataRow, String> fnameColumn;

    @FXML
    private TableColumn<DataRow, String> lnameColumn;

    @FXML
    private TableColumn<DataRow, String> genderColumn;

    @FXML
    private TableColumn<DataRow, String> phoneColumn;

    @FXML
    private TableColumn<DataRow, String> addressColumn;

    @FXML
    private TableColumn<DataRow, String> emailColumn;

    @FXML
    private TableColumn<DataRow, String> bdateColumn;

    public static final String DB_URL = "jdbc:mysql://localhost:1234/storemanagement";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1";
    public Connection dbConn = null;


    public Connection getConnection() throws Exception {

        //if already connected --> simply return the old connection
        if (dbConn != null) {
            return dbConn;
        }

        //else connect
        dbConn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        return dbConn;
    }

    @FXML
    public void initialize() throws Exception {
        employeeTableView.getItems().clear();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        fnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFname());
        lnameColumn.setCellValueFactory(cellData -> cellData.getValue().getLname());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().getGender());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhone());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        bdateColumn.setCellValueFactory(cellData -> cellData.getValue().getBdate());


        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT * FROM employee");

        while (resultset.next()) {
            String id = resultset.getString("employee_id");
            String fname = resultset.getString("employee_fname");
            String lname = resultset.getString("employee_lname");
            String gender = resultset.getString("gender");
            String phone = resultset.getString("phone");
            String address = resultset.getString("address");
            String bdate = resultset.getString("birthdate");
            String email = resultset.getString("email");

            DataRow dataRow = new DataRow(id, fname, lname, gender, phone, address, email, bdate);

            employeeTableView.getItems().add(dataRow);
        }
    }

    @FXML
    public void addEmployee() throws Exception {
        String id = inputID.getText();
        String fname = inputFirstName.getText();
        String lname = inputLastName.getText();
        String gender = inputGender.getText();
        String phone = inputPhone.getText();
        String address = inputAddress.getText();
        String birthdate = inputBirthdate.getText();
        String email = inputEmail.getText();

        Connection connection = getConnection();

        String query = "INSERT INTO employee (employee_id, employee_fname, employee_lname, gender, phone, address, email, birthdate) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, fname);
        statement.setString(3, lname);
        statement.setString(4, gender);
        statement.setString(5, phone);
        statement.setString(6, address);
        statement.setString(7, email);
        statement.setString(8, birthdate);

        statement.execute();

        initialize();
    }

    @FXML
    public void DeleteEmployee() throws Exception {
        String id = inputID.getText();

        Connection connection = getConnection();

        String query = "DELETE FROM employee WHERE employee_id = '" + id + "'";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.execute();

        initialize();
    }

    @FXML
    public void UpdateEmployee() throws Exception {
        String id = inputID.getText();
        String fname = inputFirstName.getText();
        String lname = inputLastName.getText();
        String gender = inputGender.getText();
        String phone = inputPhone.getText();
        String address = inputAddress.getText();
        String birthdate = inputBirthdate.getText();
        String email = inputEmail.getText();

        Connection connection = getConnection();

        String query = "UPDATE employee SET employee_id = '" + id + "', employee_fname = '" + fname + "', employee_lname = '" + lname + "', gender = '" + gender + "', phone = '" + phone +
                "', address = '" + address + "', birthdate = '" + birthdate + "', email = '" + email + "' WHERE employee_id = '" + id +"'";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.execute();

        initialize();
    }

    @FXML
    private void clearInputFields() {
        inputID.clear();
        inputFirstName.clear();
        inputLastName.clear();
        inputGender.clear();
        inputAddress.clear();
        inputPhone.clear();
        inputEmail.clear();
        inputBirthdate.clear();
    }

    private static class DataRow {
        private final SimpleStringProperty id;
        private final SimpleStringProperty fname;
        private final SimpleStringProperty lname;
        private final SimpleStringProperty gender;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty address;
        private final SimpleStringProperty email;
        private final SimpleStringProperty bdate;


        public DataRow(
                String id,
                String fname,
                String lname,
                String gender,
                String phone,
                String address,
                String email,
                String bdate
        ) {
            this.id = new SimpleStringProperty(id);
            this.fname = new SimpleStringProperty(fname);
            this.lname = new SimpleStringProperty(lname);
            this.gender = new SimpleStringProperty(gender);
            this.phone = new SimpleStringProperty(phone);
            this.address = new SimpleStringProperty(address);
            this.email = new SimpleStringProperty(email);
            this.bdate = new SimpleStringProperty(bdate);
        }

        public SimpleStringProperty getId() {
            return id;
        }

        public SimpleStringProperty getFname() {
            return fname;
        }

        public SimpleStringProperty getLname() {
            return lname;
        }

        public SimpleStringProperty getGender() {
            return gender;
        }

        public SimpleStringProperty getPhone() {
            return phone;
        }

        public SimpleStringProperty getAddress() {
            return address;
        }

        public SimpleStringProperty getEmail() {
            return email;
        }

        public SimpleStringProperty getBdate() {
            return bdate;
        }
    }
}
