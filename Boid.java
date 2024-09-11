import java.util.ArrayList;

public class Boid {
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


    private String name;
    private double x;
    private double y;
    private double vx;
    private double vy;

    public Boid(double x, double y, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
        boidList.add(this);
    }

    public Boid(double x, double y) {
        this.name = "unnamed";
        this.x = x;
        this.y = y;
        boidList.add(this);
    }


    public double distanceTo(Boid o) {
        return Math.sqrt((this.x - o.x) * (this.x - o.x) + (this.y - o.y) * (this.y - o.y));
    }

    public double distanceTo(int x, int y) {
        return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }


    public static ArrayList<Boid> getBoids(){
        return boidList;
    }

    public double getX() {
        return this.x; 
    }

    public double getY() {
        return this.y; 
    }


    public static void updatePosition(){
        double[] avoid = new double[boidList.size() * 2];
        double[] align = new double[boidList.size() * 2];
        double[] center = new double[boidList.size() * 2];

        // Update loop for each boid
        for (int i = 0; i < boidList.size(); i++) {
            Boid boid = boidList.get(i);
            int numVisible = 0;

            for(Boid other : boidList){
                // Check if other boids are too close/far here
                if(!boid.equals(other)){

                    // Check if this boid is too close to any other boid
                    if(boid.distanceTo(other) <= INNER_RANGE){
                        avoid[i] += boid.x - other.x;
                        avoid[i+1] += boid.y - other.y;
                    }


                    // Check other visible boids
                    if(boid.distanceTo(other) <= OUTER_RANGE){
                        numVisible += 1;

                        // Find average direction vector
                        align[i] += other.vx;
                        align[i+1] += other.vy;

                        // Find average position vector
                        center[i] += other.x;
                        center[i+1] += other.y;
                    }
                }
            }


            // Average the direction vector
            if(numVisible != 0) {
                align[i] /= numVisible;
                align[i+1] /= numVisible;

                center[i] /= numVisible;
                center[i+1] /= numVisible;
            }

            // System.out.println(boid.name + ": " +boid.x + ", " + boid.y+ "[" + boid.vx + " " + boid.vy + "]");
        }

        // Update velocity according to the checks
        for(int i = 0; i < boidList.size(); i ++) {
            Boid boid = boidList.get(i);

            // Update position according to others
            boid.vx += avoid[i] * AVOIDING;
            boid.vy += avoid[i + 1] * AVOIDING;

            boid.vx += (align[i] - boid.vx)* ALIGNING;
            boid.vy += (align[i + 1] - boid.vy)* ALIGNING;

            boid.vx += (center[i] - boid.x)* CENTERING;
            boid.vy += (center[i + 1] - boid.y)* CENTERING;


            // Keep boids on the screen
            if (boid.y < screenBounds[0]){
                boid.vy = boid.vy + TURN;
            } else if(boid.x > screenBounds[1]){
                boid.vx = boid.vx - TURN;
            } else if(boid.y > screenBounds[2]) {
                boid.vy = boid.vy - TURN;
            } else if(boid.x < screenBounds[3]){
                boid.vx = boid.vx + TURN;
            }

            // double speed = Math.max(Math.min(Math.sqrt(boid.vx * boid.vx + boid.vy * boid.vy), MAX_SPEED), MIN_SPEED);
            double speed = Math.sqrt(boid.vx * boid.vx + boid.vy * boid.vy);

            if(speed > MAX_SPEED) {
                boid.vx = (boid.vx/speed)*MAX_SPEED;
                boid.vy = (boid.vy/speed)*MAX_SPEED;
            } else if(speed < MIN_SPEED) {
                boid.vx = (boid.vx/speed)*MIN_SPEED;
                boid.vy = (boid.vy/speed)*MIN_SPEED;
            }

            boid.x += boid.vx;
            boid.y += boid.vy;


            // System.out.println(boid.name + ": " +boid.x + ", " + boid.y+ "[" + boid.vx + " " + boid.vy + "]");
        }
    }



    public static void updatePosition(int mx, int my) {
        double[] avoid = new double[boidList.size() * 2];
        double[] align = new double[boidList.size() * 2];
        double[] center = new double[boidList.size() * 2];

        // Update loop for each boid
        for (int i = 0; i < boidList.size(); i++) {
            Boid boid = boidList.get(i);
            int numVisible = 0;

            for(Boid other : boidList){
                // Check if other boids are too close/far here
                if(!boid.equals(other)){

                    // Check if this boid is too close to any other boid
                    if(boid.distanceTo(other) <= INNER_RANGE){
                        avoid[i] += boid.x - other.x;
                        avoid[i+1] += boid.y - other.y;
                    }


                    // Check other visible boids
                    if(boid.distanceTo(other) <= OUTER_RANGE){
                        numVisible += 1;

                        // Find average direction vector
                        align[i] += other.vx;
                        align[i+1] += other.vy;

                        // Find average position vector
                        center[i] += other.x;
                        center[i+1] += other.y;
                    }
                }
            }

            

            // Average the direction vector
            if(numVisible != 0) {
                align[i] /= numVisible;
                align[i+1] /= numVisible;

                center[i] /= numVisible;
                center[i+1] /= numVisible;
            }

            // System.out.println(boid.name + ": " +boid.x + ", " + boid.y+ "[" + boid.vx + " " + boid.vy + "]");
        }

        // Update velocity according to the checks
        for(int i = 0; i < boidList.size(); i ++) {
            Boid boid = boidList.get(i);

            // Add logic here for mouse steering
            if(boid.distanceTo(mx, my) <= MOUSE_RANGE){
                boid.vx += (mx - boid.x) * MOUSE_ATTENTION * (MOUSE_RANGE / boid.distanceTo(mx, my));
                boid.vy += (my - boid.y) * MOUSE_ATTENTION * (MOUSE_RANGE / boid.distanceTo(mx, my));
            }

            if(boid.distanceTo(mx, my) <= 80){
                boid.vx += (boid.x - mx) * MOUSE_ATTENTION;
                boid.vy += (boid.y - mx) * MOUSE_ATTENTION;
            }


            // Update velocity according to others
            boid.vx += avoid[i] * AVOIDING;
            boid.vy += avoid[i + 1] * AVOIDING;

            boid.vx += (align[i] - boid.vx)* ALIGNING;
            boid.vy += (align[i + 1] - boid.vy)* ALIGNING;

            boid.vx += (center[i] - boid.x)* CENTERING;
            boid.vy += (center[i + 1] - boid.y)* CENTERING;


            // Keep boids on the screen
            if (boid.y < screenBounds[0]){
                boid.vy = boid.vy + TURN;
            } else if(boid.x > screenBounds[1]){
                boid.vx = boid.vx - TURN;
            } else if(boid.y > screenBounds[2]) {
                boid.vy = boid.vy - TURN;
            } else if(boid.x < screenBounds[3]){
                boid.vx = boid.vx + TURN;
            }

            // double speed = Math.max(Math.min(Math.sqrt(boid.vx * boid.vx + boid.vy * boid.vy), MAX_SPEED), MIN_SPEED);
            double speed = Math.sqrt(boid.vx * boid.vx + boid.vy * boid.vy);

            if(speed > MAX_SPEED) {
                boid.vx = (boid.vx/speed)*MAX_SPEED;
                boid.vy = (boid.vy/speed)*MAX_SPEED;
            } else if(speed < MIN_SPEED) {
                boid.vx = (boid.vx/speed)*MIN_SPEED;
                boid.vy = (boid.vy/speed)*MIN_SPEED;
            }

            boid.x += boid.vx;
            boid.y += boid.vy;


            // System.out.pr
        }
    }
    
}
