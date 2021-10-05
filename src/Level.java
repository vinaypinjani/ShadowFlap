import bagel.Font;
import bagel.util.Point;
import bagel.Window;

public abstract class Level {
    private final Point WINDOW_CENTRE; // Point for centre of the window
    private final int fontSize; // Font size used for the game.
    protected final Font font; // Holds the font used for the game.
    private final String INSTRUCTION_MESSAGE; // String with instruction message.
    private final String COLLISION_MESSAGE; // String with collision message.
    private final String SCORE_MESSAGE; // String with score message.
    private final String LEVEL_UP_MESSAGE;


    public Level() {
        this.WINDOW_CENTRE  = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        this.fontSize = 48;
        this.font = new Font("res/font/slkscr.ttf", fontSize);
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
        this.COLLISION_MESSAGE = "GAME OVER!";
        this.SCORE_MESSAGE = "FINAL SCORE ";
        this.LEVEL_UP_MESSAGE = "LEVEL-UP!";


    }


    public Point getStringCentre(String string) {
        double x = WINDOW_CENTRE.x - (font.getWidth(string) / 2); // Gets the x coordinate for message.
        double y = WINDOW_CENTRE.y - (fontSize / 2.0); // Gets the y coordinate for the message.
        return new Point(x, y);
    }

    public void printCollisionMessage() {
        font.drawString(COLLISION_MESSAGE,
                getStringCentre(COLLISION_MESSAGE).x, getStringCentre(COLLISION_MESSAGE).y);
    }
    public void printScore(int score) {
        font.drawString(SCORE_MESSAGE + score,
                getStringCentre(SCORE_MESSAGE + score).x, getStringCentre(SCORE_MESSAGE + score).y + 75);
    }
    public void printLevelUpMessage() {
        font.drawString(LEVEL_UP_MESSAGE,
                getStringCentre(LEVEL_UP_MESSAGE).x, getStringCentre(LEVEL_UP_MESSAGE).y);
    }

    public void printInstructionMessage() {
        font.drawString(INSTRUCTION_MESSAGE, getStringCentre(INSTRUCTION_MESSAGE).x, getStringCentre(INSTRUCTION_MESSAGE).y);
    }

}
