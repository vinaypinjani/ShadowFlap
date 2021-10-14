import bagel.Image;

/**
 * Classes contains attributes and methods specific to bomb.
 */
public class Bomb extends Weapon {
    private final Image BOMB_IMAGE; // Holds the bomb image
    private final double RANGE; // Holds the range for bomb after being shot

    public Bomb() {
        BOMB_IMAGE = new Image("res/level-1/bomb.png");
        super.setWeaponY();
        RANGE = 50;
        isBomb = true; // true for bomb class
    }

    /**
     * method draws a weapon and changes motion according to state.
     * @param frame current frame
     */
    @Override
    public void update(int frame) {
        super.drawWeapon(BOMB_IMAGE);
        // Checks id weapon is shot
        if (!isShot) {
            x -= stepSize;
            lastFrame = frame;
            // checks if range is not exceeded
        } else if (frame - lastFrame <= RANGE) {
            x += 5; // increments x at 5 pixels per frame
        } else {
            isDestroyed = true; //  sets state to destroyed when range reached
        }

    }
}
