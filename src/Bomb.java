import bagel.Image;

public class Bomb extends Weapon {
    private final Image BOMB_IMAGE;
    private final double RANGE;
    private int lastFrame;

    public Bomb() {
        BOMB_IMAGE = new Image("res/level-1/bomb.png");
        super.setWeaponY();
        RANGE = 50;
        isBomb = true;
    }

    @Override
    public void update(int frame) {
        super.drawWeapon(BOMB_IMAGE);
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
