import bagel.Image;

public class Level0 extends Level {
    private final Image BACKGROUND_IMAGE;
    private final String INSTRUCTION_MESSAGE; // String with instruction message.
    private final int MAX_SCORE; // Hold the winning score for the level
    private final int MAX_LIVES;


    public Level0() {
        this.BACKGROUND_IMAGE = new Image("res/level-0/background.png");
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
        MAX_SCORE = 10;
        this.MAX_LIVES = 3;
        currLives = MAX_LIVES; // Initializes level
    }

    public int getMaxScore() {
        return MAX_SCORE;
    }

    public boolean getPipesInitiated() {
        return pipesInitiated;
    }

    public void update(int frame) {
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0);

        if (levelStarted && !levelOver) {

            if (frame % 100 == 0) {
                pipes.add(new PlasticPipe(false));
                pipesInitiated = true;
                numPipes++;
            }
            for (int i = 0; i < numPipes; i++) {
                this.pipes.get(i).update(frame);
            }
            drawHearts(MAX_LIVES, currLives);
            if (levelWon) {
                levelStarted = false;
                levelOver = true;
            }
        }
    }
}
