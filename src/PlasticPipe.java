import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;

import java.util.Random;

public class PlasticPipe extends Pipe{
    private final Image PLASTIC_PIPE_IMAGE; // Holds the image for the Pipe
    private final double INITIAL_X; // Initial x coordinate for the pipe.
    private final double STEP_SIZE;
    private double x;
    private final double[] PIPE_HEIGHTS;
    private double y;

    public PlasticPipe() {
        this.PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
        this.INITIAL_X = Window.getWidth() - PLASTIC_PIPE_IMAGE.getWidth();
        this.STEP_SIZE = 3;
        this.x = INITIAL_X;
        PIPE_HEIGHTS = new double[]{100, 300, 500};
        y = PIPE_HEIGHTS[new Random().nextInt(PIPE_HEIGHTS.length)];
    }

    public void update() {

        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, y - PLASTIC_PIPE_IMAGE.getHeight());
        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, y + 168, new DrawOptions().setRotation(Math.PI));

        x -= STEP_SIZE;


    }
}
