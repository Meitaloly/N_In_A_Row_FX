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

    public int checkXmlFile(String path)
    {
        XFU.setFilePath(path);
        return XFU.checkXmlFileValidation(gameBoard);
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
        playersByOrder = new ArrayList<>();
        for (GameLogic.generatedClasses.Player player : desc.getPlayers().getPlayer()) {
            Integer playerId = Integer.valueOf(player.getId());
            if (playersInMap.containsKey(playerId)) {
                res = false;
                playersInMap.clear();
                playersByOrder.clear();
                break;
            }
            else {
                GameLogic.Player newPlayer= new GameLogic.Player();
                newPlayer.setId((int) player.getId());
                newPlayer.setName(player.getName());
                newPlayer.setPlayerType(player.getType());
                playersInMap.put(newPlayer.getId(), newPlayer);
                playersByOrder.add(newPlayer);
            }
        }
        return res;
    }
}
