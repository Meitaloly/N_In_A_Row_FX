package GameUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = Main.class.getResource("/GameUI/main.fxml");
        loader.setLocation(mainFXML);
        primaryStage.setTitle("N IN A ROW GAME");
        Pane root = loader.load();
        MainController mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(560);
        primaryStage.show();
    }
}