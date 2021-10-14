import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 * <p>
 * Please filling your name below
 *
 * @ author: Vinay Pinjani
 */
public class ShadowFlap extends AbstractGame {

    private final Bird bird; // Holds a bird object for the game.
    private final Level0 LEVEL0; // Holds the level 0 object
    private final Level1 LEVEL1; // Holds the level 1 object
    private final double INITIAL_VELOCITY; // Initial flying velocity for the bird.
    private final int LEVEL_UP_FRAMES; // Number of frames level up screen will stay for

    private int frameCounter; // Keeps track of the frame.
    private int pauseFrames; // keeps track of the frames the game is paused for.
    private static int score = 0; // Keeps track of the score.
    private boolean levelUp; // Indicates if the level 0 has finished


    /**
     * Constructor initializes values for the game.
     */
    public ShadowFlap() {
        this.LEVEL_UP_FRAMES = 150;
        this.INITIAL_VELOCITY = -6.0;
        this.frameCounter = 0;
        this.bird = new Bird();
        this.LEVEL0 = new Level0();
        this.LEVEL1 = new Level1();
        this.levelUp = false;
        this.pauseFrames = 0;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close(); // Closes window if escape is pressed.
        }
        if (!levelUp) {
            level0Actions(input); // Actions for level 0
        } else {
            level1Actions(input); // Actions for level 1
        }
    }

    public void level0Actions(Input input) {
        LEVEL0.update(frameCounter);
        if (input.wasPressed(Keys.SPACE)) {
            LEVEL0.setLevelStarted();
        }
        if (score >= LEVEL0.getMaxScore()) {
            LEVEL0.setLevelWon();
        }
        if (!LEVEL0.getLevelOver() && !LEVEL0.getLevelWon() && LEVEL0.getLevelStarted()) {
            frameCounter++;
            LEVEL0.printScoreCounter(score);
            gameActions(input);
            if (LEVEL0.getPipesInitiated()) {
                detectCollision();
                if (checkCross()) {
                    incrementScore();
                    LEVEL0.updateCurrentPipe();
                }
            }

        } else if (LEVEL0.getLevelOver() && !LEVEL0.getLevelWon()) {
            LEVEL0.printScore(score);
            LEVEL0.printCollisionMessage();
        } else if (LEVEL0.getLevelWon()) {
            if (pauseFrames <= LEVEL_UP_FRAMES) {
                LEVEL0.printLevelUpMessage();
                LEVEL0.printScore(score);
                pauseFrames++;
            } else {
                levelUp = true;
                frameCounter = 0;
                score = 0;
                bird.resetPosition();
            }
        } else {
            LEVEL0.printInstructionMessage();
        }
    }


    public void level1Actions(Input input) {
        LEVEL1.update(frameCounter);
        Pipe.resetStepSize();
        if (input.wasPressed(Keys.SPACE)) {
            bird.setLevel1();
            LEVEL1.setLevelStarted();
        }
        if (score >= LEVEL1.getMaxScore()) {
            LEVEL1.setLevelWon();
        }

        if (!LEVEL1.getLevelOver() && !LEVEL1.getLevelWon() && LEVEL1.getLevelStarted()) {
            frameCounter++;
            LEVEL1.printScoreCounter(score);
            gameActions(input);
            if (LEVEL1.getPipesInitiated() && LEVEL1.getWeaponsInitiated()) {
                detectCollision();
                collectWeapon();
                if (checkCross()) {
                    incrementScore();
                    LEVEL1.updateCurrentPipe();
                }
                if (input.wasPressed(Keys.S) && bird.getWeaponCollected()) {
                    LEVEL1.shootWeapon();
                    bird.setWeaponCollected(false);
                }
                LEVEL1.trackShot();
            }


        } else if (LEVEL1.getLevelOver() && !LEVEL1.getLevelWon()) {
            LEVEL1.printScore(score);
            LEVEL1.printCollisionMessage();
        } else if (LEVEL1.getLevelWon()) {
            LEVEL1.printWinMessage();
        } else {
            LEVEL1.printInstructionMessage();
            LEVEL1.printShootMessage();
        }

    }


    public void gameActions(Input input) {
        bird.flap(frameCounter % 10 != 0); // Bird flaps wings every tenth frame.}
        if (input.wasPressed(Keys.SPACE)) {
            bird.fly(); // Bird flies when space bar pressed.
            bird.setVelocity(INITIAL_VELOCITY); // Resets the velocity.
        } else {
            bird.fall(); // Initiates falling if space not pressed.
        }
        if (input.wasPressed(Keys.L)) {
            Pipe.increaseStepSize();
            if (levelUp) {
                Weapon.increaseStepSize();
            }
        }
        if (input.wasPressed(Keys.K)) {
            Pipe.decreaseStepSize();
            if (levelUp) {
                Weapon.decreaseStepSize();
            }
        }
    }

    public void detectCollision() {
        if (!levelUp) {
            // Checks if the bounding box for both objects intersect.
            if (bird.getRectangle().intersects(LEVEL0.returnUpperRectangle()) ||
                    bird.getRectangle().intersects(LEVEL0.returnLowerRectangle())) {
                LEVEL0.loseLife(true);
            }
        } else {
            if (bird.getRectangle().intersects(LEVEL1.returnUpperRectangle()) ||
                    bird.getRectangle().intersects(LEVEL1.returnLowerRectangle())) {
                LEVEL1.loseLife(true);
            }
        }
        // Checks if the bird is out of the window.
        if (bird.getRectangle().centre().y < 0 || bird.getRectangle().centre().y > Window.getHeight()) {

            if (!levelUp) {
                LEVEL0.loseLife(false);
            } else {
                LEVEL1.loseLife(false);
            }
            bird.resetPosition();
        }
    }

    public void collectWeapon() {

        if (!bird.getWeaponCollected()) {
            if (LEVEL1.returnWeaponRectangle().intersects(bird.getRectangle())) {
                bird.setWeaponCollected(true);
                LEVEL1.setWeaponCollected();
                LEVEL1.updateCurrentWeapon();
            }
        } else {
            LEVEL1.setWeaponCoordinates(bird.getRectangle().topRight().x, bird.getRectangle().topRight().y);
        }

        if (checkWeaponCrossed()) {
            LEVEL1.updateCurrentWeapon();
        }
    }


    public boolean checkCross() {
        if (!levelUp) {
            return (bird.getRectangle().centre().x > LEVEL0.returnUpperRectangle().right());
        } else {
            return (bird.getRectangle().centre().x > LEVEL1.returnUpperRectangle().right());
        }
    }

    public boolean checkWeaponCrossed() {
        return (bird.getRectangle().left() > LEVEL1.returnWeaponRectangle().right());
    }

    public static void incrementScore() {
        score++;
    }
}
