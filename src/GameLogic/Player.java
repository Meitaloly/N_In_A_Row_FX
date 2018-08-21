package GameLogic;

public class Player {
    private Integer id;
    private String name;
    private int playerColor;
    private String playerType;
    private int turnCounter = 0;

    public Integer getId() {
        return id;
    }

    public int getPlayerSign() {
        return playerColor;
    }

    public String getPlayerType() {
        return playerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void incTurnCounter()
    {
        this.turnCounter++;
    }

    public void setTurnCounter(int num) {
        this.turnCounter = num;
    }

    public void reduceTurnCounter(){
        this.turnCounter--;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void restNumOfTurns()
    {
        turnCounter = 0;
    }
}