import java.util.ArrayList;

public class BoidEnvironment {
    public double OUTER_RANGE = 20;
    private final double INNER_RANGE = 2;

    public double MIN_SPEED = 2;
    public double MAX_SPEED = 3;

    private double ALIGNING = 0.05;
    private double AVOIDING = 0.05;
    private double CENTERING = 0.0005;
    private final double TURN = 0.9;

    public double MOUSE_ATTENTION = 0.005;
    public double MOUSE_RANGE = 100;

// TODO:  Add a method for setting what happens at screen border? i.e. wrap vs turn away


    private static ArrayList<Boid> boidList = new ArrayList<Boid>();
    public int[] screenBounds = new int[4]; //Top, right, bottom, left


    public BoidEnvironment(int width, int height, int bound){
        this.screenBounds[0] = bound;
        this.screenBounds[1] = width - bound;
        this.screenBounds[2] = height - bound;
        this.screenBounds[3] = bound;
    }

    public int getNumBoids(){
        return boidList.size();
    }

    public void addBoid(double x, double y){
        boidList.add(new Boid(x, y));
    }

    public void removeBoid() {
        if(boidList.size() >= 1) {
            boidList.remove(0);
        }
    }

    public ArrayList<Boid> getBoids() {
        return boidList;
    }

    public void setAvoid(double val) {
        this.AVOIDING = val;
    }

    public void setAlign(double val) {
        this.ALIGNING = val;
    }

    public void setCenter(double val) {
        this.CENTERING = val;
    }

    public void setOuter(double val) {
        this.OUTER_RANGE = val;
    }

    public void updatePosition(){
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


        }
    }


   

    public void updatePosition(int mx, int my) {
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


        }
    }

}
