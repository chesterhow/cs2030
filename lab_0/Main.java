import java.util.Scanner;

public class Main {
  public static Circle createCircle(Point p, Point q, double radius) {
    // Points are the same
    if (p.equals(q)) {
      return null;
    }

    Point m = p.midPoint(q);
    double pmDist = p.distance(m);

    // Hypotenuse shorter than adjacent. Not possible.
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
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    Point p = new Point(sc.nextDouble(), sc.nextDouble());
    Point q = new Point(sc.nextDouble(), sc.nextDouble());
    double radius = sc.nextDouble();

    Circle c = createCircle(p, q, radius);

    if (c == null) {
      System.out.println("No valid circle can be created");
    } else {
      System.out.println("Created: " + c);
    }

    sc.close();
  }
}