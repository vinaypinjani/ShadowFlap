import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;


public class Bird {
    /*
   Final Variables for the Bird Class.
   */
    private final Image BIRD_DOWN_L0; // Loads bird wing down image.
    private final Image BIRD_UP_L0; // Loads bird wing up image.
    private final double X; // The X value for constant throughout the game.
    private final double INITIAL_Y; // The Y value for the bird at spawn.
    private final double ACCELERATION; // The downward acceleration for the bird.
    private final double MAX_VELOCITY; // The maximum velocity at which the bird can fall.
    private final double SPEED; // The speed per frame for the bird flying.

    private double y; // Holds the current y position for the bird.
    private double velocity; // holds the current velocity of the bird.

    /*
    Constructor initializes all Final values for bird with given values.
    */
    public Bird() {
        this.BIRD_DOWN_L0 = new Image("res/level-0/birdWingDown.png");
        this.BIRD_UP_L0 = new Image("res/level-0/birdWingUp.png");
        this.X = 200;
        this.INITIAL_Y = 350;
        this.ACCELERATION = 0.4;
        this.MAX_VELOCITY = 10;
        this.SPEED = 6.0;

        this.y = INITIAL_Y; // Current y position is set as the initial y position.
    }

    /*
    Setter sets the velocity for the bird.
    */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /*
  Fly method implements the flying characteristics for the bird
  */
    public void fly() {
        velocity = -SPEED; // Velocity is set as the given speed but in the opposite direction as the bird is moving up.
        y += velocity; // y value incremented by the velocity per frame.

    }

    /*
   Fall method implements the falling characteristics for the bird.
   */
    public void fall() {
        // Checks if the current velocity is under the limit.
        if (velocity < MAX_VELOCITY) {
            velocity += ACCELERATION; // Increases velocity by the given acceleration each frame
        } else
            velocity = MAX_VELOCITY; // Make velocity constant if at the limit.
        y += velocity; // y value incremented by the current velocity.

    }

    /* Rectangle method returns the current bounding box for the bird.*/
    public Rectangle getRectangle() {
        return BIRD_UP_L0.getBoundingBoxAt(new Point(X, y)); // Takes in the current coordinates for the bird.
    }

    /*
    Flap method takes a boolean flag as input and draws the bird at the current coordinates.
     */
    public void flap(boolean wingDown) {
        if (wingDown) {
            BIRD_DOWN_L0.draw(X, y); // Draws bird with wings down.
        } else {
            BIRD_UP_L0.draw(X, y); // Draws bird with wings up.
        }
    }

    public double getX() {
        return X;
    }







}
