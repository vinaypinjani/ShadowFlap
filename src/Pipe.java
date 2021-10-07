import bagel.Window;

import java.util.Random;

public abstract class Pipe {
    protected final double PIPE_GAP;
    protected final double HIGH_GAP;
    protected final double LOW_GAP;
    protected final double INITIAL_X;
    protected double y;
    protected double x;
    protected final double STEP_SIZE;

    public Pipe() {
        HIGH_GAP = 100;
        LOW_GAP = 500;
        PIPE_GAP = 168;
        this.INITIAL_X = Window.getWidth();

        this.STEP_SIZE = 3;
        x = INITIAL_X;
    }

    protected double getPipeY() {
        return (new Random().nextDouble() * (LOW_GAP - HIGH_GAP)) + HIGH_GAP;
    }


    public void update() {

    }
}
