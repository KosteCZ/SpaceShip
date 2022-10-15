package cz.koscak.jan.game.spaceship.model;

public class Planet {

    private int x, y;
    private int diameter;

    public Planet(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }
}
