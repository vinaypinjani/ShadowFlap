import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;

import java.util.Random;

public abstract class Weapon {
    protected double y;
    protected double x;
    protected static double stepSize = 3;
    protected boolean isCollected;
    protected boolean isShot;
    protected boolean isDestroyed;
    private final double MAX_Y;
    private final double MIN_Y;
    private final double WEAPON_SIDE;
    private static final double MAX_STEP = 5;
    private static final double MIN_STEP = 1;
    protected boolean isBomb;


    public Weapon() {
        x = Window.getWidth();
        MAX_Y = 500;
        MIN_Y = 100;
        WEAPON_SIDE = 32;
        isCollected = false;
        isShot = false;
        isDestroyed = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setDestroyed() {
        isDestroyed = true;
    }

    public void setWeaponShot() {
        isShot = true;
    }

    public void setWeaponCollected() {
        isCollected = true;
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(int frame) {}

    protected void setWeaponY() {
        y = (new Random().nextDouble() * (MAX_Y - MIN_Y)) + MIN_Y;
    }

    protected void drawWeapon(Image image) {
        if (!isDestroyed) {
            image.drawFromTopLeft(x, y);
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, WEAPON_SIDE, WEAPON_SIDE);
    }

    public static void increaseStepSize() {
        double newSize = stepSize + (stepSize * 0.5);
        if (newSize < MAX_STEP) {
            stepSize = newSize;
        }
    }

    public static void decreaseStepSize() {
        double newSize = stepSize - (stepSize * 0.5);
        if (newSize > MIN_STEP) {
            stepSize = newSize;
        }
    }
}
