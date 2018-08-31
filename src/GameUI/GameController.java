package GameUI;

import GameLogic.GameManager;
import GameLogic.Player;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.INACTIVE;

import java.nio.ByteOrder;


public class GameController {

    @FXML
    VBox playerListVBox;
    @FXML
    HBox gameHeader;
    @FXML
    Label variantLabel;
    @FXML
    BorderPane mainBorderPane;
    @FXML
    HBox centerArea;
    @FXML
    HBox arrowArea;
    @FXML
    HBox downArea;
    @FXML
    Label playerTurnLabel;
    @FXML
    Label targetLabel;

    private Stage primaryStage;
    private GameManager gameManager;
    private Player currPlayer;
    private String dickColor;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startGame() {
        showPlayersToScreen();
        setGameType();
        showBoard();
        targetLabel.setText(String.valueOf(gameManager.getGameBoard().getTarget()));
        int index = gameManager.getTurnIndex();
        currPlayer = gameManager.getPlayersByOrder().get(index);
        dickColor = currPlayer.getPlayerColor();
        String PlayerName = currPlayer.getName();
        playerTurnLabel.setText("Current player name: " + PlayerName);
    }


    private void showBoard() {

        for (int i = 0; i < gameManager.getGameBoard().getCols(); i++) {
            ImageView arrowImg = createArow(i);
            if (gameManager.getVariant().toUpperCase().equals("POPOUT")) {
                ImageView removeDickImg = createRemoverImg(i);
                downArea.getChildren().add(removeDickImg);
                addHandlersToRemoveDiskImg(removeDickImg);
            }

            addHandlersToArrowImg(arrowImg);

            arrowArea.getChildren().add(arrowImg);

            VBox newCol = new VBox();
            newCol.setId("Col " + (i + 1));
            for (int j = 0; j < gameManager.getGameBoard().getRows(); j++) {
                Button newBtn = new Button();
                newBtn.setShape(new Circle(30));
                newBtn.setLineSpacing(2);
                newBtn.setId((j + 1) + "X" + (i + 1));
                newBtn.getStyleClass().add("boardBtn");
                newBtn.setDisable(true);
                newCol.getChildren().add(newBtn);
            }
            centerArea.getChildren().add(newCol);
        }
    }


    private void addHandlersToArrowImg(ImageView arrowImg)
    {
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

                if (checkColFull(arrowImg.getId())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Col is Full! Please Choose Another One ");
                    alert.showAndWait();
                } else {
                    insertDiskToCol(arrowImg.getId());/////////////////////////////  28.8.18
                    gameManager.getGameBoard().printBoard(); // for debug
                    nextTurnAction();
                    currPlayer = gameManager.getPlayersByOrder().get(gameManager.getTurnIndex());
                }
            }
        });
    }

    private void addHandlersToRemoveDiskImg(ImageView removeDickImg){
        removeDickImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                removeDickImg.setFitHeight(40);
                removeDickImg.setFitWidth(40);
            }
        });

        removeDickImg.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeDickImg.setFitHeight(50);
                removeDickImg.setFitWidth(50);

                String[] idArr = removeDickImg.getId().split(" ");
                int col = Integer.valueOf(idArr[1])-1;

                if (checkColEmpty(col)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Can't remove disk from empty col!");
                    alert.showAndWait();
                } else {
                    if(gameManager.getGameBoard().checkSignAndRemove(col,currPlayer.getPlayerSign()))
                    {
                        nextTurnAction();
                        currPlayer = gameManager.getPlayersByOrder().get(gameManager.getTurnIndex());
                        gameManager.getGameBoard().printBoard(); // for debug
                        removeDiskFromCol(col);
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Can't remove dick! Not your dick!");
                        alert.showAndWait();
                    }
                }
            }
        });
    }

    private void removeDiskFromCol(int col)
    {
        VBox v = (VBox) centerArea.getChildren().get(col);
        v.getChildren().clear();

        for(int i=0; i<gameManager.getGameBoard().getRows(); i++) {
            Button newBtn = new Button();
            newBtn.setShape(new Circle(30));
            newBtn.setLineSpacing(2);
            newBtn.setId((col + 1) + "X" + (i + 1));
            int colorInt = gameManager.getGameBoard().getBoard()[i][col];
            String newColor = convertNumToColor(colorInt);
            if(!newColor.equals("noColor"))
            {
                newBtn.getStyleClass().add(newColor);
                newBtn.setDisable(false);

            }
            else {
                newBtn.getStyleClass().add("boardBtn");
                newBtn.setDisable(true);
            }
            v.getChildren().add(newBtn);
        }
    }

    private String convertNumToColor(int num) {
        switch (num) {
            case 0:
                return "green";

            case 1:
                return "blue";

            case 2:
                return "red";

            case 3:
                return "yellow";

            case 4:
                return "pink";

            case 5:
                return "azure";
        }
        return "noColor";
    }



    private boolean checkColEmpty(int col)
    {
        boolean res = false;
        if(gameManager.getGameBoard().checkColEmpty(col))
        {
            res = true;
        }
        return res;
    }


    private boolean checkColFull(String id) {
        boolean res = false;
        String[] idArr = id.split(" ");
        int col = Integer.valueOf(idArr[1]);

        if (gameManager.checkColFullInBoard(col)) {
            res = true;
        }
        return res;
    }




    private ImageView createArow(int i) {
        ImageView arrowImg = new ImageView();
        Image arrow = new Image("resoures/images/arrow.jpg");
        arrowImg.setImage(arrow);
        arrowImg.setId("Arrow " + (i + 1));
        arrowImg.getStyleClass().add("arrowImg");
        arrowImg.setFitHeight(50);
        arrowImg.setFitWidth(50);
        return arrowImg;
    }

    private ImageView createRemoverImg(int i)
    {
        ImageView deleteDickImg = new ImageView();
        Image img = new Image("resoures/images/removeDisk.png");
        deleteDickImg.setImage(img);
        deleteDickImg.setId("remover " + (i + 1));
        deleteDickImg.getStyleClass().add("removeDiskImg");
        deleteDickImg.setFitHeight(50);
        deleteDickImg.setFitWidth(50);
        return deleteDickImg;

    }
    private void insertDiskToCol(String id) {///////////28.8.18
        String[] idArr = id.split(" ");
        int col = Integer.valueOf(idArr[1]);
        int i = 0;
        boolean done = false;

        VBox v = (VBox) centerArea.getChildren().get(col - 1);
        while (i < gameManager.getGameBoard().getRows() - 1 && !done) {
            if (v.getChildren().get(i).isDisable()) {
                if ((i + 1) == gameManager.getGameBoard().getRows() - 1 && v.getChildren().get(i + 1).isDisable()) {
                    Button b1 = (Button) v.getChildren().get(i + 1);
                    b1.getStyleClass().removeAll("boardBtn");
                    b1.setShape(new Circle(30));
                    b1.getStyleClass().add(dickColor);
                    b1.setDisable(false);
                    insertDiskToBoard(col - 1);
                    done = true;
                } else if (!(v.getChildren().get(i + 1).isDisable())) {
                    Button b1 = (Button) v.getChildren().get(i);
                    b1.getStyleClass().removeAll("boardBtn");
                    b1.setShape(new Circle(15));
                    b1.getStyleClass().add(dickColor);
                    b1.setDisable(false);
                    insertDiskToBoard(col - 1);
                    done = true;
                }
            }
            i++;
        }

        if(gameManager.getGameBoard().checkPlayerWin(col, variantLabel.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, gameManager.getPlayersByOrder().get(gameManager.getTurnIndex()).getName()+" WOM!");
            alert.showAndWait();
        }
    }

    private void insertDiskToBoard(int col) {
        gameManager.getGameBoard().setSignOnBoard(col, currPlayer);
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

            addPlayerDataToVBox(player, newVBox,counter);
            playerListVBox.getChildren().add(newVBox);
        }
    }

    private void addPlayerDataToVBox(Player player, VBox newVBox ,Integer index) {
        String name = player.getName();
        Integer ID = player.getId();
        String type = player.getPlayerType();
        Integer turns = player.getTurnCounter();
        String Color = player.getPlayerColor();

        Label playerName = new Label();
        playerName.setText("Name: " + name);
        newVBox.getChildren().add(playerName);

        Label PlayerId = new Label();
        PlayerId.setText("ID: " + ID.toString());
        newVBox.getChildren().add(PlayerId);

        Label PlayerType = new Label();
        PlayerType.setText("Type: " + type);
        newVBox.getChildren().add(PlayerType);

        //TODO: add binding to turns
        HBox Turns = new HBox();
        Label PlayerNumOfTurns = new Label();
        Label TurnsLabel = new Label();
        TurnsLabel.setText("Turns: ");
        PlayerNumOfTurns.setText(turns.toString());
        Turns.getChildren().add(TurnsLabel);
        Turns.getChildren().add(PlayerNumOfTurns);
        //PlayerNumOfTurns.textProperty().bind(new SimpleIntegerProperty(gameManager.getPlayersByOrder().get(index).getTurnCounter()).asString());
        newVBox.getChildren().add(Turns);

        Label PlayerColor = new Label();
        PlayerColor.setText("Color: "+ Color);
        newVBox.getChildren().add(PlayerColor);

        Separator newSep = new Separator();
        newSep.getStyleClass().add("sepPlayers");
        newVBox.getChildren().add(newSep);
    }

    private void setGameType() {
        variantLabel.setText(gameManager.getVariant());
    }
//
//    public static void sleepForAWhile(long sleepTime) {
//
//        if (sleepTime != 0) {
//            try {
//                Thread.sleep(sleepTime);
//            } catch (InterruptedException ignored) {
//
//            }
//        }
//    }

    public void nextTurnAction() {
        //int index = gameManager.getTurnIndex();
        gameManager.incCurrPlayerTurn();
        gameManager.incTurnIndex();
        dickColor = gameManager.getPlayersByOrder().get(gameManager.getTurnIndex()).getPlayerColor();
        //setDickColor(index);
        while (!gameManager.getPlayersByOrder().get(gameManager.getTurnIndex()).isAcive()) {
            gameManager.incTurnIndex();
        }
        playerTurnLabel.setText("Current player name: " + gameManager.getPlayersByOrder().get(gameManager.getTurnIndex()).getName());
    }

}