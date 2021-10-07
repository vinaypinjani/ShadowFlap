import bagel.Image;
import bagel.util.Rectangle;
import java.util.ArrayList;


public class Level0 extends Level {
    private final Image BACKGROUND_IMAGE;
    private final String INSTRUCTION_MESSAGE; // String with instruction message.
    private final int MAX_SCORE; // Hold the winning score for the level
    private final int MAX_LIVES;
    private int currLives; // The number of lives for the level
    private boolean levelOver; // Indicates if the level is won
    private boolean levelStarted;
    private boolean levelWon;
    private boolean pipesInitiated;
    private int currentPipe;

    private ArrayList<PlasticPipe> plasticPipes;
    private int numPipes;

    public Level0() {
        this.BACKGROUND_IMAGE = new Image("res/level-0/background.png");
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
        this.MAX_SCORE = 10;
        this.MAX_LIVES = 3;
        this.currLives = 3; // Initializes level
        this.levelOver = false;
        this.levelStarted = false;
        this.pipesInitiated = false;
        this.currentPipe = 0;
        this.levelWon = false;
        this.numPipes = 0;
        this.plasticPipes = new ArrayList<PlasticPipe>();
    }

    public void birdPassed() {
        currentPipe++;

    }

    public void loseLife() {
        if (currLives > 1) {
            currLives--;
            currentPipe++;
        } else {
            levelOver = true;
        }
        System.out.println(currLives);
        System.out.println(levelOver);

    }

    public void setLevelWon() {
        levelWon = true;
    }

    public int getMaxScore() {
        return MAX_SCORE;
    }

    public Rectangle returnUpperRectangle() {
        return plasticPipes.get(currentPipe).getUpperRectangle();
    }

    public Rectangle returnLowerRectangle() {
        return plasticPipes.get(currentPipe).getLowerRectangle();
    }

    public boolean getPipesInitiated() {
        return pipesInitiated;
    }


    public void setLevelOver() {
        levelOver = true;
    }

    public boolean getLevelWon() {
        return levelWon;
    }

    public boolean isLevelOver() {
        return levelOver;
    }

    public void setLevelStarted(boolean levelStarted) {
        this.levelStarted = levelStarted;
    }

    public boolean getLevelStarted() {
        return this.levelStarted;
    }

    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0);

        if (levelStarted && !levelOver) {

            if (frame % 100 == 0) {
                plasticPipes.add(new PlasticPipe(false));
                pipesInitiated = true;
                numPipes++;
            }
            for (int i = 0; i < numPipes; i++) {
                this.plasticPipes.get(i).update();
            }
            drawHearts(MAX_LIVES, currLives);
            if (levelWon) {
                levelStarted = false;
                levelOver = true;
            }
        }

    }
}
