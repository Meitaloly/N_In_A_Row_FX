package GameUI;

import GameLogic.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.stage.Stage;

public class GameController {

    @FXML Label playerOneName;
    @FXML Label playerOneId;
    @FXML Label playerOneType;
    @FXML Label playerOneColor;
    @FXML Label playerOneNumofTurns;

    private Stage primaryStage;
    private GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showPlayersToScreen(){
        int player = 0;
        while (player<gameManager.numOfPlayers()){

        }
    }
}
