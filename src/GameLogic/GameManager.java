package GameLogic;

import GameLogic.generatedClasses.GameDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private XmlFileUtils XFU;
    private GameBoard gameBoard;
    private GameDescriptor desc;
    private Map<Integer,Player> playersInMap;
    private List<Players> playersByOrder;

    public GameManager()
    {
        XFU = new XmlFileUtils();
        XFU.setGameManager(this);
        gameBoard = new GameBoard();
        playersByOrder = new ArrayList<>();
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

    public void buildPlayersFromFile()
    {
        playersInMap = new HashMap<>();

    }
}
