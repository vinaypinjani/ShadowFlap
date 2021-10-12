import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;

public class SteelPipe extends Pipe{
    private final Image STEEL_PIPE_IMAGE; // Holds the Image for the pipe
    private final Image FLAME_IMAGE;
    private int flameFrames;




    public SteelPipe() {
        STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
        FLAME_IMAGE = new Image("res/level-1/flame.png");
        y = getPipeY();
        flameFrames = 0;
        isSteel = true;

    }

    public boolean isFlameOn() {
        return flameOn;
    }

    @Override
    public void update(int frame) {

        if (x > 0 || x < Window.getWidth()) {
            if (frame % 20 == 0) {
                flameOn = true;
            }
            if (flameFrames < 3) {
                flameFrames++;
            } else {
                flameOn = false;
                flameFrames = 0;
            }
            if (flameOn) {
                FLAME_IMAGE.drawFromTopLeft(x, y);
                FLAME_IMAGE.drawFromTopLeft(x, y + PIPE_GAP - FLAME_IMAGE.getHeight(), new DrawOptions().setRotation(Math.PI));
            }
        }

        super.drawPipes(STEEL_PIPE_IMAGE);

        x -= stepSize;
    }
}
