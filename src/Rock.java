import bagel.Image;

/**
 * Classes contains attributes and methods specific to rock.
 */
public class Rock extends Weapon{
    private Image ROCK_IMAGE; // Holds the rock image
    private final int RANGE; // The range of rock shot in frames


    /**
     * Constructor initializes all values for rock
     */
    public Rock() {
        ROCK_IMAGE = new Image("res/level-1/rock.png");
        super.setWeaponY();
        RANGE = 25;
        isBomb = false; // false for rock class
    }

    /**
     * method draws a weapon and changes motion according to state.
     * @param frame current frame
     */
    @Override
    public void update(int frame) {
        super.drawWeapon(ROCK_IMAGE);
        // Checks id weapon is shot
        if (!isShot) {
            x -= stepSize;
            lastFrame = frame;
        // checks if range is not exceeded
        } else if (frame - lastFrame <= RANGE) {
            x += SHOT_PIXELS; // increments x at 5 pixels per frame
        } else {
            isDestroyed = true; //  sets state to destroyed when range reached
        }
    }
}
