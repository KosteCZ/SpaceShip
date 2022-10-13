package cz.koscak.jan.game.spaceship.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int GAME_SPEED_DELAY_DEFAULT = 80;
    private List<SpaceShip> listOfSpaceShips = new ArrayList<>();
    /*private List<Planet> listOfPlanets = new ArrayList<>();*/

    private boolean debugMode = false;

    private GameStatus gameStatus = GameStatus.PAUSED;

    private long time = 0;
    private int speed = 2;

    public void newGame() {
        newGame(false);
    }
    public void newGame(boolean debugMode) {
        setDebugMode(debugMode);

        time = 0;

        if (!(GameStatus.PLAY.equals(gameStatus) || GameStatus.PAUSED.equals(gameStatus))) {
            gameStatus = GameStatus.PAUSED;
        }

        SpaceShip spaceShip = new SpaceShip(400, 400);
        listOfSpaceShips.add(spaceShip);

        checkVictoryCondition();
    }

    public void play() {
        if (!GameStatus.PLAY.equals(gameStatus)) {
            return;
        }
        time = time + 1;

        for (SpaceShip spaceShip: listOfSpaceShips) {
            spaceShip.move();
        }
        /*for (Human human: listOfHumans) {
            human.doAction(this, time, listOfViruses);
        }*/
    }

    private void checkVictoryCondition() {
        /*if (dead >= 10) {
            gameStatus = GameStatus.DEFEAT;
            return;
        }
        for (Human human: listOfHumans) {
            if (!(HumanState.HEALTHY.equals(human.getState()) || HumanState.DEAD.equals(human.getState()))) return;
        }
        gameStatus = GameStatus.VICTORY;*/
    }

    public int getSpeedDelay() {
        return GAME_SPEED_DELAY_DEFAULT / ((int) Math.pow(2, speed));
    }

    public String getSpeedForUI() {
        if (speed > 1) return "  " + (speed - 1);
        return "0.5";
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public long getTime() {
        return time;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<SpaceShip> getListOfSpaceShips() {
        return listOfSpaceShips;
    }

}
