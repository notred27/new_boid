import java.util.ArrayList;

public class BoidEnvironment {
     private static final double OUTER_RANGE = 20;
    private static final double INNER_RANGE = 2;

    private static final double MIN_SPEED = 2;
    private static final double MAX_SPEED = 3;

    private static final double ALIGNING = 0.05;
    private static final double AVOIDING = 0.05;
    private static final double CENTERING = 0.0005;
    private static final double TURN = 0.6;

    public static double MOUSE_ATTENTION = 0.005;
    public static double MOUSE_RANGE = 100;




    private static ArrayList<Boid> boidList = new ArrayList<Boid>();
    public static int[] screenBounds = {100, 400, 400, 100}; //Top, right, bottom, left


    public BoidEnvironment(){

    }


    public void addBoid(double x, double y){
        boidList.add(new Boid(x, y));
    }


}
