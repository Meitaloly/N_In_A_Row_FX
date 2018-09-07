package GameUI;

import GameLogic.GameManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoaderController {
    @FXML
    ProgressBar progressBar;
    @FXML
    Label LoadingLabel;
    private Stage primaryStage;
    private GameManager gameManager;
    private Thread LoaderThread;

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

    public int RunTask(GameManager game, String path, Stage mainStage, MainController mainController) throws InterruptedException {
        final int[] res = new int[1];
        Task<Integer> currentRunningTask = new LoadFileTask(game, path);
        bindTaskToUIComponents(currentRunningTask);
        currentRunningTask.setOnSucceeded(e -> Platform .runLater( () ->
                {
                    mainStage.show();
                    res[0] = currentRunningTask.getValue();
                    mainController.doWhenTaskFinish(res[0]);

                }
                )
        );

        LoaderThread = new Thread(currentRunningTask);
        LoaderThread.setName("loaderThread");
        LoaderThread.start();
        return res[0];
    }
}
