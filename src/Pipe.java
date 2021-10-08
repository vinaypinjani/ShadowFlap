import bagel.Window;
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


    public Pipe() {
        HIGH_GAP = 100;
        LOW_GAP = 500;
        PIPE_GAP = 168;
        this.INITIAL_X = Window.getWidth();
        x = INITIAL_X;
        PIPE_WIDTH = 65;
        PIPE_HEIGHT = 768;

    }

    protected double getPipeY() {
        return (new Random().nextDouble() * (LOW_GAP - HIGH_GAP)) + HIGH_GAP;
    }


    public void update() {
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
}
