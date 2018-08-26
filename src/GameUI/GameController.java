package GameUI;

import GameLogic.GameManager;
import GameLogic.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GameController {

    @FXML VBox playerListVBox;
//    @FXML Label playerOneName;
//    @FXML Label playerOneId;
//    @FXML Label playerOneType;
//    @FXML Label playerOneColor;
//    @FXML Label playerOneNumofTurns;

    private Stage primaryStage;
    private GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showPlayersToScreen() {
        for (Player player : gameManager.getPlayersByOrder()) {
            int counter = 0;
            String VBoxName = "Player" + (counter + 1) + "info";
            VBox newVBox = new VBox();
            newVBox.setId(VBoxName);
            addPlayerDataToVBox(player, newVBox);
            playerListVBox.getChildren().add(newVBox);
        }
    }

    public void addPlayerDataToVBox(Player player, VBox newVBox) {
        String name = player.getName();
        Integer ID = player.getId();
        String type = player.getPlayerType();
        Integer turns = player.getTurnCounter();

        Label playerName = new Label();
        playerName.setText(name);
        newVBox.getChildren().add(playerName);

        Label PlayerId = new Label();
        PlayerId.setText(ID.toString());
        newVBox.getChildren().add(PlayerId);

        Label PlayerType = new Label();
        PlayerType.setText(type);
        newVBox.getChildren().add(PlayerType);

        Label PlayerNumOfTurns = new Label();
        PlayerNumOfTurns.setText(turns.toString());
        newVBox.getChildren().add(PlayerNumOfTurns);
    }
//        if (gameManager.getNumOfPlayers()>0) {
//
//            int player = 1;
//            String name;
//            String ID;
//            String type;
//            String turns;
//            //while (player<gameManager.numOfPlayers()){
//
//            //  }
//            name = gameManager.getPlayerName(player);  //only for debug
//            playerOneName.setText(name);
//            ID = "ID: " + gameManager.getPlayerId(player);    //only for debug
//            playerOneId.setText(ID);
//            type = "Player type: " + gameManager.getPlayerType(player);  //only for debug
//            playerOneType.setText(type);
//            turns = "Number of turns: " + gameManager.getPlayerNumOfTurns(player);   //only for debug
//            playerOneNumofTurns.setText(turns);
//        }
}
