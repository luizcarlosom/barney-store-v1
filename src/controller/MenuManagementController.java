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
import javafx.stage.Stage;

public class MenuManagementController {

    @FXML
    private Button buttonNewCategory;

    @FXML
    private Button buttonNewProduct;

    @FXML
    private Button buttonViewDash;

    @FXML
    private Button buttonViewOrder;

    @FXML
    void addCategory(ActionEvent event) {
        try {
            URL orderPath = getClass().getResource("../view/AddCategoryView.fxml");
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

    @FXML
    void addProduct(ActionEvent event) {
        try {
            URL orderPath = getClass().getResource("../view/AddProductView.fxml");
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

    @FXML
    void viewDash(ActionEvent event) {
        try {
            URL orderPath = getClass().getResource("../view/ChartsView.fxml");
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

    @FXML
    void viewOrder(ActionEvent event) {
        try {
            URL orderPath = getClass().getResource("../view/ListOrderView.fxml");
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
