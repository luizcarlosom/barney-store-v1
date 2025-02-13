package controller;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagementController {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button signin;

    @FXML
    void SignInManagementView(ActionEvent event) {
        String username = login.getText();
        String passwordText = password.getText();

        if ("admin".equals(username) && "123456".equals(passwordText)) {
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
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText(null);
            alert.setContentText("Login ou senha incorretos. Tente novamente.");
            alert.showAndWait();
        }
    }

}
