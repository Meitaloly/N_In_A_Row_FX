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

    public void incTurnIndex(){
        turnIndex++;
        if (playersInMap.size()<=turnIndex){
            turnIndex = 0;
        }
    }

    public int getTurnIndex(){
        return turnIndex;
    }

    public GameManager()
    {
        XFU = new XmlFileUtils();
        XFU.setGameManager(this);
        gameBoard = new GameBoard();
    }

    public void startTimer()
    {
        gameTimer = new GameTimer();
        timer = new Timer();
    }

    public String getCurrTime()
    {
        return gameTimer.getTime();
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public ArrayList<GameLogic.Player> getPlayersByOrder()
    {
        return playersByOrder;
    }

    public String getVariant() {
        return variant;
    }

    public int checkXmlFile(String path)
    {
        int res = -1;
        XFU.setFilePath(path);
        synchronized (this)
        {
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

    public boolean buildPlayersFromFile() {
        boolean res = true;
        playersInMap = new HashMap<>();
        playersInMap.clear();
        playersByOrder = new ArrayList<>();
        playersByOrder.clear();

        System.out.println("num of players: " + desc.getPlayers().getPlayer().size());
        for (GameLogic.generatedClasses.Player player : desc.getPlayers().getPlayer()) {
            System.out.println(Thread.currentThread().getName());
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
                System.out.println("playersInMap size is: " + playersInMap.size());
                System.out.println("playersByOrder size is: " + playersByOrder.size());
            }
        }
        if(res)
        {
          variant = desc.getGame().getVariant();
        }
        return res;
    }

    public int getNumOfPlayers(){
        return playersInMap.size();
    }

    public int getPlayerId (int i){
        return playersInMap.get(i).getId();
    }

    public String getPlayerName (int i){
        return playersInMap.get(i).getName();
    }

    public int getPlayerNumOfTurns (int i){
        return playersInMap.get(i).getTurnCounter();
    }

    public String getPlayerType (int i){
        return playersInMap.get(i).getPlayerType();
    }

/*
    public int getPlayerColor (int i){
        return playersInMap.get(i)();
    }*/
}
