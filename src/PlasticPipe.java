import bagel.Image;
import java.util.Random;

/**
 * Class contains methods and attributes for plastic pipes.
 */
public class PlasticPipe extends Pipe{
    private final Image PLASTIC_PIPE_IMAGE; // Holds the image for the Pipe
    private final double MID_GAP; // Y value for the mid level pipe opening.
    private final double[] PIPE_HEIGHTS_L0; // List for the three height options.

    /**
     * Constructor initializes values for the plastic pipe object.
     * @param isLevel1 indicates if player is playing level1.
     */
    public PlasticPipe(boolean isLevel1) {
        this.PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");
        this.MID_GAP = 300;
        this.PIPE_HEIGHTS_L0 = new double[]{HIGH_GAP, MID_GAP, LOW_GAP};
        // Checks if it is level 1
        if (!isLevel1) {
            // Randomly picks height from 3 options if level 0.
            y = PIPE_HEIGHTS_L0[new Random().nextInt(PIPE_HEIGHTS_L0.length)];
        } else {
            y = getPipeY();
        }
        isSteel = false; // false as plastic pipe
    }

    /**
     * @return false
     */
    public boolean isFlameOn() {
        return false;
    }

    /**
     * method draws the pipe in the new position each frame.
     * @param frame the current frame
     */
    public void update(int frame) {
        super.drawPipes(PLASTIC_PIPE_IMAGE);
        x -= stepSize; //  decrements x position.
    }
}
