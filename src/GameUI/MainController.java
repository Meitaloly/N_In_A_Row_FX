package GameUI;

import GameLogic.GameManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
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
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        else
        {
            res = LoadFromTask(selectedFile.getPath());
        }

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
            loaderController.RunTask(gameManager,path,primaryStage);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gameManager.checkXmlFile(path);
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
        }

        return msg;
    }

}
