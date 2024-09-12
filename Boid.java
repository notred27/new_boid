
public class Boid {
    public double x;
    public double y;
    public double vx;
    public double vy;

    public Boid(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double distanceTo(Boid o) {
        return Math.sqrt((this.x - o.x) * (this.x - o.x) + (this.y - o.y) * (this.y - o.y));
    }

    public double distanceTo(int x, int y) {
        return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
