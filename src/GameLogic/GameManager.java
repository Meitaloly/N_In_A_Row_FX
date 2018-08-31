package GameLogic;

import GameLogic.generatedClasses.GameDescriptor;
import GameLogic.generatedClasses.Player;

import java.util.*;

public class GameManager {

    private XmlFileUtils XFU;
    private GameBoard gameBoard;
    private GameDescriptor desc;
    private Map<Integer, GameLogic.Player> playersInMap;
    private ArrayList<GameLogic.Player> playersByOrder;
    private String variant;
    private boolean activeGame = false;
    private int turnIndex = 0;
    private GameTimer gameTimer;
    private Timer timer;
    private GameHistory history;
//    private boolean savedGamedLoaded = false;
//    private boolean loadedBoard = false;

    public void incTurnIndex() {
        turnIndex++;
        if (playersInMap.size() <= turnIndex) {
            turnIndex = 0;
        }
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public GameManager() {
        XFU = new XmlFileUtils();
        XFU.setGameManager(this);
        gameBoard = new GameBoard();
    }


//    public void checkWinner(int col, String gameType) {
//        gameBoard.checkPlayerWin(col, gameType);
//    }

    public void startTimer() {
        gameTimer = new GameTimer();
        timer = new Timer();
    }

    public String getCurrTime() {
        return gameTimer.getTime();
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public ArrayList<GameLogic.Player> getPlayersByOrder() {
        return playersByOrder;
    }

    public String getVariant() {
        return variant;
    }

    public int checkXmlFile(String path) {
        int res = -1;
        XFU.setFilePath(path);
        synchronized (this) {
            res = XFU.checkXmlFileValidation(gameBoard);
        }
        return res;

    }

    public void setDesc(GameDescriptor desc) {
        this.desc = desc;
    }

    public GameDescriptor getDesc() {
        return desc;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int ComputerPlay() {
        Random rand = new Random();
        int choosenCol = rand.nextInt((int) gameBoard.getCols() - 1);
        int placeInBoard = gameBoard.getBoard()[1][choosenCol];
        while (placeInBoard != -1) {
            choosenCol = rand.nextInt((int) gameBoard.getCols() - 1);
            placeInBoard = gameBoard.getBoard()[1][choosenCol];

        }
        return choosenCol;
    }

    public void incCurrPlayerTurn() {
        playersByOrder.get(turnIndex).incTurnCounter();
    }

    public boolean buildPlayersFromFile() {
        boolean res = true;
        playersInMap = new HashMap<>();
        playersInMap.clear();
        playersByOrder = new ArrayList<>();
        playersByOrder.clear();

        for (GameLogic.generatedClasses.Player player : desc.getPlayers().getPlayer()) {
            Integer playerId = Integer.valueOf(player.getId());
            if (playersInMap.containsKey(playerId)) {
                playersInMap.clear();
                playersByOrder.clear();
                res = false;
                break;
            } else {
                GameLogic.Player newPlayer = new GameLogic.Player();
                newPlayer.setId((int) player.getId());
                newPlayer.setName(player.getName());
                newPlayer.setPlayerType(player.getType());
                playersInMap.put(newPlayer.getId(), newPlayer);
                playersByOrder.add(newPlayer);
            }
        }
        if (res) {
            variant = desc.getGame().getVariant();
            setColorosToPlayers();
        }
        return res;
    }


    public boolean checkColFullInBoard(int col) {
        return gameBoard.isColFull(col);
    }

    public void setColorosToPlayers() {
        int size = playersInMap.size();
        for (int i = 0; i < size; i++) {
            playersByOrder.get(i).setPlayerColor(setDickColor(i));
            playersByOrder.get(i).setPlayerSign(i);
        }
    }

    private String setDickColor(int index) {
        switch (index) {
            case 0:
                return ("green");

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

    public int getNumOfPlayers() {
        return playersInMap.size();
    }

    public int getPlayerId(int i) {
        return playersInMap.get(i).getId();
    }

    public String getPlayerName(int i) {
        return playersInMap.get(i).getName();
    }

    public int getPlayerNumOfTurns(int i) {
        return playersInMap.get(i).getTurnCounter();
    }

    public String getPlayerType(int i) {
        return playersInMap.get(i).getPlayerType();
    }
}