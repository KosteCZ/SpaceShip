package cz.koscak.jan.game.spaceship.model;

import cz.koscak.jan.game.spaceship.gui.GamePanel;

public class Bullet {

    private double x, y;
    private double rotation;
    private int timeToDeath;
    public static final int TIME_TO_DEAD_INFINITE = -1;
    public static final int TIME_TO_DEAD_MEDIUM = 150;

    public Bullet(double x, double y, double rotation, int timeToDeath) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.timeToDeath = timeToDeath;
    }

    public int getIntX() {
        return (int) Math.floor(x + 0.5d);
    }

    public int getIntY() {
        return (int) Math.floor(y + 0.5d);
    }

    public boolean move() {
        if (timeToDeath != TIME_TO_DEAD_INFINITE) timeToDeath = timeToDeath - 1;
        x = x + Math.cos(Math.toRadians(rotation)) * 2;
        y = y + Math.sin(Math.toRadians(rotation)) * 2;
        if (x < 0 || x > GamePanel.WIDTH || y < GamePanel.TOP_BAR || y > GamePanel.HEIGHT) return false;
        if (timeToDeath == 0) return false;
        return true;
    }
}
