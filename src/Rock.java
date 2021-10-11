import bagel.Image;

public class Rock extends Weapon{
    private Image ROCK_IMAGE;

    public Rock() {
        ROCK_IMAGE = new Image("res/level-1/rock.png");
        super.setWeaponY();
    }

    public void update() {

        super.drawWeapon(ROCK_IMAGE);
        if (!isCollected) {
            x -= stepSize;
        } else if (isShot) {
            x += 5;
        }
    }
}
