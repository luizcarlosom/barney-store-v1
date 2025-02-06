import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL rootPath = getClass().getResource("view/RootView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(rootPath);
        Parent root = fxmlLoader.load();
        Scene screen = new Scene(root);

        primaryStage.setTitle("Barney Store");
        primaryStage.setScene(screen);
        primaryStage.show();
    }
}
