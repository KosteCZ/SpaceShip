package cz.koscak.jan.game.spaceship.model;

public class SpaceShip {

    private static final double FORCE_MAX = 1;
    private static final double FORCE_MIN = -0.2;
    private static final double ROTATION_MAX = 1;
    private static final double ROTATION_MIN = -1;
    private static final int TIME_TO_RELOAD_BULLET = 5;
    private double x, y;
    private double force, rotation;
    private double rotationChange;
    private int timeToReloadBullet;

    public SpaceShip(double x, double y) {
        this.x = x;
        this.y = y;
        force = 1;
        rotation = 0;
        rotationChange = 1;
        timeToReloadBullet = 0;
    }

    public int getIntX() {
        return (int) Math.floor(x + 0.5d);
    }

    public int getIntY() {
        return (int) Math.floor(y + 0.5d);
    }

    public double getRotation() {
        return rotation;
    }

    public void move() {
        rotation = rotation + rotationChange;
        if (force < FORCE_MIN) force = FORCE_MIN;
        if (force > FORCE_MAX) force = FORCE_MAX;
        if (rotation < 0)    rotation = rotation + 360;
        if (rotation > 360 ) rotation = rotation - 360;
        //System.out.println("VX: " + (force * Math.cos(Math.toRadians(rotation))));
        //System.out.println("VY: " + (force * Math.sin(Math.toRadians(rotation))));
        x = x + (force * Math.cos(Math.toRadians(rotation)));
        y = y + (force * Math.sin(Math.toRadians(rotation)));

        // Position:
        /*System.out.println("FORCE: " + force + ", ROTATION: " + rotation + ", RC: " + rotationChange);
        System.out.println("SIN: " + Math.sin(Math.toRadians(rotation)) + "COS: " + Math.cos(Math.toRadians(rotation)));
        System.out.println();
        System.out.println("SIN + COS - 1: " + paintCorrectionBasedOnRotation());
        System.out.println();
        System.out.println();*/
    }

    public void decreaseReloadingTime() {
        if (timeToReloadBullet > 0 ) timeToReloadBullet = timeToReloadBullet - 1;
    }

    public void setReloadingTimeForBullet() {
        timeToReloadBullet = TIME_TO_RELOAD_BULLET;
    }

    public double paintCorrectionBasedOnRotation() {
        return (Math.abs( (Math.abs(Math.sin(Math.toRadians(rotation)))) + Math.abs(Math.cos(Math.toRadians(rotation))) - 1)  / 2);
    }

    public void increaseForce() {
         force = force + 0.1;
        if(force > FORCE_MAX) {
            force = FORCE_MAX;
        }
    }

    public void decreaseForce() {
        force = force - 0.1;
        if(force < FORCE_MIN) {
            force = FORCE_MIN;
        }
    }

    public void turnLeft() {
        rotationChange = rotationChange - 0.1;
        if(rotationChange < ROTATION_MIN) {
            rotationChange = ROTATION_MIN;
        }
    }

    public void turnRight() {
        rotationChange = rotationChange + 0.1;
        if(rotationChange > ROTATION_MAX) {
            rotationChange = ROTATION_MAX;
        }
    }

    public int getTimeToReloadBullet() {
        return timeToReloadBullet;
    }
}
