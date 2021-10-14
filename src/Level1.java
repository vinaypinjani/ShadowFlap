import bagel.Image;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class contains methods and attributes for level 1. It updates pipes and weapons randomly and also implements the
 * shooting of weapons.
 */
public class Level1 extends Level {
    private final Image BACKGROUND_IMAGE; // Holds the background image for level 1.
    private final int MAX_SCORE; // Hold the winning score for the level.
    private final int MAX_LIVES; // Holds the maximum available lives for the level.
    private ArrayList<Weapon> weapons; // Array list holds the weapon objects generated.
    private int numWeapons; // Holds the total number of weapons.
    private int currentWeapon; // Holds the index of the current weapon.
    private boolean weaponsInitiated; // Indicates if the weapon has been initiated.
    private int pickedWeapon; // holds the index of the weapon picked by bird.
    private int shotIndex; // holds the index of the weapon shot by the bird.


    /**
     * Constructor initializes variables and finals with the initial values.
     */
    public Level1() {
        BACKGROUND_IMAGE = new Image("res/level-1/background.png");
        MAX_SCORE = 30;
        MAX_LIVES = 6;
        currLives = MAX_LIVES; // Max available lives at start of level.
        weapons = new ArrayList<Weapon>();
        this.weaponsInitiated = false;
        this.currentWeapon = 0;
        this.numWeapons = 0;

    }

    /**
     * @return weaponInitiated indicates whether the first weapon object is made.
     */
    public boolean getWeaponsInitiated() {
        return weaponsInitiated;
    }

    /**
     * @return Bounding box for the current weapon.
     */
    public Rectangle returnWeaponRectangle() {
        return weapons.get(currentWeapon).getBoundingBox();
    }

    /**
     * Method updates the position of the weapon with bird's coordinates after it has picked the weapon.
     *
     * @param xBird current x coordinate for the bird
     * @param yBird current y coordinate for the bird
     */
    public void setWeaponCoordinates(double xBird, double yBird) {
        weapons.get(pickedWeapon).setPos(xBird, yBird);
    }

    /**
     * Increments the index of the current weapon.
     */
    public void updateCurrentWeapon() {
        currentWeapon++;
    }

    /**
     * Stores the index of the picked weapon and changes state.
     */
    public void setWeaponCollected() {
        pickedWeapon = currentWeapon;



    }

    /**
     * @return MAX_SCORE the winning score for the level.
     */
    public int getMaxScore() {
        return MAX_SCORE;
    }


    /**
     * Stores the index for the shot weapon and changes state.
     */
    public void shootWeapon() {
        shotIndex = pickedWeapon;
        weapons.get(shotIndex).setWeaponShot();
    }

    /**
     * Method tracks a weapon after it has been shot by the bird. It checks for collisions with the pipes/
     */
    public void trackShot() {

        // Checks if weapons x is ahead of pipe's x
        if (weapons.get(shotIndex).getBoundingBox().right() >= pipes.get(currentPipe).getUpperRectangle().left()) {

            // Checks if the weapons y value is not in the gap between the pipes.
            if (weapons.get(shotIndex).getBoundingBox().top() <= pipes.get(currentPipe).getUpperRectangle().bottom() ||
                    weapons.get(shotIndex).getBoundingBox().bottom() >= pipes.get(currentPipe).getLowerRectangle().top()) {

                // Checks if the weapon is capable of destroying the pipe
                if (weapons.get(shotIndex).isBomb() ||
                        (!weapons.get(shotIndex).isBomb && !pipes.get(currentPipe).isSteel())) {
                    // checks if weapon is not already destroyed
                    if (!weapons.get(shotIndex).isDestroyed()) {
                        pipes.remove(currentPipe); // removes the pipe on impact
                        numPipes--; // decrements num pipes
                        ShadowFlap.incrementScore(); //  increases the score
                        weapons.get(shotIndex).setDestroyed(); // destroys the weapon after impact
                    }
                } else if (!weapons.get(shotIndex).isDestroyed()) {
                    weapons.get(shotIndex).setDestroyed(); // destroys the weapon if not compatible
                }
            }
        }
    }


    /**
     * update method is called each frame, it generates and manages states of pipes and weapons.
     *
     * @param frame contains the current frame for the level
     */
    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0); //  Draws the background image

        // Checks if game is in play
        if (levelStarted && !levelOver) {
            // Either a steel pipe or a plastic pipe object is created every 100 frames
            if (frame % 100 == 0) {
                if (new Random().nextBoolean()) {
                    pipes.add(new SteelPipe());
                } else {
                    pipes.add(new PlasticPipe(true));
                }
                numPipes++;
                pipesInitiated = true;
                // Either a rock or a bomb object is created between 2 pipes
            } else if (frame % 50 == 0) {
                if (new Random().nextBoolean()) {
                    weapons.add(new Rock());
                } else {
                    weapons.add(new Bomb());
                }
                numWeapons++;
                weaponsInitiated = true;
            }

            // updates the state of all pipes each frame
            for (int i = 0; i < numPipes; i++) {
                pipes.get(i).update(frame);
            }

            // updates the state of all weapons each frame
            for (int i = 0; i < numWeapons; i++) {
                this.weapons.get(i).update(frame);
            }

            //draws the life bar for the level
            drawHearts(MAX_LIVES, currLives);

            // checks if the level has been completed
            if (levelWon) {
                levelStarted = false;
                levelOver = true;
            }
        }
    }
}
