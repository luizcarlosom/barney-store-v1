package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable { 

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TableView<Product> productsTable;
    
    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TableColumn<Product, Double> valueColumn;

    @FXML
    private TableColumn<Product, Integer> desiredQuantity;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCategories();
        loadProducts();

        productsTable.getColumns().forEach(column -> column.setReorderable(false));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty().asObject());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        desiredQuantity.setCellValueFactory(cellData -> cellData.getValue().desiredQuantityProperty().asObject());
    
        desiredQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        desiredQuantity.setEditable(true);

        desiredQuantity.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setDesiredQuantity(event.getNewValue());
        });
    }

    private void loadCategories() {
        try {
            URL pathDatabase = getClass().getResource("../database/Category.csv");
    
            if (pathDatabase == null) {
                System.out.println("Arquivo Category.csv não encontrado! Verifique o caminho.");
                return;
            }
    
            BufferedReader br = new BufferedReader(new FileReader(pathDatabase.toURI().getPath()));
    
            br.readLine();
    
            String line;
            while ((line = br.readLine()) != null) { 
                String[] values = line.split(",");
                if (values.length > 1) {
                    categoryComboBox.getItems().add(values[1]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        try {
            URL pathDatabase = getClass().getResource("../database/Product.csv");
    
            if (pathDatabase == null) {
                System.out.println("Arquivo Product.csv não encontrado! Verifique o caminho.");
                return;
            }
    
            BufferedReader br = new BufferedReader(new FileReader(pathDatabase.toURI().getPath()));

            br.readLine();
    
            String line;
            while ((line = br.readLine()) != null) { 
                String[] values = line.split(",");
                if (values.length > 3) {
                    Product product = new Product(
                        values[0], 
                        values[1],
                        Integer.parseInt(values[2]),
                        Double.parseDouble(values[3])
                    );
                    productsTable.getItems().add(product);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
