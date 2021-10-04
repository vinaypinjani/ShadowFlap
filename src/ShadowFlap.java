import bagel.*;
import bagel.util.Point;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
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
    private final String INSTRUCTION_MESSAGE; // String with instruction message.
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
        this.WINDOW_CENTRE  = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        this.SCORE_POINT = new Point(100, 100);
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
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

        if (input.wasPressed(Keys.SPACE)) {
            LEVEL.setLevelStarted(true);
        }
        if (LEVEL.getLevelStarted()) {
            frameCounter++;
        }
        LEVEL.update(frameCounter);

    }

}
