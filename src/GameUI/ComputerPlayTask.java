package GameUI;

import GameLogic.GameManager;
import javafx.concurrent.Task;

public class ComputerPlayTask extends Task<Integer> {

    GameManager gameManager;
    int ChoosenCol;

    public ComputerPlayTask(GameManager game) {
        gameManager = game;
    }


    @Override
    protected Integer call() throws Exception {
        if (Thread.currentThread().getName().equals("loaderThread")) {
            ChoosenCol = gameManager.ComputerPlay();
            sleepForAWhile(1500);
        }
        return ChoosenCol;
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
