import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * Weapon class contains the common attributes and methods for the weapons in the game.
 */
public abstract class Weapon {
    private final double MAX_Y; // Max y value for weapon
    private final double MIN_Y; // Min y value for the weapon
    private final double WEAPON_SIDE; // Contains the width/length of the weapon in pixels
    private static final double MAX_STEP = 5; //  Max step size for weapon
    private static final double MIN_STEP = 1; // Min step size for weapon
    protected static double stepSize = 3; //  current step size for weapon
    protected final double SHOT_PIXELS; // Holds the number of pixels to be increased after being shot.
    protected double y; // current y position for the weapon object
    protected double x; // current x position for the weapon object
    protected int lastFrame; // holds the last frame before being shot
    protected boolean isShot; // indicates if the weapon is shot
    protected boolean isDestroyed; // indicates if the weapon is destroyed
    protected boolean isBomb; // indicates if the weapon type is bomb



    /**
     * Constructor initializes values for the object
     */
    public Weapon() {
        MAX_Y = 500;
        MIN_Y = 100;
        WEAPON_SIDE = 32;
        SHOT_PIXELS = 5;

        x = Window.getWidth();
        isShot = false;
        isDestroyed = false;
    }


    /**
     * @return isDestroyed indicates whether the weapon is destroyed
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * @return isBomb indicates if weapon is bomb
     */
    public boolean isBomb() {
        return isBomb;
    }

    /**
     * Changes the state of weapon to destroyed
     */
    public void setDestroyed() {
        isDestroyed = true;
    }

    /**
     * Changes the state of weapon to shot
     */
    public void setWeaponShot() {
        isShot = true;
    }


    /**
     * Method updates the position of the bird after it has been collected by the bird.
     * @param x position of weapon
     * @param y position of weapon
     */
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Abstract method updates the state of the weapon
     * @param frame current frame
     */
    public abstract void update(int frame);

    /**
     * generates a random y value for the weapon
     */
    protected void setWeaponY() {
        y = (new Random().nextDouble() * (MAX_Y - MIN_Y)) + MIN_Y;
    }

    /**
     * Method draws the weapon on current coordinates if not destroyed
     * @param image of the variable
     */
    protected void drawWeapon(Image image) {
        if (!isDestroyed) {
            image.drawFromTopLeft(x, y);
        }
    }

    /**
     * @return the bounding box of the weapon object
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, WEAPON_SIDE, WEAPON_SIDE);
    }

    /**
     * Static method that increase the step size of all weapons.
     */
    public static void increaseStepSize() {
        double newSize = stepSize + (stepSize * 0.5);
        if (newSize < MAX_STEP) {
            stepSize = newSize;
        }
    }

    /**
     * Static method that decrease the step size of all weapons.
     */
    public static void decreaseStepSize() {
        double newSize = stepSize - (stepSize * 0.5);
        if (newSize > MIN_STEP) {
            stepSize = newSize;
        }
    }
}
