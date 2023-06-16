package com.example.store_management_app;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class StockPageController {

    @FXML
    private AnchorPane stockPane;
    @FXML
    private TextField inputID;
    @FXML
    private TextField inputItemName;
    @FXML
    private TextField inputQuantity;

    // Stock table
    @FXML
    private TableView<Stock> stockTable;
    @FXML
    private TableColumn<Stock, Integer> stockID;
    @FXML
    private TableColumn<Stock, String> itemName_s;
    @FXML
    private TableColumn<Stock, Integer> quantity;

    // Out of stock table
    @FXML
    private TableView<outOfStock> outOfStockTable;
    @FXML
    private TableColumn<outOfStock, Integer> outOfStockID;
    @FXML
    private TableColumn<outOfStock, String> itemName_ofs;

    @FXML
    private Button addButton;
    @FXML
    private void addButtonClicked(ActionEvent event) throws IOException {
        int stockID = Integer.parseInt(inputID.getText());
        String itemName_s = inputItemName.getText();
        int quantity = Integer.parseInt(inputQuantity.getText());

        try {
            // Insert the new order into the database
            String insertQuery = "INSERT INTO stock (stock_id, item_name, quantity) " +
                    "VALUES ('" + stockID + "', '" + itemName_s + "', '" + quantity + "')";

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);

            initialize();

            // Clear the input fields
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button deleteButton;
    @FXML
    private void deleteButtonClicked(ActionEvent event) throws IOException {
        int stockId = Integer.parseInt(inputID.getText());

        try {
            // Delete the selected order from the database
            String deleteQuery = "DELETE FROM stock WHERE stock_id = '" + stockId + "'";
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);

            initialize();

            // Clear the input fields
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button updateButton;
    @FXML
    private void updateButtonClicked(ActionEvent event) throws IOException {
        int stockID = Integer.parseInt(inputID.getText());
        String itemName_s = inputItemName.getText();
        int quantity = Integer.parseInt(inputQuantity.getText());

        try {
            // update new order to database
            String updateQuery = "UPDATE stock SET item_name = '" + itemName_s + "', quantity = '" + quantity + "' WHERE stock_id = '" + stockID +"'";
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(updateQuery);

            initialize();

            // Clear the input fields
            clearInputFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button clearButton;
    @FXML
    private void clearInputFields() throws IOException {
        inputID.clear();
        inputItemName.clear();
        inputQuantity.clear();
    }

    @FXML
    private void backBtn() throws IOException {
        AnchorPane mainpane = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        stockPane.getChildren().setAll(mainpane);
    }

    // Database connection details
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

    // Initialize method to set up the table view and columns
    public void initialize() throws Exception {

        stockTable.getItems().clear();

        outOfStockTable.getItems().clear();

        // Set up the column mappings
        stockID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStockId()).asObject());
        itemName_s.setCellValueFactory(cellData -> cellData.getValue().itemName_sProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        outOfStockID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOutOfStockIdId()).asObject());
        itemName_ofs.setCellValueFactory(cellData -> cellData.getValue().itemName_ofsProperty());


        // Connect to the database
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSetStock = statement.executeQuery("SELECT * FROM stock");
        // Load initial data into the table view
        while (resultSetStock.next()) {
            int stockID = resultSetStock.getInt("stock_id");
            String itemName_s = resultSetStock.getString("item_name");
            int quantity = resultSetStock.getInt("quantity");

            Stock stock = new Stock(stockID, itemName_s, quantity);
            stockTable.getItems().add(stock);
        }
        resultSetStock.close();

        ResultSet resultSetOutOfStock = statement.executeQuery("SELECT * FROM outofstock");
        // Add any additional initialization code here
        while (resultSetOutOfStock.next()) {
            int outOfStockID = resultSetOutOfStock.getInt("outofstock_id");
            String itemName_ofs = resultSetOutOfStock.getString("itemname");

            outOfStock Outofstock = new outOfStock(outOfStockID, itemName_ofs);
            outOfStockTable.getItems().add(Outofstock);
        }
        resultSetOutOfStock.close();

    }

//        String query ="SELECT * from stock inner join outofstock on stock.item_name = outofstock.itemname";
//        ResultSet resultSet = statement.executeQuery(query);
//
//        System.out.println("   stock_id"
//                + "    item_name"
//                + "     quantity  "
//                + "outofstock_id"
//                + "     itemname");
//
//        while (resultSet.next()) {
//            int stockID = resultSet.getInt("stock_id");
//            String itemName_s = resultSet.getString("item_name");
//            int quantity = resultSet.getInt("quantity");
//            int outOfStockID = resultSet.getInt("outofstock_id");
//            String itemName_ofs = resultSet.getString("itemname");
//
//            System.out.format(
//                    "%10s%10s%10s%10s%20s\n", stockID,
//                    itemName_s, quantity, outOfStockID, itemName_ofs);
//
//            StockPageController.Stock stock = new StockPageController.Stock(stockID, itemName_s, quantity);
//            stockTable.getItems().add(stock);
//
//            StockPageController.outOfStock outofstock = new StockPageController.outOfStock(outOfStockID, itemName_ofs);
//            outOfSockTable.getItems().add(outofstock);
//        }
//
//        // Step 7: Close the connection
//        connection.close();
//    }




    // Stock class
    public class Stock {
        private final IntegerProperty stockId;
        private final StringProperty itemName_s;
        private final IntegerProperty quantity;

        public Stock(int stockId, String itemName_s, int quantity) {
            this.stockId = new SimpleIntegerProperty(stockId);
            this.itemName_s = new SimpleStringProperty(itemName_s);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        // Getters and setters for the properties
        public int getStockId() {
            return stockId.get();
        }

        public IntegerProperty stockIdProperty() {
            return stockId;
        }

        public void setStockId(int stockId) {
            this.stockId.set(stockId);
        }

        public String getItemName_s() {
            return itemName_s.get();
        }

        public StringProperty itemName_sProperty() {
            return itemName_s;
        }

        public void setItemName_s(String itemName_s) {
            this.itemName_s.set(itemName_s);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }
    }

    // Out of stock class
    public class outOfStock {
        private final IntegerProperty outOfStockId;
        private final StringProperty itemName_ofs;

        public outOfStock(int outOfStockId, String itemName_ofs) {
            this.outOfStockId = new SimpleIntegerProperty(outOfStockId);
            this.itemName_ofs = new SimpleStringProperty(itemName_ofs);
        }

        // Getters and setters for the properties
        public int getOutOfStockIdId() {
            return outOfStockId.get();
        }

        public IntegerProperty outOfStockIdProperty() {
            return outOfStockId;
        }

        public void setOutOfStockId(int outOfStockId) {
            this.outOfStockId.set(outOfStockId);
        }

        public String itemName_ofs() {
            return itemName_ofs.get();
        }

        public StringProperty itemName_ofsProperty() {
            return itemName_ofs;
        }

        public void setItemName_ofs(String itemName_ofs) {
            this.itemName_ofs.set(itemName_ofs);
        }
    }

}
