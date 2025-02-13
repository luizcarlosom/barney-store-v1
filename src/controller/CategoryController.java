package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryController {

    @FXML
    private Button backButton;

    @FXML
    private Button buttonAddProduct;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField nameField;

    @FXML
    void addProduct(ActionEvent event) {

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
            e.printStackTrace();
        }
    }

}
