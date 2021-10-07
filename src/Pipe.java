import bagel.Window;

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

    public Pipe() {
        HIGH_GAP = 100;
        LOW_GAP = 500;
        PIPE_GAP = 168;
        this.INITIAL_X = Window.getWidth();
        x = INITIAL_X;

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
}
