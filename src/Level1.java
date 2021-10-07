import bagel.Image;

import java.util.ArrayList;
import java.util.Random;

public class Level1 extends Level{
    private final Image BACKGROUND_IMAGE;
    private final int MAX_SCORE; // Hold the winning score for the level
    private int currLives; // The number of lives for the level
    private final int MAX_LIVES;
    private boolean levelWon; // Indicates if the level is won
    private ArrayList<Pipe> pipes;
    private int currPipe;
    private int numPipes;
    private boolean pipesInitiated;
    private boolean levelStarted;
    private boolean levelOver;


    public Level1() {
        BACKGROUND_IMAGE = new Image("res/level-1/background.png");
        MAX_SCORE = 30;
        MAX_LIVES = 6;
        currLives = 6;
        levelWon = false;
        pipes = new ArrayList<Pipe>();
        currPipe = 0;
        levelStarted = false;
        levelOver = false;
    }

    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0,0);

        if ( !levelOver) {

            if (frame % 100 == 0) {
                if (new Random().nextBoolean()) {
                    pipes.add(new SteelPipe());
                } else {
                    pipes.add(new PlasticPipe(true));
                }
                pipesInitiated = true;
                numPipes++;
            }
            for (int i = 0; i < numPipes; i++) {
                this.pipes.get(i).update();
            }
            drawHearts(MAX_LIVES, currLives);
            if (levelWon) {
                levelStarted = false;
                levelOver = true;
            }
        }
    }
}
