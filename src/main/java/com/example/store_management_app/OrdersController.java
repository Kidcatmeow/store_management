package com.example.store_management_app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.value.ObservableValue;
import java.sql.*;


public class OrdersController {

    @FXML
    private TextField inputorder;

    @FXML
    private TextField inputcustomer;

    @FXML
    private TextField inputdate;

    @FXML
    private TextField inputstatus;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, Integer> customerIdColumn;

    @FXML
    private TableColumn<Order, String> dateColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;



    private Connection connection;
    private Statement statement;

    // Initialize method to set up the table view and columns
    public void initialize() {
        // Set up the column mappings
        orderIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderId()).asObject());
        customerIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerId()).asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Connect to the database
        connectToDatabase();

        // Load initial data into the table view
        loadOrderData();

        // Add any additional initialization code here
    }

    // Method to connect to the database
    private void connectToDatabase() {
        try {
            // Provide your database connection details
            String url = "jdbc:mysql://localhost:1234/storemanagement";
            String username = "root";
            String password = "1";

            // Establish the database connection
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load order data from the database into the table view
    private void loadOrderData() {
        try {
            String query = "SELECT * FROM orders";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                String date = resultSet.getString("date");
                String status = resultSet.getString("status");


                Order order = new Order(orderId, customerId, date, status);
                System.out.println(order.status);
                orderTableView.getItems().add(order);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle the add button action
    @FXML
    private void handleAddButton(ActionEvent event) {
        int orderId = Integer.parseInt(inputorder.getText());
        int customerId = Integer.parseInt(inputcustomer.getText());
        String date = inputdate.getText();
        String status = inputstatus.getText();

        try {
            // Insert the new order into the database
            String insertQuery = "INSERT INTO orders (order_id, customer_id, date, status) " +
                    "VALUES ('" + orderId + "', '" + customerId + "', '" + date + "', '" + status  +  "')";

            statement.executeUpdate(insertQuery);

            // Create a new Order object with the provided data
            Order newOrder = new Order(orderId, customerId, date, status);

            // Add the new order to the table view
            orderTableView.getItems().add(newOrder);

            // Clear the input fields
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle the delete button action
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        int orderId = Integer.parseInt(inputorder.getText());

        try {
            // Delete the selected order from the database
            String deleteQuery = "DELETE FROM orders WHERE order_id = '" + orderId + "'";
            statement.executeUpdate(deleteQuery);

            // Remove the selected order from the table view
            //For .remove(Int)
//            orderTableView.getItems().remove(orderId-1);

            //For .remove(object))
            Order selectedOrder = null;
            for (Order order : orderTableView.getItems()) {
                System.out.println(order.getOrderId());
                if (order.getOrderId() == orderId) {
                    selectedOrder = order;
                    break;
                }
            }
            if (selectedOrder != null) {
                orderTableView.getItems().remove(selectedOrder);
            }

            // Clear the input fields
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle the update button action
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        int orderId = Integer.parseInt(inputorder.getText());
        int customerId = Integer.parseInt(inputcustomer.getText());
        String date = inputdate.getText();
        String status = inputstatus.getText();

        try {
            // update new order to database
            String updateQuery = "UPDATE orders SET customer_id = '" + customerId + "', date = '" + date + "', status = '" + status + "' WHERE order_id = '" + orderId +"'";
            statement.executeUpdate(updateQuery);

            for (Order order : orderTableView.getItems()){
                if(order.getOrderId()==orderId){
                    order.setCustomerId(customerId);
                    order.setDate(date);
                    order.setStatus(status);
                    break;
                }
            }

            // Clear the input fields
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to clear the input fields
    @FXML
    private void clearInputFields() {
        inputorder.clear();
        inputcustomer.clear();
        inputdate.clear();
        inputstatus.clear();
    }
    public class Order {
        private final IntegerProperty orderId;
        private final IntegerProperty customerId;
        private final StringProperty date;
        private final StringProperty status;

        public Order(int orderId, int customerId, String date, String status) {
            this.orderId = new SimpleIntegerProperty(orderId);
            this.customerId = new SimpleIntegerProperty(customerId);
            this.date = new SimpleStringProperty(date);
            this.status = new SimpleStringProperty(status);
        }

        // Getters and setters for the properties
        public int getOrderId() {
            return orderId.get();
        }

        public IntegerProperty orderIdProperty() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId.set(orderId);
        }

        public int getCustomerId() {
            return customerId.get();
        }

        public IntegerProperty customerIdProperty() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId.set(customerId);
        }

        public String getDate() {
            return date.get();
        }

        public StringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getStatus() {
            return status.get();
        }

        public StringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

    }

}
