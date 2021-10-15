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
    private  Level0 LEVEL0; // Holds the level 0 object
    private  Level1 LEVEL1; // Holds the level 1 object
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

    /**
     * the method performs tasks required for level0.
     * @param input is the keys entered by the user each time update is called
     */
    public void level0Actions(Input input) {
        LEVEL0.update(frameCounter); // updates the state of level 1
        if (input.wasPressed(Keys.SPACE)) {
            LEVEL0.setLevelStarted(); // initiates game
        }
        if (score >= LEVEL0.getMaxScore()) {
            LEVEL0.setLevelWon(); // checks if max score reached
        }
        // checks if game is in play state
        if (!LEVEL0.getLevelOver() && !LEVEL0.getLevelWon() && LEVEL0.getLevelStarted()) {
            frameCounter++; // updates frame counter each frame
            LEVEL0.printScoreCounter(score);
            gameActions(input);
            // checks if the first pipe is made
            if (LEVEL0.getPipesInitiated()) {
                detectCollision();
                // if bird crosses a pipe
                if (checkCross()) {
                    incrementScore(); // score is added
                    LEVEL0.updateCurrentPipe(); // next pipe is checked
                }
            }
        // checks if player has lost the level
        } else if (LEVEL0.getLevelOver() && !LEVEL0.getLevelWon()) {
            LEVEL0.printScore(score);
            LEVEL0.printCollisionMessage();
        // checks if player has won the level
        } else if (LEVEL0.getLevelWon()) {
            // pauses screen
            if (pauseFrames <= LEVEL_UP_FRAMES) {
                LEVEL0.printLevelUpMessage();
                LEVEL0.printScore(score);
                pauseFrames++;
            } else {
                levelUp = true; // indicates next level
                // resets common variables for level1
                frameCounter = 0;
                score = 0;
                bird.resetPosition();
            }
        // if player has not started level
        } else {
            LEVEL0.printInstructionMessage();
        }
    }


    /**
     * This method performs tasks required by level 1.
     * @param input is the key entered by the user each frame
     */
    public void level1Actions(Input input) {
        LEVEL1.update(frameCounter); // Updates state of level 1
        Pipe.resetStepSize(); // resets timescale
        if (input.wasPressed(Keys.SPACE)) {
            bird.setLevel1(); // indicates level 1 to bird
            LEVEL1.setLevelStarted(); // indicates level 1 started
        }
        // checks if score has exceeded max score
        if (score >= LEVEL1.getMaxScore()) {
            LEVEL1.setLevelWon();
        }

        // Checks if game is in play state
        if (!LEVEL1.getLevelOver() && !LEVEL1.getLevelWon() && LEVEL1.getLevelStarted()) {
            frameCounter++; // increments frame counter
            LEVEL1.printScoreCounter(score);
            gameActions(input);
            // checks if first pipe is made
            if (LEVEL1.getPipesInitiated()) {
                detectCollision();
                // if bird crosses a pipe
                if (checkCross()) {
                    incrementScore(); // score is updated
                    LEVEL1.updateCurrentPipe(); // next pipe is updated
                }
                // checks if first weapon is initiated
                if(LEVEL1.getWeaponsInitiated()) {
                    collectWeapon();
                }
            }

            // Checks if player presses s when bird has a weapon
            if (input.wasPressed(Keys.S) && bird.getWeaponCollected()) {
                LEVEL1.shootWeapon();
                bird.setWeaponCollected(false); // indicates bird has lost weapon
                bird.setWeaponShot(true); // indicates bird has shot weapon
            }
            // checks if a shot was made by bird
            if (bird.getWeaponShot()) {
                LEVEL1.trackShot(); // checks if the shot makes any damage
            }
        // checks if level is lost
        } else if (LEVEL1.getLevelOver() && !LEVEL1.getLevelWon()) {
            LEVEL1.printScore(score);
            LEVEL1.printCollisionMessage();
        // checks if level is won
        } else if (LEVEL1.getLevelWon()) {
            LEVEL1.printWinMessage();
        // prints instructions if level is not started
        } else {
            LEVEL1.printInstructionMessage();
            LEVEL1.printShootMessage();
        }

    }


    /**
     * This method contains the actions for the games that are made by the input.
     * @param input is the input entered by the player.
     */
    public void gameActions(Input input) {
        bird.flap(frameCounter % 10 != 0); // Bird flaps wings every tenth frame.}
        if (input.wasPressed(Keys.SPACE)) {
            bird.fly(); // Bird flies when space bar pressed.
            bird.setVelocity(INITIAL_VELOCITY); // Resets the velocity.
        } else {
            bird.fall(); // Initiates falling if space not pressed.
        }
        if (input.wasPressed(Keys.L)) {
            Pipe.increaseStepSize(); // Increases time scale for pipes
            if (levelUp) {
                Weapon.increaseStepSize(); // Increases time scale for weapons
            }
        }
        if (input.wasPressed(Keys.K)) {
            Pipe.decreaseStepSize(); // decreases timescale for pipes
            if (levelUp) {
                Weapon.decreaseStepSize(); //  decreases timescale for weapons
            }
        }
    }

    /**
     * Method is used to see if at any frame the bird collides with a pipe or goes out of screen.
     */
    public void detectCollision() {
        // Checks if the bounding box for both objects intersect.
        if (!levelUp) {
            if (bird.getRectangle().intersects(LEVEL0.returnUpperRectangle()) ||
                    bird.getRectangle().intersects(LEVEL0.returnLowerRectangle())) {
                LEVEL0.loseLife(true); // indicates life lost by collision
            }
        } else {
            if (bird.getRectangle().intersects(LEVEL1.returnUpperRectangle()) ||
                    bird.getRectangle().intersects(LEVEL1.returnLowerRectangle())) {
                LEVEL1.loseLife(true); // indicates life lost by collision
            }
        }
        // Checks if the bird is out of the window.
        if (bird.getRectangle().centre().y < 0 || bird.getRectangle().centre().y > Window.getHeight()) {

            // indicates life lost without collision
            if (!levelUp) {
                LEVEL0.loseLife(false);
            } else {
                LEVEL1.loseLife(false);
            }
            bird.resetPosition();
        }
    }

    /**
     * Method is used to search and collect weapons for the bird and update the weapons location accordingly
     */
    public void collectWeapon() {
        // checks if bird already has weapon
        if (!bird.getWeaponCollected()) {
            // if bird overlaps a weapon
            if (LEVEL1.returnWeaponRectangle().intersects(bird.getRectangle())) {
                bird.setWeaponCollected(true); // indicates weapon collected
                bird.setWeaponShot(false);
                LEVEL1.setWeaponCollected();
                LEVEL1.updateCurrentWeapon(); // next weapon is now looked for
            }
        } else {
            // updates the coordinates of the weapon if collected
            LEVEL1.setWeaponCoordinates(bird.getRectangle().topRight().x, bird.getRectangle().topRight().y);
        }

        if (checkWeaponCrossed()) {
            LEVEL1.updateCurrentWeapon(); // looks for next weapon if bird crosses current
        }
    }


    /**
     * @return boolean indicating if bird has successfully crossed a pipe
     */
    public boolean checkCross() {
        // checks if birds x coordinate is ahead of pipe
        if (!levelUp) {
            return (bird.getRectangle().centre().x > LEVEL0.returnUpperRectangle().right());
        } else {
            return (bird.getRectangle().centre().x > LEVEL1.returnUpperRectangle().right());
        }
    }

    /**
     * @return boolean indicating if bird has crossed a weapon
     */
    public boolean checkWeaponCrossed() {
        return (bird.getRectangle().left() > LEVEL1.returnWeaponRectangle().right());
    }

    /**
     * Static method used to increase game score
     */
    public static void incrementScore() {
        score++;
    }
}
