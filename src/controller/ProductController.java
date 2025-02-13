package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Product;

public class ProductController {

    @FXML
    private Button buttonAddProduct;

    @FXML
    private Button buttonBack;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField valueField;

    @FXML
    void addProduct(ActionEvent event) {
        String name = nameField.getText();
        String category = categoryField.getText();
        String quantityText = quantityField.getText();
        String valueText = valueField.getText();

        if (name.isEmpty() || category.isEmpty() || quantityText.isEmpty() || valueText.isEmpty()) {
            showAlert(AlertType.WARNING, "Todos os campos devem ser preenchidos!");
            return;
        }

        int quantity;
        double value;
        try {
            quantity = Integer.parseInt(quantityText);
            value = Double.parseDouble(valueText);
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Quantidade e valor devem ser números válidos!");
            return;
        }

        Product newProduct = new Product(name, category, quantity, value);
        saveProductToCSV(newProduct);
    }

    @FXML
    void backPage(ActionEvent event) {
        try {
            URL orderPath = getClass().getResource("../view/MenuManagementView.fxml");
            FXMLLoader loader = new FXMLLoader(orderPath);
            Parent newRoot = loader.load();

            Node sourceNode = (Node) event.getSource();

            Scene currentScene = sourceNode.getScene();

            Stage stage = (Stage) currentScene.getWindow();

            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro ao carregar a página de menu!");
        }
    }

    private void saveProductToCSV(Product product) {
        URL pathDatabase = getClass().getResource("../database/Product.csv");
    
        if (pathDatabase == null) {
            showAlert(AlertType.ERROR, "Arquivo Product.csv não encontrado!");
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathDatabase.getPath(), true))) {
            writer.write(product.toString());
            writer.newLine();
            showAlert(AlertType.INFORMATION, "Produto adicionado com sucesso!");
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro ao salvar o produto no arquivo!");
            e.printStackTrace();
        }
    }
    
    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == AlertType.ERROR ? "Erro" : "Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
