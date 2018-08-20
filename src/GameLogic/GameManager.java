package GameLogic;

public class GameManager {

    private XmlFileUtils XFU;
    private GameBoard gameBoard;

    public GameManager()
    {
        XFU = new XmlFileUtils();
        gameBoard = new GameBoard();
    }

    public int checkXmlFile(String path)
    {
        XFU.setFilePath(path);
        return XFU.checkXmlFileValidation(gameBoard);
    }


}
