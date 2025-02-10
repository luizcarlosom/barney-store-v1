package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.List;

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

    @FXML
    private Button finishedOrderButton;

    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

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

        categoryComboBox.setOnAction(event -> filterProductsByCategory());
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
                    allProducts.add(product);
                }
            }
            productsTable.setItems(allProducts);
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void filterProductsByCategory() {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory == null || selectedCategory.isEmpty()) {
            productsTable.setItems(allProducts);
        } else {
            ObservableList<Product> filteredProducts = allProducts.stream()
                .filter(product -> product.categoryProperty().get().equals(selectedCategory))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
            productsTable.setItems(filteredProducts);
        }
    }

    @FXML
    void finishedOrder(ActionEvent event) {
        ObservableList<Product> selectedProducts = allProducts.stream()
            .filter(product -> product.desiredQuantityProperty().get() > 0)
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (selectedProducts.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Carrinho Vazio");
            alert.setHeaderText(null);
            alert.setContentText("O carrinho está vazio! Adicione produtos antes de finalizar o pedido.");
            alert.showAndWait();
        } else {
            double totalPrice = 0.0;
            StringBuilder orderDetails = new StringBuilder();
            orderDetails.append("Itens selecionados:\n\n");

            for (Product product : selectedProducts) {
                int newQuantity = product.quantityProperty().get() - product.desiredQuantityProperty().get();

                if (newQuantity < 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Estoque Insuficiente");
                    alert.setHeaderText(null);
                    alert.setContentText("Estoque insuficiente para o produto: " + product.nameProperty().get());
                    alert.showAndWait();
                    return;
                }

                product.setQuantity(newQuantity);
                double productTotal = product.desiredQuantityProperty().get() * product.valueProperty().get();
                totalPrice += productTotal;

                orderDetails.append("- ")
                            .append(product.nameProperty().get())
                            .append(" | Quantidade: ").append(product.desiredQuantityProperty().get())
                            .append(" | Unitário: R$ ").append(String.format("%.2f", product.valueProperty().get()))
                            .append(" | Total: R$ ").append(String.format("%.2f", productTotal))
                            .append("\n");
            }

            orderDetails.append("\nPreço total do pedido: R$ ")
                        .append(String.format("%.2f", totalPrice));

            updateStockCSV(allProducts);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Resumo do Pedido");
            alert.setHeaderText("Pedido Finalizado com Sucesso!");
            alert.setContentText(orderDetails.toString());
            alert.showAndWait();
        }
    }

    private void updateStockCSV(List<Product> products) {
        try {
            URL pathDatabase = getClass().getResource("../database/Product.csv");
            if (pathDatabase == null) {
                System.out.println("Arquivo Product.csv não encontrado!");
                return;
            }
    
            File file = new File(pathDatabase.toURI());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    
            writer.write("ID,Name,Quantity,Value,Category");
            writer.newLine();
    
            for (Product product : products) {
                writer.write(product.nameProperty().get() + "," +
                             product.quantityProperty().get() + "," +
                             product.valueProperty().get() + "," +
                             product.categoryProperty().get());
                writer.newLine();
            }
    
            writer.close();
            System.out.println("Estoque atualizado com sucesso!");
    
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }    

}
