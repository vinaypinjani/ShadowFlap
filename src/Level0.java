import bagel.Image;

import java.util.Random;

public class Level0 extends Level {
    private final Image BACKGROUND_IMAGE;
    private int MAX_SCORE; // Hold the winning score for the level
    private int numHearts; // The number of lives for the level
    private boolean levelWon; // Indicates if the level is won
    private boolean levelStarted;

    private PlasticPipe[] plasticPipes;
    private int numPipes;

    public Level0() {
        BACKGROUND_IMAGE = new Image("res/level-0/background.png");
        MAX_SCORE = 10;
        numHearts = 3; // Initializes level
        levelWon = false;
        levelStarted = false;

        numPipes = 0;
        plasticPipes = new PlasticPipe[MAX_SCORE];
    }

    public void setLevelStarted(boolean levelStarted) {
        this.levelStarted = levelStarted;
    }

    public boolean getLevelStarted() {
        return this.levelStarted;
    }

    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0);
        if (levelStarted && !levelWon) {
            if (frame % 100 == 0) {
                plasticPipes[numPipes] = new PlasticPipe();
                plasticPipes[numPipes].update();
                numPipes++;
            }

            for (int i = 0; i < numPipes; i++) {
                this.plasticPipes[i].update();
            }
        } else {
            // press space to start
        }

        if (numPipes == 10) {
            levelWon = true;
        }
    }
}
