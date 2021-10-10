import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

public abstract class Pipe {
    protected final double PIPE_GAP;
    protected final double HIGH_GAP;
    protected final double LOW_GAP;
    protected final double INITIAL_X;
    protected double y;
    protected double x;
    protected static double stepSize = 3;
    protected static final double MAX_STEP = 5;
    protected static final double MIN_STEP = 1;
    private final double PIPE_WIDTH;
    private final double PIPE_HEIGHT;
    private final double FLAME_HEIGHT;

    protected boolean flameOn;


    public Pipe() {
        HIGH_GAP = 100;
        LOW_GAP = 500;
        PIPE_GAP = 168;
        this.INITIAL_X = Window.getWidth();
        x = INITIAL_X;
        PIPE_WIDTH = 65;
        PIPE_HEIGHT = 768;

        this.FLAME_HEIGHT = 39;
        flameOn = false;

    }
    public abstract boolean isFlameOn();

    protected double getPipeY() {
        return (new Random().nextDouble() * (LOW_GAP - HIGH_GAP)) + HIGH_GAP;
    }


    public void update(int frame) {
    }

    public static void increaseStepSize() {
       double newSize = stepSize + (stepSize*0.5);
       if (newSize < MAX_STEP) {
           stepSize = newSize;
       }
    }

    public static void decreaseStepSize() {
        double newSize = stepSize - (stepSize*0.5);
        if (newSize > MIN_STEP) {
            stepSize = newSize;
        }
    }

    public void drawPipes(Image image) {
        image.drawFromTopLeft(x, y - PIPE_HEIGHT);
        image.drawFromTopLeft(x, y + PIPE_GAP, new DrawOptions().setRotation(Math.PI));
    }

    /*
  Returns a bounding box for the upper pipe.
   */
    public Rectangle getUpperRectangle() {
        return new Rectangle(x, y - PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT);
    }

    /*
    Returns a bounding box for the lower pipe.
     */
    public Rectangle getLowerRectangle() {
        return new Rectangle(x, y + PIPE_GAP, PIPE_WIDTH, PIPE_HEIGHT);
    }

    public Rectangle getFlameUpperRectangle() {
        return new Rectangle(x, y - PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT + FLAME_HEIGHT);
    }

    public Rectangle getFlameLowerRectangle() {
        return new Rectangle(x, y + PIPE_GAP - FLAME_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT + FLAME_HEIGHT);
    }


}
