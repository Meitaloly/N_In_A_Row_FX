package GameUI;

import GameLogic.ComputerChoice;
import GameLogic.GameManager;
import javafx.concurrent.Task;

public class ComputerPlayTask extends Task<ComputerChoice> {

    GameManager gameManager;
    int ChoosenCol;

    public ComputerPlayTask(GameManager game) {
        gameManager = game;
    }


    @Override
    protected ComputerChoice call() throws Exception {
            ComputerChoice computerChoice = gameManager.ComputerPlay();
            sleepForAWhile(1500);
            return computerChoice;
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
