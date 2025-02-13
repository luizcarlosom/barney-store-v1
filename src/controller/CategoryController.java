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
import model.Category;

public class CategoryController {

    @FXML
    private Button backButton;

    @FXML
    private Button buttonAddCategory;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField nameField;

    // Método para adicionar categoria
    @FXML
    void addCategory(ActionEvent event) {
        String name = nameField.getText();
        String description = categoryField.getText();

        if (name.isEmpty() || description.isEmpty()) {
            showAlert(AlertType.WARNING, "Campos de nome ou descrição estão vazios!");
            return;
        }

        int id = generateNewCategoryId();

        Category newCategory = new Category(id, name, description);

        saveCategoryToCSV(newCategory);
    }

    // Método para voltar para a página anterior
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

    // Método para gerar um novo ID
    private int generateNewCategoryId() {
        // Lógica para gerar um novo ID (isso pode ser melhorado, por exemplo, lendo o último ID do CSV)
        return 3; // Exemplo de ID fixo
    }

    // Método para salvar a categoria no arquivo CSV
    private void saveCategoryToCSV(Category category) {
        URL pathDatabase = getClass().getResource("../database/Category.csv");

        if (pathDatabase == null) {
            showAlert(AlertType.ERROR, "Arquivo Category.csv não encontrado!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathDatabase.getPath(), true))) {
            writer.write(category.toString());
            writer.newLine();
            showAlert(AlertType.INFORMATION, "Categoria adicionada com sucesso!");
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erro ao salvar a categoria no arquivo!");
            e.printStackTrace();
        }
    }

    // Método para exibir alertas
    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == AlertType.ERROR ? "Erro" : "Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
