import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;


import java.util.Random;

public class PlasticPipe extends Pipe{
    private final Image PLASTIC_PIPE_IMAGE; // Holds the image for the Pipe
    private final double PIPE_GAP;
    private final double INITIAL_X; // Initial x coordinate for the pipe.
    private final double STEP_SIZE;
    private double x;
    private final double[] PIPE_HEIGHTS;
    private double y;

    public PlasticPipe() {
        this.PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
        this.INITIAL_X = Window.getWidth() - PLASTIC_PIPE_IMAGE.getWidth();
        PIPE_GAP = 168;
        this.STEP_SIZE = 3;
        this.x = INITIAL_X;
        PIPE_HEIGHTS = new double[]{100, 300, 500};
        y = PIPE_HEIGHTS[new Random().nextInt(PIPE_HEIGHTS.length)];
    }

    public void update() {

        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, y - PLASTIC_PIPE_IMAGE.getHeight());
        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, y + PIPE_GAP, new DrawOptions().setRotation(Math.PI));

        x -= STEP_SIZE;


    }

    /*
   Returns a bounding box for the upper pipe.
    */
    public Rectangle getUpperRectangle() {
        return new Rectangle(x, y- PLASTIC_PIPE_IMAGE.getHeight( ), PLASTIC_PIPE_IMAGE.getWidth(), PLASTIC_PIPE_IMAGE.getHeight());
    }

    /*
    Returns a bounding box for the lower pipe.
     */
    public Rectangle getLowerRectangle() {
        return new Rectangle(x, y + PIPE_GAP, PLASTIC_PIPE_IMAGE.getWidth(), PLASTIC_PIPE_IMAGE.getHeight());
    }

    public double pipeEnd() {
        return x + PLASTIC_PIPE_IMAGE.getWidth();
    }

}
