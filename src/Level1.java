import bagel.Image;

public class Level1 extends Level{
    private final Image BACKGROUND_IMAGE;
    private int MAX_SCORE; // Hold the winning score for the level
    private int numHearts; // The number of lives for the level
    private boolean levelWon; // Indicates if the level is won

    public Level1() {
        BACKGROUND_IMAGE = new Image("res/level-1/background.png");
        MAX_SCORE = 30;
        numHearts = 6; // Initializes level
        levelWon = false;
    }
}
