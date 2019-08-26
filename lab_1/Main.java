import java.util.Scanner;

public class Main {
    public static Circle createCircle(Point p, Point q, double radius) {
        if (p.equals(q)) {
            return null;
        }

        Point m = p.midPoint(q);
        double pmDist = p.distance(m);

        if (radius < pmDist) {
            return null;
        }

        double pqAngle = p.angleTo(q);
        double pcAngle = Math.acos(pmDist / radius);
        double angle = pqAngle + pcAngle;
        Point centre;

        if (angle <= Math.PI) {
            centre = p.moveTo(angle, radius);
        } else {
            centre = p.moveTo(- (2 * Math.PI - angle), radius);
        }

        return Circle.getCircle(centre, radius);
    }

    public static Point[] readPoints(Scanner sc) {
        int numPoints = sc.nextInt();
        Point[] points = new Point[numPoints];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(sc.nextDouble(), sc.nextDouble());
        }

        return points;
    }

    public static boolean isInside(Circle c, Point p) {
        return c.getCentre().distance(p) <= c.getRadius();
    }

    public static int discCoverage(Point p, Point q, Point[] points) {
        int discs = 0;

        if (p.distance(q) <= 2) {
            Circle c = createCircle(p, q, 1);
            for (Point point : points) {
                if (isInside(c, point)) {
                    discs++;
                }
            }
        }

        return discs;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Point[] points = readPoints(sc);
        int max = 0;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int dc = discCoverage(points[i], points[j], points);
                if (dc > max) {
                    max = dc;
                }
            }
        }

        System.out.println("Maximum Disc Coverage: " + max);

        sc.close();
    }
}
