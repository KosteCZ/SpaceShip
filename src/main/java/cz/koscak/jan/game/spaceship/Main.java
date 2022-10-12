package cz.koscak.jan.game.spaceship;

import cz.koscak.jan.game.spaceship.gui.GameFrame;
import cz.koscak.jan.game.spaceship.model.Game;

import javax.swing.*;

public class Main {
    private static final boolean DEBUG_MODE = true; //false;
    public static void main(String[] args) {
        resetScreenScalingOnWindowsOS();

        Game game = new Game();
        game.newGame(DEBUG_MODE);

        GameFrame gameFrame = new GameFrame(game);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Main main = new Main();
        main.runGame(gameFrame, game);
    }

    private static void resetScreenScalingOnWindowsOS() {
        System.setProperty("sun.java2d.uiScale", "1.0");
    }

    private void runGame(GameFrame gameFrame, Game game) {
        Thread threadUI = new Thread(() -> {
            try {
                while (true) {
                    gameFrame.repaint();
                    Thread.sleep(50);
                }
            } catch (InterruptedException exception) {
                System.err.println("ERROR: " + exception.getMessage());
                exception.printStackTrace();
            }
        });
        threadUI.start();
        Thread threadGame = new Thread(() -> {
            try {
                while (true) {
                    game.play();
                    Thread.sleep(game.getSpeedDelay());
                }
            } catch (InterruptedException exception) {
                System.err.println("ERROR: " + exception.getMessage());
                exception.printStackTrace();
            }
        });
        threadGame.start();
    }

}