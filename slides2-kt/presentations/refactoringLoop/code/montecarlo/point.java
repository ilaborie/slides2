public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) { this.x = x; this.y = y; }

    public boolean inCircle() {
        return (x * x) + (y * y) <= 1;
    }

    private static final Random rnd = new Random();
    public static Point newPoint() {
        return new Point(rnd.nextDouble(), rnd.nextDouble());
    }

    public static double compute(int count, int inCircle) {
        return ((double) inCircle / count) * 4;
    }
}