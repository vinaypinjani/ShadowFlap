import bagel.DrawOptions;
import bagel.Image;

public class SteelPipe extends Pipe{
    private final Image STEEL_PIPE_IMAGE; // Holds the Image for the pipe



    public SteelPipe() {
        STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
        y = getPipeY();
    }

    @Override
    public void update() {

        STEEL_PIPE_IMAGE.drawFromTopLeft(x, y - STEEL_PIPE_IMAGE.getHeight());
        STEEL_PIPE_IMAGE.drawFromTopLeft(x, y + PIPE_GAP, new DrawOptions().setRotation(Math.PI));

        x -= stepSize;
    }
}
