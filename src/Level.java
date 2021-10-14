import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * The abstract class Level holds the common attributes and methods used by the two levels
 * of the game. It also implements the life bar functionality for both levels.
 */
public abstract class Level {
    private final Image EMPTY_HEART; // Holds the image for empty hearts.
    private final Image FULL_HEART; // Holds the image for full heart.
    private final Point WINDOW_CENTRE; // Point for centre of the window
    private final Point SCORE_POINT;
    private final int fontSize; // Font size used for the game.
    private final Font font; // Holds the font used for the game.
    private final String INSTRUCTION_MESSAGE; // String with instruction message.
    private final String COLLISION_MESSAGE; // String with collision message.
    private final String SCORE_MESSAGE; // String with score message.
    private final String LEVEL_UP_MESSAGE; // Message displayed at the end of level 0.
    private final String SCORE_COUNTER; // Score display during the game.
    private final String SHOOT_MESSAGE; // To be shown at the start of level 1.
    private final String WIN_MESSAGE; // To  be displayed at the end of game.

    protected ArrayList<Pipe> pipes; // Array list contains the pipe objects for the level.
    protected int currLives; // Contains the remaining number of lives
    protected boolean pipesInitiated; // Indicates if the pipe objects have been made
    protected int currentPipe; // The index of the current pipe.
    protected int numPipes; // Total number of pipes.
    protected boolean levelStarted; // Indicates if the level is started.
    protected boolean levelOver; // Indicates if the level is lost.
    protected boolean levelWon; // Indicates if the level is won.


    /**
     * Constructed initializes the attributes with their initial values.
     */
    public Level() {
        this.EMPTY_HEART = new Image("res/level/noLife.png");
        this.FULL_HEART = new Image("res/level/fullLife.png");
        this.WINDOW_CENTRE = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        this.SCORE_POINT = new Point(100,100);
        this.fontSize = 48;
        this.font = new Font("res/font/slkscr.ttf", fontSize);
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
        this.COLLISION_MESSAGE = "GAME OVER!";
        this.SCORE_MESSAGE = "FINAL SCORE ";
        this.LEVEL_UP_MESSAGE = "LEVEL-UP!";
        this.SCORE_COUNTER = "SCORE: ";
        this.SHOOT_MESSAGE = "PRESS 'S' TO SHOOT";
        this.WIN_MESSAGE = "CONGRATULATIONS!";
        this.pipes = new ArrayList<Pipe>();
        this.pipesInitiated = false;
        this.currentPipe = 0;
        this.numPipes = 0;
        this.levelWon = false;
        this.levelStarted = false;
        this.levelOver = false;
    }


    /**
    *This method is called when the bird  loses its life by collision or by leaving the screen.
    *@param collision indicates whether the bird has collied with a pipe.
     */
    public void loseLife(boolean collision) {
        // checks if player is not on final life and decrements remaining lives.
        if (currLives > 1) {
            currLives--;
            // Removes current pipe if bird has collided.
            if (collision) {
                pipes.remove(currentPipe);
                numPipes--;
            }
        } else {
            // Indicates level over.
            levelOver = true;
        }
    }

    /**
     * @return pipesInitiated that indicates whether pipes have been made.
     */
    public boolean getPipesInitiated() { return pipesInitiated; }

    /**
     * @return levelStarted indicates whether the current level has started.
     */
    public boolean getLevelStarted() { return levelStarted; }

    /**
     * sets the value of level started to true.
     */
    public void setLevelStarted() { levelStarted = true; }


    /**
     * @return levelOver indicates if the current level is lost.
     */
    public boolean getLevelOver() { return levelOver; }

    /**
     * Updates the index of the current pipe when the bird completes cross.
     */
    public void updateCurrentPipe() { currentPipe++; }

    /**
     * Sets levelWon to true and indicated that the level is completed.
     */
    public void setLevelWon() { levelWon = true; }

    /**
     * @return levelWon indicates whether the current level is won.
     */
    public boolean getLevelWon() { return levelWon; }

    /**
     * sets the value of level over to false. Indicating game over
     */
    public void setLevelOver() { levelOver = true; }


    /**
     * @param string contains the string that is to be displayed at the centre of the screen.
     * @return Point from which the string must be drawn fo it to be centered.
     */
    public Point getStringCentre(String string) {
        double x = WINDOW_CENTRE.x - (font.getWidth(string) / 2); // Gets the x coordinate for message.
        double y = WINDOW_CENTRE.y - (fontSize / 2.0); // Gets the y coordinate for the message.
        return new Point(x, y);
    }

    /**
     * Prints the collision message to be displayed.
     */
    public void printCollisionMessage() {
        font.drawString(COLLISION_MESSAGE,
                getStringCentre(COLLISION_MESSAGE).x, getStringCentre(COLLISION_MESSAGE).y);
    }


    /**
     * Method prints the final score of the game.
     * @param score contains the current score of the game.
     */
    public void printScore(int score) {
        font.drawString(SCORE_MESSAGE + score,
                getStringCentre(SCORE_MESSAGE + score).x, getStringCentre(SCORE_MESSAGE + score).y + 75);
    }

    /**
     * Method prints the level up message at the end of level 0.
     */
    public void printLevelUpMessage() {
        font.drawString(LEVEL_UP_MESSAGE,
                getStringCentre(LEVEL_UP_MESSAGE).x, getStringCentre(LEVEL_UP_MESSAGE).y);
    }

    /**
     * Method prints the instruction method at the start of the level.
     */
    public void printInstructionMessage() {
        font.drawString(INSTRUCTION_MESSAGE, getStringCentre(INSTRUCTION_MESSAGE).x, getStringCentre(INSTRUCTION_MESSAGE).y);
    }


    /**
     * Method prints shooting instructions at the start of level 1.
     */
    public void printShootMessage() {
        font.drawString(SHOOT_MESSAGE, getStringCentre(SHOOT_MESSAGE).x, getStringCentre(SHOOT_MESSAGE).y + 68);
    }


    /**
     * Method prints the score on screen during gameplay.
     * @param score is the current score of the game.
     */
    public void printScoreCounter(int score) {
        font.drawString(SCORE_COUNTER + score, SCORE_POINT.x, SCORE_POINT.y);

    }


    /**
     * Prints winning message at the end of the game.
     */
    public void printWinMessage() {
        font.drawString(WIN_MESSAGE, getStringCentre(WIN_MESSAGE).x, getStringCentre(WIN_MESSAGE).y);
    }


    /**
     * Method draws the life bar on the on the screen during game play and is updated every time a lif is lost.
     * @param maxLives contains the max number of lives for that level.
     * @param remainingLives contains the number of lives remaining.
     */
    public void drawHearts(int maxLives, int remainingLives) {
        final double SPACE = 50; // Space between two hearts.
        final double HEART_Y = 15; // y value for all hearts.
        double x = 100; // Initial x value for the hearts.

        // loop draws the remaining hearts on screen as red hearts
        for (int i = 0; i < remainingLives; i++) {
            FULL_HEART.drawFromTopLeft(x, HEART_Y);
            x = x + SPACE; // increments x value for each heart.
        }
        // loop draws the lives lost as empty hearts
        for (int i = 0; i < maxLives - remainingLives; i++) {
            EMPTY_HEART.drawFromTopLeft(x, HEART_Y);
            x = x + SPACE; // Increments x value for each heart.
        }
    }

    /**
     * @return Bounding box containing the upper pipe
     */
    public Rectangle returnUpperRectangle() {
        // Checks if flames are on for steel pipe.
        if (!pipes.get(currentPipe).isFlameOn()) {
            return pipes.get(currentPipe).getUpperRectangle(); // rectangle without flame.
        } else {
            return pipes.get(currentPipe).  getFlameUpperRectangle(); // rectangle with flame.
        }
    }


    /**
     * @return Bounding box containing the lower pipe
     */
    public Rectangle returnLowerRectangle() {
        // Checks if flame is on for steel pipe.
        if (!pipes.get(currentPipe).isFlameOn()) {
            return pipes.get(currentPipe).getLowerRectangle(); // rectangle without flame.
        } else {
            return pipes.get(currentPipe).getFlameLowerRectangle(); // rectangle with flame.
        }
    }
}
