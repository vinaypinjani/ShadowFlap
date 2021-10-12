import bagel.Image;

public class Rock extends Weapon{
    private Image ROCK_IMAGE;
    private final int RANGE;
    private int lastFrame;

    public Rock() {
        ROCK_IMAGE = new Image("res/level-1/rock.png");
        super.setWeaponY();
        RANGE = 25;
        isBomb = false;

    }

    public void update(int frame) {

        super.drawWeapon(ROCK_IMAGE);
        if (!isShot) {
            x -= stepSize;
            lastFrame = frame;
        } else if (frame - lastFrame <= RANGE) {
            x += 5;
        } else {
            isDestroyed = true;
        }
    }
}
