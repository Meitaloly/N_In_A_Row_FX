package GameUI;

import GameLogic.GameManager;
import GameLogic.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.INACTIVE;

import java.nio.ByteOrder;


public class GameController {

    @FXML VBox playerListVBox;
    @FXML HBox gameHeader;
    @FXML Label variantLabel;
    @FXML BorderPane mainBorderPane;
    @FXML HBox centerArea;
    @FXML HBox arrowArea;
    @FXML Label timerLabel;

    private Stage primaryStage;
    private GameManager gameManager;
    private ScrollPane scrollPane;
    private Timer showTimer;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startGame()
    {
//        gameManager.startTimer();
//        gameManager.setActiveGame(true);
//        showTimer = new Timer(gameManager,this);
//        java.util.Timer time = new java.util.Timer();
//        time.schedule(showTimer,1000);

        showPlayersToScreen();
        setGameType();
        showBoard();
    }

    public void setTimer(String currTime)
    {
        timerLabel.setText(currTime);
    }

    private void showBoard() {

        for (int i = 0; i < gameManager.getGameBoard().getCols(); i++) {
            ImageView arrowImg = new ImageView();
            Image arrow = new Image("resoures/images/arrow.jpg");
            arrowImg.setImage(arrow);
            arrowImg.getStyleClass().add("arrowImg");
            arrowImg.setFitHeight(50);
            arrowImg.setFitWidth(50);

            arrowImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    arrowImg.setFitHeight(40);
                    arrowImg.setFitWidth(40);
                }
            });

            arrowImg.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    arrowImg.setFitHeight(50);
                    arrowImg.setFitWidth(50);
                }
            });

            arrowArea.getChildren().add(arrowImg);

            VBox newCol = new VBox();
            newCol.setId("Col "+(i+1));
            for(int j=0; j<gameManager.getGameBoard().getRows();j++)
            {
                Button newBtn = new Button();
                newBtn.setLineSpacing(2);
                newBtn.setId((j+1)+ "X" + (i+1));
                newBtn.getStyleClass().add("boardBtn");
                newBtn.setDisable(true);
                newCol.getChildren().add(newBtn);
            }
            centerArea.getChildren().add(newCol);
        }
    }

    private void showPlayersToScreen() {
        Integer counter = 1;

        for (Player player : gameManager.getPlayersByOrder()) {
            String VBoxName = "Player" + counter + "info";
            VBox newVBox = new VBox();
            newVBox.setId(VBoxName);

            Label playerTitle = new Label();
            playerTitle.setText("Player: " + (counter++));
            newVBox.getChildren().add(playerTitle);

            addPlayerDataToVBox(player, newVBox);
            playerListVBox.getChildren().add(newVBox);
        }
    }

    private void addPlayerDataToVBox(Player player, VBox newVBox) {
        String name = player.getName();
        Integer ID = player.getId();
        String type = player.getPlayerType();
        Integer turns = player.getTurnCounter();

        Label playerName = new Label();
        playerName.setText("Name: " + name);
        newVBox.getChildren().add(playerName);

        Label PlayerId = new Label();
        PlayerId.setText("ID: " + ID.toString());
        newVBox.getChildren().add(PlayerId);

        Label PlayerType = new Label();
        PlayerType.setText("Type: "+type);
        newVBox.getChildren().add(PlayerType);

        Label PlayerNumOfTurns = new Label();
        PlayerNumOfTurns.setText("Turns: " +turns.toString());
        newVBox.getChildren().add(PlayerNumOfTurns);

        Separator newSep = new Separator();
        newSep.getStyleClass().add("sepPlayers");
        newVBox.getChildren().add(newSep);
    }

    private void setGameType()
    {
        variantLabel.setText(gameManager.getVariant());
    }
}