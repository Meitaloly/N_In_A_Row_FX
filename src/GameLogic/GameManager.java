package GameLogic;

import GameLogic.generatedClasses.GameDescriptor;
import GameLogic.generatedClasses.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private XmlFileUtils XFU;
    private GameBoard gameBoard;
    private GameDescriptor desc;
    private Map<Integer, GameLogic.Player> playersInMap;
    private ArrayList<GameLogic.Player> playersByOrder;

    public GameManager()
    {
        XFU = new XmlFileUtils();
        XFU.setGameManager(this);
        gameBoard = new GameBoard();
    }

    public ArrayList<GameLogic.Player> getPlayersByOrder()
    {
        return playersByOrder;
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
