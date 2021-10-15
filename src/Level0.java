import bagel.Image;

/**
 * This class contains method and attributes particular to level 0. It implements pipe generation and background
 * drawing for level 0.
 */
public class Level0 extends Level {
    private final Image BACKGROUND_IMAGE; // Holds the background image for level 0.
    private final int MAX_SCORE; // Holds the winning score for the level.
    private final int MAX_LIVES; // Holds the maximum lives available in the level.


    /**
     * Constructor initializes the final variables and lives for the level.
     */
    public Level0() {
        this.BACKGROUND_IMAGE = new Image("res/level-0/background.png");
        this.MAX_SCORE = 1;
        this.MAX_LIVES = 3;
        currLives = MAX_LIVES; // Initializes initial lives with max lives
    }

    /**
     * @return MAX_SCORE contains the maximum score for level 0.
     */
    public int getMaxScore() {
        return MAX_SCORE;
    }


    /**
     * Update method is called every frame and generates new pipes and updates the state of those pipes.
     *
     * @param frame contains the current frame of the leve.
     */
    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0); // Draws the background image for the level

        // Checks if the level is in play.
        if (levelStarted && !levelOver) {

            // Adds a new plastic pipe object every 100 frames.
            if (frame % 100 == 0) {
                pipes.add(new PlasticPipe(false));
                pipesInitiated = true;
                numPipes++; // Increments number of pipes
            }

            // updates the state of all pipes made.
            if (pipesInitiated) {
                for (int i = 0; i < numPipes; i++) {
                    pipes.get(i).update(frame);
                }
            }

            // draws the life bar that is updated constantly,
            drawHearts(MAX_LIVES, currLives);

            // Checks if level is won.
            if (levelWon) {
                levelStarted = false;
                levelOver = true;
            }
        }
    }
}
