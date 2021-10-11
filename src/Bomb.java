import bagel.Image;

public class Bomb extends Weapon{
    private final Image BOMB_IMAGE;

    public Bomb() {
        BOMB_IMAGE = new Image("res/level-1/bomb.png");
        super.setWeaponY();
    }

    public void update() {

        super.drawWeapon(BOMB_IMAGE);
        if (!isCollected) {
            x -= stepSize;
        } else if (isShot) {
        x += 5;
    }

    }
}
