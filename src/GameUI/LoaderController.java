package GameUI;

import GameLogic.GameManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class LoaderController {
    @FXML
    ProgressBar progressBar;
    @FXML
    Label LoadingLabel;
    private Stage primaryStage;
    private GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public static void sleepForAWhile(long sleepTime) {

        if (sleepTime != 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void bindTaskToUIComponents(Task<Integer> aTask) {
        // task progress bar
        progressBar.progressProperty().bind(aTask.progressProperty());
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished();
        });

    }

    public void onTaskFinished() {
        this.progressBar.progressProperty().unbind();
        primaryStage.close();
    }

    public void RunTask(GameManager game, String path, Stage mainStage) throws InterruptedException {
        Task<Integer> currentRunningTask = new LoadFileTask(game, path);
        bindTaskToUIComponents(currentRunningTask);
        currentRunningTask.setOnSucceeded(e -> {
            mainStage.show();
        });

        Thread LoaderThread = new Thread(currentRunningTask);
        LoaderThread.setName("loaferThread");
        LoaderThread.start();
    }
}
