package com.example.store_management_app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.*;


public class OrdersController {

    @FXML
    AnchorPane ordersPane;

    @FXML
    private TextField inputorder;

    @FXML
    private TextField inputcustomer;

    @FXML
    private DatePicker inputdate;

    @FXML
    private ComboBox<String> inputstatus;

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

    public static final String DB_URL = "jdbc:mysql://localhost:1234/storemanagement";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1";
    public Connection dbConn = null;


    // Method to connect to the database
    //connect to database only for the firstime
    public Connection getConnection() throws Exception {

        //if already connected --> simply return the old connection
        if (dbConn != null) {
            return dbConn;
        }

        //else connect
        dbConn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        return dbConn;
    }

    // Initialize method to set up the table view and columns
    public void initialize() throws Exception {

        orderTableView.getItems().clear();


        ObservableList<String> statusChoices = FXCollections.observableArrayList("delivered", "not delivered");
        inputstatus.setItems(statusChoices);

        // Set up the column mappings
        orderIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderId()).asObject());
        customerIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerId()).asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Connect to the database
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");

        // Load initial data into the table view
        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            int customerId = resultSet.getInt("customer_id");
            String date = resultSet.getString("date");
            String status = resultSet.getString("status");

            Order order = new Order(orderId, customerId, date, status);
            orderTableView.getItems().add(order);
        }

    }


    // Method to load order data from the database into the table view
//    private void loadOrderData() {
//        try {
//            String query = "SELECT * FROM orders";
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                int orderId = resultSet.getInt("order_id");
//                int customerId = resultSet.getInt("customer_id");
//                String date = resultSet.getString("date");
//                String status = resultSet.getString("status");
//
//
//                Order order = new Order(orderId, customerId, date, status);
//                System.out.println(order.status);
//                orderTableView.getItems().add(order);
//            }
//
//            resultSet.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // Method to handle the add button action
    @FXML
    private void handleAddButton(ActionEvent event) {
        int orderId = Integer.parseInt(inputorder.getText());
        int customerId = Integer.parseInt(inputcustomer.getText());
        String date = inputdate.getValue().toString();
        String status = inputstatus.getValue();

        try {
            // Insert the new order into the database
            String insertQuery = "INSERT INTO orders (order_id, customer_id, date, status) " +
                    "VALUES ('" + orderId + "', '" + customerId + "', '" + date + "', '" + status  +  "')";

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);

            initialize();


//            // Create a new Order object with the provided data
//            Order newOrder = new Order(orderId, customerId, date, status);
//
//            // Add the new order to the table view
//            orderTableView.getItems().add(newOrder);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to handle the delete button action
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        int orderId = Integer.parseInt(inputorder.getText());

        try {
            // Delete the selected order from the database
            String deleteQuery = "DELETE FROM orders WHERE order_id = '" + orderId + "'";
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);

            initialize();


            // Remove the selected order from the table view
            //For .remove(Int)
//            orderTableView.getItems().remove(orderId-1);

            //For .remove(object))
//            Order selectedOrder = null;
//            for (Order order : orderTableView.getItems()) {
//                System.out.println(order.getOrderId());
//                if (order.getOrderId() == orderId) {
//                    selectedOrder = order;
//                    break;
//                }
//            }
//            if (selectedOrder != null) {
//                orderTableView.getItems().remove(selectedOrder);
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to handle the update button action
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        int orderId = Integer.parseInt(inputorder.getText());
        int customerId = Integer.parseInt(inputcustomer.getText());
        String date = inputdate.getValue().toString();
        String status = inputstatus.getValue();


        try {
            // update new order to database
            String updateQuery = "UPDATE orders SET customer_id = '" + customerId + "', date = '" + date + "', status = '" + status + "' WHERE order_id = '" + orderId +"'";
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(updateQuery);

            initialize();
//            for (Order order : orderTableView.getItems()){
//                if(order.getOrderId()==orderId){
//                    order.setCustomerId(customerId);
//                    order.setDate(date);
//                    order.setStatus(status);
//                    break;
//                }
//            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to clear the input fields
    @FXML
    private void clearInputFields() {
        inputorder.clear();
        inputcustomer.clear();
        inputdate.setValue(null);
        inputstatus.getSelectionModel().clearSelection();
    }

    @FXML
    private void backBtn() throws IOException {
        AnchorPane mainpane = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        ordersPane.getChildren().setAll(mainpane);
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
