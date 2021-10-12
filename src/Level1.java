import bagel.Image;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Level1 extends Level {
    private final Image BACKGROUND_IMAGE;
    private final int MAX_SCORE; // Hold the winning score for the level
    private int currLives; // The number of lives for the level
    private final int MAX_LIVES;
    private boolean levelWon; // Indicates if the level is won
    private ArrayList<Pipe> pipes;
    private ArrayList<Weapon> weapons;

    private int numPipes;
    private boolean pipesInitiated;
    private boolean levelStarted;
    private boolean levelOver;
    private int currentPipe;
    private int numWeapons;
    private int currentWeapon;
    private boolean weaponsInitiated;
    private int pickedWeapon;
    private int nextWeapon;
    private int shotIndex;


    public Level1() {
        BACKGROUND_IMAGE = new Image("res/level-1/background.png");
        MAX_SCORE = 30;
        MAX_LIVES = 100;
        currLives = MAX_LIVES;
        levelWon = false;
        pipes = new ArrayList<Pipe>();
        weapons = new ArrayList<Weapon>();
        levelStarted = false;
        levelOver = false;
        this.currentPipe = 0;
        this.numWeapons = 0;
        this.currentWeapon = 0;
        this.weaponsInitiated = false;
        this.pickedWeapon = 0;
        this.nextWeapon = 1;


    }

    public boolean getPipesInitiated() {
        return pipesInitiated;
    }

    public boolean getWeaponsInitiated() {
        return weaponsInitiated;
    }

    public boolean getLevelStarted() {
        return levelStarted;
    }

    public void setLevelStarted() {
        levelStarted = true;
    }


    public Rectangle returnUpperRectangle() {
        if (!pipes.get(currentPipe).isFlameOn()) {
            return pipes.get(currentPipe).getUpperRectangle();
        } else {
            return pipes.get(currentPipe).getFlameUpperRectangle();
        }
    }

    public Rectangle returnLowerRectangle() {
        if (!pipes.get(currentPipe).isFlameOn()) {
            return pipes.get(currentPipe).getLowerRectangle();
        } else {
            return pipes.get(currentPipe).getFlameLowerRectangle();
        }
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
        if (weapons.get(shotIndex).getX() >= pipes.get(currentPipe).getUpperRectangle().left()) {
            if (weapons.get(shotIndex).getY() <= pipes.get(currentPipe).getUpperRectangle().bottom() ||
                    weapons.get(shotIndex).getY() >= pipes.get(currentPipe).getLowerRectangle().top()) {
                if (weapons.get(shotIndex).isBomb() ||
                        (!weapons.get(shotIndex).isBomb && !pipes.get(currentPipe).isSteel())) {
                    if (!weapons.get(shotIndex).isDestroyed()) {
                        pipes.get(currentPipe).destroy();
                        weapons.get(shotIndex).setDestroyed();
                    }

                }


            }
        }
    }


    public void collide() {
        if (currLives > 1) {
            currLives--;
            pipes.remove(currentPipe);
            numPipes--;
        } else {
            levelOver = true;
        }
    }

    public void loseLife() {
        if (currLives > 1) {
            currLives--;
        } else {
            levelOver = true;
        }
    }

    public void setWeaponCollected() {
        pickedWeapon = currentWeapon;
        weapons.get(pickedWeapon).setWeaponCollected();

    }

    public boolean getLevelOver() {
        return levelOver;
    }

    public void updateCurrentPipe() {
        currentPipe++;
    }

    public int getMaxScore() {
        return MAX_SCORE;
    }

    public void setLevelWon() {
        levelWon = true;
    }

    public boolean getLevelWon() {
        return levelWon;
    }

    public void destroyWeapon() {
        if (weapons.get(pickedWeapon).isDestroyed()) {
            weapons.remove(pickedWeapon);
        }
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
                this.pipes.get(i).update(frame);
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
