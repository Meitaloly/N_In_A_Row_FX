package GameUI;

import GameLogic.GameManager;
import javafx.concurrent.Task;

public class LoadFileTask extends Task<Integer> {

    GameManager gameManager;
    String path;

    public LoadFileTask(GameManager game, String pathToCheck) {
        gameManager = game;
        path = pathToCheck;
    }

    @Override
    protected Integer call() throws Exception {
        int res = -1;
        Object lock1 = new Object();
        synchronized (lock1) {
            if(Thread.currentThread().getName().equals("loaderThread")) {
                res = gameManager.checkXmlFile(path);
                sleepForAWhile(1800);
            }
        }
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
