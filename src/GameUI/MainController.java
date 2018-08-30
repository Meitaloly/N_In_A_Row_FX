package GameUI;

import GameLogic.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML Label fileNameLable;
    @FXML Button startGameBtn;
    @FXML Button exitCurrGameBtn;
    @FXML GridPane mainGridPane;
    @FXML Label filePathLable;

    private Stage primaryStage;
    private GameManager gameManager;
    private File selectedFile;

    public MainController()
    {
        gameManager = new GameManager();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        startGameBtn.setDisable(true);
        exitCurrGameBtn.setDisable(true);
    }

    public void openFileButtonAction() throws InterruptedException {
        int res = -1;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        else
        {
            res = LoadFromTask(selectedFile.getPath());
        }
    }

    public void doWhenTaskFinish(int res)
    {
        if(res != -1)
        {
            String msg = ErrorMessageFromXmlFile(res);
            msg = msg + " Please Load New XML File!";
            startGameBtn.setDisable(true);
            filePathLable.setText("File Error: ");
            fileNameLable.setText(msg);
            fileNameLable.setTextFill(Color.RED);
        }
        else
        {
            startGameBtn.setDisable(false);
            filePathLable.setText("File Path: ");
            fileNameLable.setText(selectedFile.getPath());
            fileNameLable.setTextFill(Color.BLACK);

        }
    }

    private int LoadFromTask(String path){
        int res = -1;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL loadFXML = Main.class.getResource("Loader.fxml");
            loader.setLocation(loadFXML);
            Stage LoaderStage= new Stage();
            LoaderStage.initStyle(StageStyle.UNDECORATED);
            LoaderStage.setTitle("XML-Loader");
            Pane root = loader.load();
            LoaderController loaderController = loader.getController();
            loaderController.setPrimaryStage(LoaderStage);
            loaderController.setGameManager(gameManager);
            LoaderStage.setScene(new Scene(root));
            primaryStage.hide();
            LoaderStage.show();
            res = loaderController.RunTask(gameManager, path, primaryStage, this);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void startGame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL gameUrl = getClass().getResource("Game.fxml");
            loader.setLocation(gameUrl);
            Stage gameStage = new Stage();
            gameStage.setTitle("N IN A ROW GAME");
            Pane root = loader.load();
            GameController gameController = loader.getController();
            gameController.setPrimaryStage(gameStage);
            gameController.setGameManager(gameManager);
            Scene scene = new Scene(root, 900, 600);
            gameStage.setScene(scene);

            primaryStage.hide();
            gameStage.show();
            gameController.startGame();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private String ErrorMessageFromXmlFile(int num)
    {
        String msg= "";
        switch (num)
        {
            case 0:
                msg = "File not Exist!";
                break;
            case 1:
                msg= "Num of rows not in range!";
                break;
            case 2:
                msg = "Num of cols not in range!";
                break;
            case 3:
                msg = "Target bigger than rows or cols!";
                break;
            case 4:
                msg = "Target is 0!";
                break;
            case 5:
                msg = "Not XML file!";
                break;
            case 6:
                msg = "Number of players not in range!";
                break;
            case 7:
                msg = "There are 2 players with the same ID!";
                break;
        }

        return msg;
    }

}
