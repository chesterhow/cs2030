public class Circle {
  private final Point centre;
  private final double radius;

  private Circle(Point centre, double radius) {
    this.centre = centre;
    this.radius = radius;
  }

  public static Circle getCircle(Point centre, double radius) {
    if (radius > 0) {
      return new Circle(centre, radius);
    }

    return null;
  }

  @Override
  public String toString() {
    return "circle of radius " + String.format("%.1f", this.radius) + " centered at " + this.centre;
  }
}