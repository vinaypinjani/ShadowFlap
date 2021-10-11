import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;

import java.util.Random;

public abstract class Weapon {
    protected double y;
    protected double x;
    protected static double stepSize = 3;

    private final double MAX_Y;
    private final double MIN_Y;
    private final double WEAPON_SIDE;
    private static final double MAX_STEP = 5;
    private  static final double MIN_STEP = 1;


    public Weapon() {
        x = Window.getWidth();
        MAX_Y = 500;
        MIN_Y = 100;
        WEAPON_SIDE = 32;

    }

    public void update() {

    }

    protected void setWeaponY() {
        y =  (new Random().nextDouble() * (MAX_Y - MIN_Y)) + MIN_Y;
    }

    protected void drawWeapon(Image image) {
        image.drawFromTopLeft(x, y);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, WEAPON_SIDE, WEAPON_SIDE);
    }

    public static void increaseStepSize() {
        double newSize = stepSize + (stepSize*0.5);
        if (newSize < MAX_STEP) {
            stepSize = newSize;
        }
    }

    public static void decreaseStepSize() {
        double newSize = stepSize - (stepSize*0.5);
        if (newSize > MIN_STEP) {
            stepSize = newSize;
        }
    }




}
