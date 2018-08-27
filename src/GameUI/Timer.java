package GameUI;

import GameLogic.GameManager;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class Timer extends TimerTask {

    private GameManager gameManager;
    private GameController gameController;

    public Timer(GameManager gm, GameController gc)
    {
        gameController = gc;
        gameManager = gm;
    }

    @Override
    public void run() {
        gameController.setTimer(gameManager.getCurrTime());
    }
}
