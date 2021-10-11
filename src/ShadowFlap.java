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
    private final Level0 LEVEL0;
    private final Level1 LEVEL1;

    private final Point WINDOW_CENTRE; // Point for centre of the window
    private final Point SCORE_POINT; // Point at which score is displayed.

    private final String COLLISION_MESSAGE; // String with collision message.
    private final String WIN_MESSAGE; // String with win message.
    private final String SCORE_MESSAGE; // String with score message.
    private final double INITIAL_VELOCITY; // Initial flying velocity for the bird.

    private int frameCounter; // Keeps track of the frame.
    private int pauseFrames;
    private int score; // Keeps track of the score.
    private boolean gameOver; // Indicates if the game is over.
    private boolean gameWin; // Indicates if the game is won.
    private boolean startGame; // Indicates if the game is started.
    private boolean levelUp;


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
        this.LEVEL0 = new Level0();
        this.LEVEL1 = new Level1();
        this.levelUp = false;
        this.pauseFrames = 0;
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
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close(); // Closes window if escape is pressed.
        }
        if (!levelUp) {
            LEVEL0.update(frameCounter);

            if (input.wasPressed(Keys.SPACE)) {
                LEVEL0.setLevelStarted();
            }
            if (!gameOver && !gameWin) {
                if (LEVEL0.getLevelStarted()) {
                    frameCounter++;
                    LEVEL0.printScoreCounter(score);
                    birdActions(input);
                    if (LEVEL0.getPipesInitiated()) {
                        detectCollision();
                        if (LEVEL0.isLevelOver()) {
                            gameOver = true;
                        }
                        if (checkCross()) {
                            score++;
                            if (score >= LEVEL0.getMaxScore()) {
                                LEVEL0.setLevelWon();
                            }
                            LEVEL0.updateCurrentPipe();
                            if (LEVEL0.getLevelWon()) {
                                gameWin = true;
                            }
                        }
                    }
                } else {
                    LEVEL0.printInstructionMessage();
                }
            } else if (gameOver && !gameWin) {
                LEVEL0.printScore(score);
                LEVEL0.printCollisionMessage();
            } else if (gameWin) {
                if (pauseFrames <= 20) {
                    LEVEL0.printLevelUpMessage();
                    LEVEL0.printScore(score);
                    pauseFrames++;
                } else {
                    LEVEL0.setLevelOver();
                    levelUp = true;
                    gameWin = false;
                    frameCounter = 0;
                    score = 0;
                    bird.resetPosition();
                }
            }
        } else {
            LEVEL1.update(frameCounter);
            if (input.wasPressed(Keys.SPACE)) {
                bird.setLevel1();

                LEVEL1.setLevelStarted();
            }
            if (!gameOver && !gameWin) {
                if (LEVEL1.getLevelStarted()) {
                    frameCounter++;
                    LEVEL1.printScoreCounter(score);
                    birdActions(input);
                    if (LEVEL1.getPipesInitiated()) {
                        detectCollision();
                        if (LEVEL1.getLevelOver()) {
                            gameOver = true;
                        }
                        if (checkCross()) {
                            score++;
                            if (score >= LEVEL1.getMaxScore()) {
                                LEVEL1.setLevelWon();
                            }
                            LEVEL1.updateCurrentPipe();
                            if (LEVEL1.getLevelWon()) {
                                gameWin = true;
                            }
                        }
                    }
                } else {
                    LEVEL1.printInstructionMessage();
                    LEVEL1.printShootMessage();
                }
            } else if (gameOver && !gameWin) {
                LEVEL1.printScore(score);
                LEVEL1.printCollisionMessage();
            } else if (gameWin) {
                LEVEL1.printWinMessage();
            }

        }


    }

    public void birdActions(Input input) {
        bird.flap(frameCounter % 10 != 0); // Bird flaps wings every tenth frame.}
        if (input.wasPressed(Keys.SPACE)) {
            bird.fly(); // Bird flies when space bar pressed.
            bird.setVelocity(INITIAL_VELOCITY); // Resets the velocity.
        } else {
            bird.fall(); // Initiates falling if space not pressed.
        }
        if (input.wasPressed(Keys.L)) {
            Pipe.increaseStepSize();
            Weapon.increaseStepSize();
        }
        if (input.wasPressed(Keys.K)) {
            Pipe.decreaseStepSize();
            Weapon.decreaseStepSize();
        }
    }

    public void detectCollision() {
        if (!levelUp) {
            // Checks if the bounding box for both objects intersect.
            if (bird.getRectangle().intersects(LEVEL0.returnUpperRectangle()) ||
                    bird.getRectangle().intersects(LEVEL0.returnLowerRectangle())) {
                LEVEL0.loseLife();
                //LEVEL0.updateCurrentPipe();
                // Indicates game over.
            }
        } else {
            if (bird.getRectangle().intersects(LEVEL1.returnUpperRectangle()) ||
                    bird.getRectangle().intersects(LEVEL1.returnLowerRectangle())) {
                LEVEL1.loseLife();
                //LEVEL1.updateCurrentPipe();
                // Indicates game over.
            }
        }
        // Checks if the bird is out of the window.
        if (bird.getRectangle().centre().y < 0 || bird.getRectangle().centre().y > Window.getHeight()) {
            bird.resetPosition();
            if (!levelUp) {
                LEVEL0.outOfWindow();
            } else {
                LEVEL1.outOfWindow();
            }
        }
    }

    public boolean checkCross() {
        if (!levelUp) {
            return (bird.getRectangle().centre().x > LEVEL0.returnUpperRectangle().right());
        } else {
            return (bird.getRectangle().centre().x > LEVEL1.returnUpperRectangle().right());
        }
    }


}
