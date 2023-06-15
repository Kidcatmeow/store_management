package com.example.store_management_app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class StockPageController {
    // Database connection details
    public static final String DB_URL = "jdbc:mysql://localhost:1234/storemanagement";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1";

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
    private TableView<SalesPageController.DataRow> stockTable;
    @FXML
    private TableColumn<SalesPageController.DataRow, Integer> stockID;
    @FXML
    private TableColumn<SalesPageController.DataRow, String> itemName_s;
    @FXML
    private TableColumn<SalesPageController.DataRow, Integer> quantity;

    // Out of stock table
    @FXML
    private TableView<SalesPageController.DataRow> outOfSockTable;
    @FXML
    private TableColumn<SalesPageController.DataRow, Integer> outOfStockID;
    @FXML
    private TableColumn<SalesPageController.DataRow, String> itemName_ofs;

    @FXML
    private Button addButton;
    @FXML
    public void addButtonClicked() throws IOException {
    }

    @FXML
    private Button deleteButton;
    @FXML
    public void deleteButtonClicked() throws IOException {
    }

    @FXML
    private Button updateButton;
    @FXML
    public void updateButtonClicked() throws IOException {
    }

    @FXML
    private Button clearButton;
    @FXML
    public void clearButtonClicked() throws IOException {
    }

    private Connection connection;
    private Statement statement;

    public void initialize() {
        stockID.setCellValueFactory(cellData -> cellData.getValue().getID().asObject());
        itemName_s.setCellValueFactory(cellData -> cellData.getValue().getName());
        quantity.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        outOfStockID.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        itemName_ofs.setCellValueFactory(cellData -> cellData.getValue().getTotalSales().asObject());

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM stock")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("stock_id");
                String name = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double totalSales = resultSet.getDouble("total_sales");

                SalesPageController.DataRow dataRow = new SalesPageController.DataRow(id, name, price, quantity, totalSales);
//                System.out.println(dataRow.name);
                totalsalesTable.getItems().add(dataRow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }

    public static class DataRow {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty quantity;
        private final SimpleDoubleProperty totalSales;

        public DataRow(int id, String name, double price, int quantity, double totalSales) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.totalSales = new SimpleDoubleProperty(totalSales);
        }

        public SimpleIntegerProperty getID() {
            return  this.id;
        }
        public SimpleStringProperty getName() {
            return  this.name;
        }
        public SimpleDoubleProperty getPrice() {
            return price;
        }
        public SimpleIntegerProperty getQuantity() {
            return quantity;
        }
        public SimpleDoubleProperty getTotalSales() {
            return totalSales;
        }
    }

}
