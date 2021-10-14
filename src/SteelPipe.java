import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;

/**
 * Class contains methods and attributes for steel pipes.
 */
public class SteelPipe extends Pipe{
    private final Image STEEL_PIPE_IMAGE; // Holds the Image for the pipe
    private final Image FLAME_IMAGE; //   Holds the image for the flame
    private final int MAX_FLAME_FRAMES; // Max number of frames flame can be active for.
    private int flameFrames; // counts the frames that the flame is active for.

    /**
     * Constructor initializes values for the steel pipe object.
     */
    public SteelPipe() {
        this.STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
        this.FLAME_IMAGE = new Image("res/level-1/flame.png");
        this.MAX_FLAME_FRAMES = 3;
        y = getPipeY(); //  assigns a random y value for the pipe opening.
        this.flameFrames = 0;
        isSteel = true;
    }

    /**
     * @return flameOn that indicates whether the flame is on when classed.
     */
    public boolean isFlameOn() {
        return flameOn;
    }


    /**
     * Method draws and updates the position of the steel pipe and the flame.
     * @param frame current frame of the game.
     */
    @Override
    public void update(int frame) {
        // Checks if the pipe is in the window
        if (x > 0 || x < Window.getWidth()) {
            // lights flame every 20th frame.
            if (frame % 20 == 0) {
                flameOn = true;
            }
            if (flameFrames <= MAX_FLAME_FRAMES) {
                flameFrames++; // Increments number of frames the flame is on for
            } else {
                flameOn = false; // switches flame off
                flameFrames = 0; // resets flame frames
            }
            // Draws flame on steel pipes if flame is on
            if (flameOn) {
                FLAME_IMAGE.drawFromTopLeft(x, y);
                FLAME_IMAGE.drawFromTopLeft(x, y + PIPE_GAP - FLAME_IMAGE.getHeight(),
                        new DrawOptions().setRotation(Math.PI));
            }
        }
        // Draws steel pipes
        super.drawPipes(STEEL_PIPE_IMAGE);
        x -= stepSize; // updates x position
    }
}
