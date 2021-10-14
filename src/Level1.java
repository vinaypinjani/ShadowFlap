import bagel.Image;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Level1 extends Level {
    private final Image BACKGROUND_IMAGE;
    private final int MAX_SCORE; // Hold the winning score for the level
    private final int MAX_LIVES;
    private ArrayList<Weapon> weapons;
    private int numWeapons;
    private int currentWeapon;
    private boolean weaponsInitiated;
    private int pickedWeapon;
    private int shotIndex;


    public Level1() {
        BACKGROUND_IMAGE = new Image("res/level-1/background.png");
        MAX_SCORE = 30;
        MAX_LIVES = 6;
        currLives = MAX_LIVES;
        weapons = new ArrayList<Weapon>();
        this.weaponsInitiated = false;
        this.pickedWeapon = 0;
    }

    public boolean getWeaponsInitiated() {
        return weaponsInitiated;
    }

    public Rectangle returnWeaponRectangle() {
        return weapons.get(currentWeapon).getBoundingBox();
    }

    public void setWeaponCoordinates(double xBird, double yBird) {
        weapons.get(pickedWeapon).setPos(xBird, yBird);
    }

    public void updateCurrentWeapon() {
        currentWeapon++;
    }


    public void shootWeapon() {
        shotIndex = pickedWeapon;
        weapons.get(shotIndex).setWeaponShot();
    }

    public void trackShot() {
        if (weapons.get(shotIndex).getBoundingBox().right() >= pipes.get(currentPipe).getUpperRectangle().left()) {
            if (weapons.get(shotIndex).getBoundingBox().top() <= pipes.get(currentPipe).getUpperRectangle().bottom() ||
                    weapons.get(shotIndex).getBoundingBox().bottom() >= pipes.get(currentPipe).getLowerRectangle().top()) {
                if (weapons.get(shotIndex).isBomb() ||
                        (!weapons.get(shotIndex).isBomb && !pipes.get(currentPipe).isSteel())) {
                    if (!weapons.get(shotIndex).isDestroyed()) {
                        pipes.remove(currentPipe);
                        numPipes--;
                        ShadowFlap.incrementScore();
                        weapons.get(shotIndex).setDestroyed();
                    }
                } else if (!weapons.get(shotIndex).isDestroyed()) {
                    weapons.get(shotIndex).setDestroyed();
                }
            }
        }
    }

    public void setWeaponCollected() {
        pickedWeapon = currentWeapon;
        weapons.get(pickedWeapon).setWeaponCollected();
    }

    public int getMaxScore() {
        return MAX_SCORE;
    }

    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0);

        if (levelStarted && !levelOver) {
            if (frame % 100 == 0) {
                if (new Random().nextBoolean()) {
                    pipes.add(new SteelPipe());
                } else {
                    pipes.add(new PlasticPipe(true));
                }
                pipesInitiated = true;
                numPipes++;
            }
            if (frame % 50 == 0 && frame % 100 != 0) {

                if (new Random().nextBoolean()) {
                    weapons.add(new Rock());
                } else {
                    weapons.add(new Bomb());
                }
                weaponsInitiated = true;
                numWeapons++;
            }
            for (int i = 0; i < numPipes; i++) {
                pipes.get(i).update(frame);
            }
            for (int i = 0; i < numWeapons; i++) {
                this.weapons.get(i).update(frame);
            }
            drawHearts(MAX_LIVES, currLives);

            if (levelWon) {
                levelStarted = false;
                levelOver = true;
            }
        }
    }
}
