import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Rectangle;


import java.util.Random;

public class PlasticPipe extends Pipe{
    private final Image PLASTIC_PIPE_IMAGE; // Holds the image for the Pipe

    private final double MID_GAP;
     // Initial x coordinate for the pipe.
    private final double[] PIPE_HEIGHTS_L0;
    private final double PLASTIC_Y;



    public PlasticPipe(boolean isLevel1) {
        this.PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
        MID_GAP = 300;

        PIPE_HEIGHTS_L0 = new double[]{HIGH_GAP, MID_GAP, LOW_GAP};
        if (!isLevel1) {
            PLASTIC_Y = PIPE_HEIGHTS_L0[new Random().nextInt(PIPE_HEIGHTS_L0.length)];
        } else {
            PLASTIC_Y = getPipeY();
        }

    }



    public void update() {

        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, PLASTIC_Y - PLASTIC_PIPE_IMAGE.getHeight());
        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, PLASTIC_Y + PIPE_GAP, new DrawOptions().setRotation(Math.PI));

        x -= STEP_SIZE;
    }

    /*
   Returns a bounding box for the upper pipe.
    */
    public Rectangle getUpperRectangle() {
        return new Rectangle(x, PLASTIC_Y - PLASTIC_PIPE_IMAGE.getHeight(), PLASTIC_PIPE_IMAGE.getWidth(), PLASTIC_PIPE_IMAGE.getHeight());
    }

    /*
    Returns a bounding box for the lower pipe.
     */
    public Rectangle getLowerRectangle() {
        return new Rectangle(x, PLASTIC_Y + PIPE_GAP, PLASTIC_PIPE_IMAGE.getWidth(), PLASTIC_PIPE_IMAGE.getHeight());
    }


}
