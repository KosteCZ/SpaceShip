package cz.koscak.jan.game.spaceship.model;

import cz.koscak.jan.game.spaceship.gui.GameFrame;
import cz.koscak.jan.game.spaceship.gui.GamePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Game {

    private static final int GAME_SPEED_DELAY_DEFAULT = 80;
    private List<SpaceShip> listOfSpaceShips = new ArrayList<>();
    private List<Bullet> listOfBullets = new ArrayList<>();
    private List<Planet> listOfPlanets = new ArrayList<>();

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

        listOfSpaceShips = new ArrayList<>();
        listOfBullets = new ArrayList<>();
        listOfPlanets = new ArrayList<>();

        SpaceShip spaceShip = new SpaceShip(400, 400);
        listOfSpaceShips.add(spaceShip);

        listOfPlanets.add(new Planet(200 - 15, GamePanel.TOP_BAR + 200 - 15, 30));
        listOfPlanets.add(new Planet(600 - 15, GamePanel.TOP_BAR + 200 - 15, 30));
        listOfPlanets.add(new Planet(200 - 15, GamePanel.TOP_BAR + 600 - 15, 30));
        listOfPlanets.add(new Planet(600 - 15, GamePanel.TOP_BAR + 600 - 15, 30));

        checkVictoryCondition();
    }

    public void play() {
        if (!GameStatus.PLAY.equals(gameStatus)) {
            return;
        }
        time = time + 1;

        for (SpaceShip spaceShip: listOfSpaceShips) {
            spaceShip.decreaseReloadingTime();
            spaceShip.move();
        }
        moveBulletsAndRemoveOld();
        /*for (Human human: listOfHumans) {
            human.doAction(this, time, listOfViruses);
        }*/
    }

    private void moveBulletsAndRemoveOld() {
        ListIterator<Bullet> iteratorBullet = listOfBullets.listIterator();
        while(iteratorBullet.hasNext()){
            if(!iteratorBullet.next().move()){
                iteratorBullet.remove();
                if (isDebugMode()) System.out.println("Bullet removed...");
            }
        }
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

    public List<Bullet> getListOfBullets() {
        return listOfBullets;
    }

    public List<Planet> getListOfPlanets() {
        return listOfPlanets;
    }
}
