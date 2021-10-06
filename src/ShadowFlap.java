import bagel.*;
import bagel.util.Point;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 * <p>
 * Please filling your name below
 *
 * @ author: Vinay Pinjani
 */
public class ShadowFlap extends AbstractGame {
    //private final Image backgroundImage; // Holds the background image for the game.
    //private final int fontSize; // Font size used for the game.
    //private final Font font; // Holds the font used for the game.

    private final Bird bird; // Holds a bird object for the game.
    //private final Pipe pipe; // Holds a pipe object for the game.
    private final Level0 LEVEL;

    private final Point WINDOW_CENTRE; // Point for centre of the window
    private final Point SCORE_POINT; // Point at which score is displayed.

    private final String COLLISION_MESSAGE; // String with collision message.
    private final String WIN_MESSAGE; // String with win message.
    private final String SCORE_MESSAGE; // String with score message.
    private final double INITIAL_VELOCITY; // Initial flying velocity for the bird.

    private int frameCounter; // Keeps track of the frame.
    private int score; // Keeps track of the score.
    private boolean gameOver; // Indicates if the game is over.
    private boolean gameWin; // Indicates if the game is won.
    private boolean startGame; // Indicates if the game is started.


    public ShadowFlap() {
        //this.backgroundImage = new Image("res/background.png");
        //this.fontSize = 48;
        //this.font = new Font("res/slkscr.ttf", fontSize);
        this.WINDOW_CENTRE = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        this.SCORE_POINT = new Point(100, 100);

        this.COLLISION_MESSAGE = "GAME OVER!";
        this.WIN_MESSAGE = "CONGRATULATIONS!";
        this.SCORE_MESSAGE = "FINAL SCORE ";
        this.INITIAL_VELOCITY = -6.0;

        this.frameCounter = 0;
        this.score = 0;
        this.gameOver = false;
        this.gameWin = false;
        this.startGame = false;

        this.bird = new Bird();
        this.LEVEL = new Level0();
        //this.pipe = new Pipe();

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
        LEVEL.update(frameCounter);
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close(); // Closes window if escape is pressed.
        }

        if (input.wasPressed(Keys.SPACE)) {
            LEVEL.setLevelStarted(true);
        }


        if (!gameOver && !gameWin) {
            if (LEVEL.getLevelStarted()) {


                frameCounter++;
                if (LEVEL.getPipesInitiated()) {

                    detectCollision();
                    if (LEVEL.isLevelOver()) {
                        gameOver = true;
                    }
                    if (checkCross()) {
                        score++;
                        if (score >= LEVEL.getMaxScore()) {
                            LEVEL.setLevelWon();
                        }
                        LEVEL.birdPassed();
                        if (LEVEL.getLevelWon()) {
                            gameWin = true;
                        }
                    }
                    LEVEL.printScoreCounter(score);
                }

                bird.flap(frameCounter % 10 != 0); // Bird flaps wings every tenth frame.}
                if (input.wasPressed(Keys.SPACE)) {
                    bird.fly(); // Bird flies when space bar pressed.
                    bird.setVelocity(INITIAL_VELOCITY); // Resets the velocity.
                } else {
                    bird.fall(); // Initiates falling if space not pressed.
                }
            } else {
                LEVEL.printInstructionMessage();
            }
        } else if (gameOver && !gameWin) {
            LEVEL.printScore(score);
            LEVEL.printCollisionMessage();
        } else if (gameWin) {
            LEVEL.setLevelOver();
            LEVEL.printLevelUpMessage();
        }


    }

    public void detectCollision() {
        // Checks if the bounding box for both objects intersect.
        if (bird.getRectangle().intersects(LEVEL.returnUpperRectangle()) ||
                bird.getRectangle().intersects(LEVEL.returnLowerRectangle())) {

                LEVEL.loseLife();
            // Indicates game over.
        }
        // Checks if the bird is out of the window.
        if (bird.getRectangle().centre().y < 0 || bird.getRectangle().centre().y > Window.getHeight()) {
            gameOver = true; // Indicates game over.
        }
    }

    public boolean checkCross() {
        return bird.getRectangle().centre().x > LEVEL.returnUpperRectangle().right();
    }


}
