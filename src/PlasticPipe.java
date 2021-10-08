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




    public PlasticPipe(boolean isLevel1) {
        this.PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
        MID_GAP = 300;

        PIPE_HEIGHTS_L0 = new double[]{HIGH_GAP, MID_GAP, LOW_GAP};
        if (!isLevel1) {
            y = PIPE_HEIGHTS_L0[new Random().nextInt(PIPE_HEIGHTS_L0.length)];
        } else {
            y = getPipeY();
        }

    }



    public void update() {

        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, y - PLASTIC_PIPE_IMAGE.getHeight());
        PLASTIC_PIPE_IMAGE.drawFromTopLeft(x, y + PIPE_GAP, new DrawOptions().setRotation(Math.PI));

        x -= stepSize;
    }




}
