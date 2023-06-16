package com.example.store_management_app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;


public class SalesPageController {

    @FXML
    AnchorPane totalsalesPane;

    // Database connection details
    public static final String DB_URL = "jdbc:mysql://localhost:1234/storemanagement";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1";

    @FXML
    private TableView<DataRow> totalsalesTable;

    @FXML
    private TableColumn<DataRow, Integer> idColumn;

    @FXML
    private TableColumn<DataRow, String> nameColumn;

    @FXML
    private TableColumn<DataRow, Double> priceColumn;

    @FXML
    private TableColumn<DataRow, Integer> quantityColumn;

    @FXML
    private TableColumn<DataRow, Double> totalsalesColumn;


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getID().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
        totalsalesColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalSales().asObject());

        // Retrieve data from the database view and populate the table
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM item_sales_view")) {


            while (resultSet.next()) {
                int id = resultSet.getInt("stock_id");
                String name = resultSet.getString("item_name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double totalSales = resultSet.getDouble("total_sales");

                DataRow dataRow = new DataRow(id, name, price, quantity, totalSales);
//                System.out.println(dataRow.name);
                totalsalesTable.getItems().add(dataRow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }

    @FXML
    private void backBtn() throws IOException {
        AnchorPane mainpane = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        totalsalesPane.getChildren().setAll(mainpane);
    }

    // A simple data model class representing a row in the view
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



