package controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChartsController {

    @FXML
    private Button backButton;

    @FXML
    private LineChart<?, ?> billingChart;

    @FXML
    private BarChart<?, ?> inventoryControlChart;

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
