package GameUI;

import GameLogic.ComputerChoice;
import GameLogic.GameManager;
import GameLogic.Player;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import java.util.ArrayList;
import java.util.List;


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
    @FXML
    Button finishGameBtn;
    @FXML
    Button leaveGameBtn;
    @FXML
    Button startGameBtn;
    @FXML
    Button backBtn;
    @FXML Label pathLabel;
    @FXML Button exitBtn;

    private Stage primaryStage;
    private GameManager gameManager;
    private Player currPlayer;
    private String dickColor;
    private Stage MenuScreen;


    public void setMenuScreen(Stage menuScreen) {
        MenuScreen = menuScreen;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showGameBoard(String filePath)
    {
        exitBtn.setDisable(true);
        pathLabel.setText(filePath);
        backBtn.setDisable(false);
        leaveGameBtn.setDisable(true);
        gameManager.resetGame();
        showPlayersToScreen();
        setGameType();
        showBoard();
        targetLabel.setText(String.valueOf(gameManager.getGameBoard().getTarget()));
    }

    public void backBtnAction()
    {
        finishTheGame();
    }

    public void startGame() {
        startGameBtn.setDisable(true);
        exitBtn.setDisable(false);
        backBtn.setDisable(true);
        gameManager.setActiveGame(true);
        leaveGameBtn.setDisable(false);
        startGameBtn.setDisable(true);
        gameManager.setActiveGame(true);
        int index = gameManager.getTurnIndex();
        currPlayer = gameManager.getPlayersByOrder().get(index);
        dickColor = currPlayer.getPlayerColor();
        String PlayerName = currPlayer.getName();
        playerTurnLabel.setText("Current player name: " + PlayerName);
        if (currPlayer.getPlayerType().toUpperCase().equals("COMPUTER")) {
            playerTurnLabel.setText("Current player name: " + PlayerName + " (Computer Calculate next step...)");

            try {
                RunTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void finishTheGame()
    {
        gameManager.resetGame();
        primaryStage.hide();
        MenuScreen.show();
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
                if(gameManager.getActiveGame()) {
                    if (!currPlayer.getPlayerType().toUpperCase().equals("COMPUTER")) {
                        arrowImg.setFitHeight(40);
                        arrowImg.setFitWidth(40);
                    }
                }
            }
        });

        arrowImg.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(gameManager.getActiveGame()) {

                    if (!currPlayer.getPlayerType().toUpperCase().equals("COMPUTER")) {
                        arrowImg.setFitHeight(50);
                        arrowImg.setFitWidth(50);

                        if (checkColFull(arrowImg.getId())) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Col is Full! Please Choose Another One ");
                            alert.showAndWait();
                        } else {
                            insertDiskToCol(arrowImg.getId());/////////////////////////////  28.8.18
                            gameManager.getGameBoard().printBoard(); // for debug
                            nextTurnAction();
                        }
                    }
                }
            }
        });
    }

    private void addHandlersToRemoveDiskImg(ImageView removeDickImg){
        removeDickImg.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        if(gameManager.getActiveGame()) {
                            if (!currPlayer.getPlayerType().toUpperCase().equals("COMPUTER")) {
                                removeDickImg.setFitHeight(40);
                                removeDickImg.setFitWidth(40);
                            }
                        }
                    }
                });

        removeDickImg.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameManager.getActiveGame()) {
                    if (!currPlayer.getPlayerType().toUpperCase().equals("COMPUTER")) {

                        removeDickImg.setFitHeight(50);
                        removeDickImg.setFitWidth(50);

                        String[] idArr = removeDickImg.getId().split(" ");
                        int col = Integer.valueOf(idArr[1]) - 1;

                        if (checkColEmpty(col)) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Can't remove disk from empty col!");
                            alert.showAndWait();
                        } else {
                            if (gameManager.getGameBoard().checkSignAndRemove(col, currPlayer.getPlayerSign())) {
                                nextTurnAction();
                                currPlayer = gameManager.getPlayersByOrder().get(gameManager.getTurnIndex());
                                gameManager.getGameBoard().printBoard(); // for debug
                                removeDiskFromCol(col);

                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Can't remove dick! Not your dick!");
                                alert.showAndWait();
                            }
                        }
                    }
                }
            }
        });
    }

    private void converIntSignToName(List<String> strList, List<Integer> intList)
    {
        for(Integer num : intList)
        {
            strList.add(convertSignToPlayer(num));
        }
    }

    private String convertSignToPlayer(int num)
    {
        String res = "";

        for(Player player : gameManager.getPlayersByOrder())
        {
            if(player.getPlayerSign() == num)
            {
                res = player.getName();
                break;
            }
        }

        return res;

    }

    private String getNamesFromList(List<String> winnersList){
        String res = "";
        int size = winnersList.size();

        for(String str : winnersList)
        {
            res += str;
            size--;
            if(size!=0)
            {
                res+=", ";
            }
        }
        return res;
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

        List<String> winnersList = new ArrayList<>();
        List<Integer> signsOfWinners = new ArrayList<>();

        if (gameManager.getGameBoard().checkAnyWinner(col, signsOfWinners)) {
            converIntSignToName(winnersList, signsOfWinners);
            String names = getNamesFromList(winnersList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, names + " WON!");
            alert.showAndWait();
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

        if(gameManager.getGameBoard().checkPlayerWin(col, variantLabel.getText(),currPlayer.getPlayerType())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, currPlayer.getName()+" WOM!");
            alert.showAndWait();
            finishTheGame();
        }
        else
        {
//            if(gameManager.getGameBoard().isBoardFull()) {
//                if (!gameManager.getVariant().toUpperCase().equals("POPOUT")) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Board is full - game over! ");
//                    alert.showAndWait();
//                    finishTheGame();
//                }
//                else
//                {
//
//                    checkPlayerCanRemove(currPlayer.getPlayerSign());
//                }
//            }
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
        newSep.setPrefSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
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

        setTurnsToScreen();    ////////////// not working with comp player
        gameManager.incCurrPlayerTurn();
        gameManager.incTurnIndex();
        currPlayer = gameManager.getPlayersByOrder().get(gameManager.getTurnIndex());
        dickColor = currPlayer.getPlayerColor();
        if (!currPlayer.isAcive()) {
            nextTurnAction();
        }
        playerTurnLabel.setText("Current player name: " + currPlayer.getName());
        if(currPlayer.getPlayerType().toUpperCase().equals("COMPUTER"))
        {
            playerTurnLabel.setText("Current player name: " + currPlayer.getName() + " (Computer calculate next step...)");
            try {
                RunTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            if(gameManager.getGameBoard().isBoardFull()) {
                if (!gameManager.getVariant().toUpperCase().equals("POPOUT")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Board is full - game over! ");
                    alert.showAndWait();
                    finishTheGame();
                }
                else
                {
                    if(gameManager.getGameBoard().checkPlayerCanRemove(currPlayer.getPlayerSign()))
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Board is full and no disk at the bottom is yours - game over! ");
                        alert.showAndWait();
                        finishTheGame();
                    }
                }
            }
        }

    }

    public void setTurnsToScreen(){
        int index = gameManager.getTurnIndex();
        VBox VBtemp = (VBox)playerListVBox.getChildren().get(index+1);
        HBox HBtemp =  (HBox)VBtemp.getChildren().get(4);
        Label temp = (Label)HBtemp.getChildren().get(1);
        Integer turns = currPlayer.getTurnCounter()+1;
        temp.setText(turns.toString());
    }

    public void RunTask() throws InterruptedException {
        Task<ComputerChoice> currentRunningTask = new ComputerPlayTask(gameManager);
        currentRunningTask.setOnSucceeded(e -> Platform.runLater( () ->
                {
                    computerPlay(currentRunningTask.getValue());
                }
                )
        );

        Thread ComputerPlaysThread = new Thread(currentRunningTask);
        ComputerPlaysThread.setName("loaderThread");
        ComputerPlaysThread.start();
    }

    public void computerPlay(ComputerChoice computerChoice)
    {
        if(computerChoice.getSucceeded())
        {
            System.out.println("computer choose: " + computerChoice.getChoosenCol());
            String id = "computer " + (computerChoice.getChoosenCol() + 1);

            if(computerChoice.getPopout())
            {
                gameManager.getGameBoard().checkSignAndRemove(computerChoice.getChoosenCol(),currPlayer.getPlayerSign());
                removeDiskFromCol(computerChoice.getChoosenCol());
            }
            else {

                insertDiskToCol(id);/////////////////////////////  28.8.18
            }
            gameManager.getGameBoard().printBoard(); // for debug
            nextTurnAction();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Computer Can't Play! ");
            alert.showAndWait();
            finishTheGame();
        }
    }

    public void switchBackground(){
        //ImageView change = new ImageView();
        //Image img = new Image("resoures/images/red-background.png");
        mainBorderPane.getStyleClass().clear();
        mainBorderPane.getStyleClass().add("style2");
        //primaryStage.show();
        showBoard();

    }

    public void leaveTheGame(){
        VBox VBtemp = (VBox)playerListVBox.getChildren().get(gameManager.getTurnIndex()+1);
        Label temp = (Label)VBtemp.getChildren().get(1);
        temp.setText("Name: " + currPlayer.getName() + " (left the game.");
        gameManager.getGameBoard().removeAllDisksOfPlayer(currPlayer);
        for (int col = 0 ; col < gameManager.getGameBoard().getCols(); col++){
            removeDiskFromCol(col);
        }
        currPlayer.setDisable();
        if(gameManager.isOnlyOnePlayerLeft())
        {
            printWinner();
        }
        else {
            nextTurnAction();
        }
    }

    public void printWinner()
    {
        Player winner = gameManager.getWinnerPlayer();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, winner.getName()+ " WON!");
        alert.showAndWait();
        finishTheGame();
    }
}