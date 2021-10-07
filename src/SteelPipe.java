import bagel.DrawOptions;
import bagel.Image;

public class SteelPipe extends Pipe{
    private final Image STEEL_PIPE_IMAGE; // Holds the Image for the pipe

    private final double STEEL_Y;

    public SteelPipe() {
        STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
        STEEL_Y = getPipeY();
    }

    @Override
    public void update() {

        STEEL_PIPE_IMAGE.drawFromTopLeft(x, STEEL_Y - STEEL_PIPE_IMAGE.getHeight());
        STEEL_PIPE_IMAGE.drawFromTopLeft(x, STEEL_Y + PIPE_GAP, new DrawOptions().setRotation(Math.PI));

        x -= STEP_SIZE;
    }
}
