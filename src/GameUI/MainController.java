package GameUI;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {

    private Stage primaryStage;
    public Label fileNameLable;
    public Button startGameBtn;
    public Button exitCurrGameBtn;

    public void initialize() {
        startGameBtn.setDisable(true);
        exitCurrGameBtn.setDisable(true);
    }

    public void openFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select words file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.xml", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        else
        {
            //fileChecker.setFilePath(selectedFile.getPath());
            fileNameLable.setText(selectedFile.getPath());
            startGameBtn.setDisable(false);
        }
    }
}
