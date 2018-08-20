package GameUI;

import GameLogic.GameManager;
import javafx.concurrent.Task;

public class LoadFileTask extends Task<Integer> {

    GameManager gameManager;
    String path;

    public LoadFileTask(GameManager game,String pathToCheck)
    {
        gameManager = game;
        path = pathToCheck;
    }

    @Override
    protected Integer call() throws Exception {
        int res = gameManager.checkXmlFile(path);
        sleepForAWhile(3500);
        return res;
    }

    public static void sleepForAWhile(long sleepTime) {
        if (sleepTime != 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {

            }
        }
    }
}
