import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.Window;
import bagel.util.Rectangle;

import java.util.ArrayList;

public abstract class Level {
    private final Image EMPTY_HEART;
    private final Image FULL_HEART;
    private final Point WINDOW_CENTRE; // Point for centre of the window
    private final int fontSize; // Font size used for the game.
    protected final Font font; // Holds the font used for the game.
    protected final String INSTRUCTION_MESSAGE; // String with instruction message.
    protected final String COLLISION_MESSAGE; // String with collision message.
    protected final String SCORE_MESSAGE; // String with score message.
    protected final String LEVEL_UP_MESSAGE;
    protected final String SCORE_COUNTER;
    protected final String SHOOT_MESSAGE;
    protected final String WIN_MESSAGE;
    protected ArrayList<Pipe> pipes;

    protected int currLives;
    protected boolean pipesInitiated;
    protected int currentPipe;
    protected int numPipes;
    protected boolean levelStarted;
    protected boolean levelOver;
    protected boolean levelWon; // Indicates if the level is won


    public Level() {
        this.EMPTY_HEART = new Image("res/level/noLife.png");
        this.FULL_HEART = new Image("res/level/fullLife.png");
        this.WINDOW_CENTRE  = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        this.fontSize = 48;
        this.font = new Font("res/font/slkscr.ttf", fontSize);
        this.INSTRUCTION_MESSAGE = "PRESS SPACE TO START";
        this.COLLISION_MESSAGE = "GAME OVER!";
        this.SCORE_MESSAGE = "FINAL SCORE ";
        this.LEVEL_UP_MESSAGE = "LEVEL-UP!";
        this.SCORE_COUNTER = "SCORE: ";
        this.SHOOT_MESSAGE = "PRESS 'S' TO SHOOT";
        this.WIN_MESSAGE =  "CONGRATULATIONS!";
        this.pipes = new ArrayList<Pipe>();
        this.pipesInitiated = false;
        this.currentPipe = 0;
        this.numPipes = 0;
        this.levelWon = false;
        this.levelStarted = false;
        this.levelOver = false;

    }

    public void collide() {
        if (currLives > 1) {
            currLives--;
            pipes.remove(currentPipe);
            numPipes--;
        } else {
            levelOver = true;
        }
    }

    public void loseLife() {
        if (currLives > 1) {
            currLives--;
        } else {
            levelOver = true;
        }
    }
    public boolean getPipesInitiated() {
        return pipesInitiated;
    }

    public boolean getLevelStarted() {
        return levelStarted;
    }

    public void setLevelStarted() {
        levelStarted = true;
    }

    public boolean getLevelOver() {
        return levelOver;
    }

    public void updateCurrentPipe() {
        currentPipe++;
    }

    public void setLevelWon() {
        levelWon = true;
    }

    public boolean getLevelWon() {
        return levelWon;
    }

    public void setLevelOver() {
        levelOver = true;
    }

    public boolean isLevelOver() {
        return levelOver;
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

    public void printShootMessage() {
        font.drawString(SHOOT_MESSAGE, getStringCentre(SHOOT_MESSAGE).x, getStringCentre(SHOOT_MESSAGE).y + 68);
    }

    public void printScoreCounter(int score) {
        font.drawString(SCORE_COUNTER + score, 100, 100);

    }
    public void printWinMessage() {
        font.drawString(WIN_MESSAGE, getStringCentre(WIN_MESSAGE).x, getStringCentre(WIN_MESSAGE).y);
    }


    public void drawHearts(int maxLives, int remainingLives) {
        final double SPACE = 50;
        final double HEART_Y = 15;
        double x = 100;
        for (int i =0; i < remainingLives; i++) {

            FULL_HEART.drawFromTopLeft(x, HEART_Y);
            x = x + SPACE;
        }
        for (int i = 0; i < maxLives - remainingLives; i++) {
            EMPTY_HEART.drawFromTopLeft(x, HEART_Y);
            x = x + SPACE;
        }
    }

    public Rectangle returnUpperRectangle() {
        if (!pipes.get(currentPipe).isFlameOn()) {
            return pipes.get(currentPipe).getUpperRectangle();
        } else {
            return pipes.get(currentPipe).getFlameUpperRectangle();
        }
    }

    public Rectangle returnLowerRectangle() {
        if (!pipes.get(currentPipe).isFlameOn()) {
            return pipes.get(currentPipe).getLowerRectangle();
        } else {
            return pipes.get(currentPipe).getFlameLowerRectangle();
        }
    }
}
