import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;
import java.util.Random;

/**
 * Pipe class holds the common attributes and method for the pipes. It also tracks the coordinates for each pipe object
 * and reacts to timescale changes.
 */
public abstract class Pipe {
    protected final double PIPE_GAP; // Holds the value for the gap between a pipe set
    protected final double HIGH_GAP; // Y value for high gap
    protected final double LOW_GAP; // Y value for low gap
    private final double INITIAL_X; // Initial x value for each pipe object
    private final double PIPE_WIDTH; // Width of each pipe in pixels
    private final double PIPE_HEIGHT; // Height of each pipe in pixels
    private final double FLAME_HEIGHT; // Height of the flame

    protected boolean isSteel; // Indicates if pipe is steel
    protected boolean flameOn; // Indicates if pipe has flame on

    protected double y; // current y value of pipe opening
    protected double x; // current x value of pipe


    private static final double MAX_STEP = 5; //  maximum step size
    private static final double MIN_STEP = 1; // minimum step size
    private static final double INITIAL_STEP_SIZE = 3; // step size at the start of game
    protected static double stepSize = INITIAL_STEP_SIZE; // current step size for all pipe objects


    /**
     * Constructor initiates final and variables with initial values.
     */
    public Pipe() {
        this.HIGH_GAP = 100;
        this.LOW_GAP = 500;
        this.PIPE_GAP = 168;
        this.INITIAL_X = Window.getWidth();
        this.x = INITIAL_X;
        this.PIPE_WIDTH = 65;
        this.PIPE_HEIGHT = 768;
        this.FLAME_HEIGHT = 39;
        this.flameOn = false;
    }

    /**
     * @return isSteel returns true for steel pipes.
     */
    public boolean isSteel() {
        return isSteel;
    }

    /**
     * abstract method for steel pipes indicates if the flame is on.
     */
    public abstract boolean isFlameOn();

    /**
     * @return random double for pipe opening between Max game and min gap.
     */
    protected double getPipeY() {
        return (new Random().nextDouble() * (LOW_GAP - HIGH_GAP)) + HIGH_GAP;
    }


    /**
     * Abstract method used to update state of pipes
     */
    public abstract void update(int frame);


    /**
     * Static method that increase the step size of all pipes.
     */
    public static void increaseStepSize() {
        double newSize = stepSize + (stepSize * 0.5);
        // checks if new size is in the range
        if (newSize <= MAX_STEP) {
            stepSize = newSize;
        }
    }

    /**
     * Static method that decrease the step size of all pipes.
     */
    public static void decreaseStepSize() {
        double newSize = stepSize - (stepSize * 0.5);
        // checks if new size is in range
        if (newSize >= MIN_STEP) {
            stepSize = newSize;
        }
    }


    /**
     * Methods resets the step size to initial value.
     */
    public static void resetStepSize() {
        stepSize = INITIAL_STEP_SIZE;
    }

    /**
     * Method draws a set of pipes on the screen.
     *
     * @param image contains the image of the pipe.
     */
    public void drawPipes(Image image) {
        image.drawFromTopLeft(x, y - PIPE_HEIGHT); // upper pipe
        image.drawFromTopLeft(x, y + PIPE_GAP, new DrawOptions().setRotation(Math.PI)); // lower pipe
    }

    /**
     * Returns a bounding box for the upper pipe.
     */
    public Rectangle getUpperRectangle() {
        return new Rectangle(x, y - PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT);
    }

    /**
     * Returns a bounding box for the lower pipe.
     */
    public Rectangle getLowerRectangle() {
        return new Rectangle(x, y + PIPE_GAP, PIPE_WIDTH, PIPE_HEIGHT);
    }

    /**
     * Returns a bounding box with fire for the upper pipe.
     */
    public Rectangle getFlameUpperRectangle() {
        return new Rectangle(x, y - PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT + FLAME_HEIGHT);
    }

    /**
     * Returns a bounding box with fire for the lower pipe.
     */
    public Rectangle getFlameLowerRectangle() {
        return new Rectangle(x, y + PIPE_GAP - FLAME_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT + FLAME_HEIGHT);
    }
}
