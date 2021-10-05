import bagel.Image;
import bagel.util.Rectangle;

import java.util.Random;

public class Level0 extends Level {
    private final Image BACKGROUND_IMAGE;
    private final String INSTRUCTION_MESSAGE; // String with instruction message.
    private int MAX_SCORE; // Hold the winning score for the level
    private int numHearts; // The number of lives for the level
    private boolean levelOver; // Indicates if the level is won
    private boolean levelStarted;
    private boolean pipesInitiated;
    private int currentPipe;

    private PlasticPipe[] plasticPipes;
    private int numPipes;

    public Level0() {
        this.BACKGROUND_IMAGE = new Image("res/level-0/background.png");
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
        this.MAX_SCORE = 10;
        this.numHearts = 3; // Initializes level
        this.levelOver = false;
        this.levelStarted = false;
        this.pipesInitiated = false;
        this.currentPipe = 0;

        this.numPipes = 0;
        this.plasticPipes = new PlasticPipe[MAX_SCORE];
    }

    public void birdPassed() {
        currentPipe++;
    }

    public Rectangle returnUpperRectangle() {
       return plasticPipes[currentPipe].getUpperRectangle();
    }

    public Rectangle returnLowerRectangle() {
        return plasticPipes[currentPipe].getLowerRectangle();
    }

    public boolean getPipesInitiated() {return pipesInitiated;}

    public double returnPipeEnd() {
        return plasticPipes[currentPipe].pipeEnd();
    }
    public void setLevelOver() {
        levelOver = true;
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
                plasticPipes[numPipes] = new PlasticPipe();
                plasticPipes[numPipes].update();
                pipesInitiated = true;
                numPipes++;
            }

            for (int i = 0; i < numPipes; i++) {
                this.plasticPipes[i].update();
            }


            if (currentPipe == 10) {
                levelOver = true;
                levelStarted = false;
            }
        }

    }
}
