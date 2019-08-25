class Point {
  private double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double distance(Point q) {
    double dx = this.x - q.x;
    double dy = this.y - q.y;
	  return Math.sqrt(dx * dx + dy* dy);
	}

  public Point midPoint(Point p) {
    double midPointX = (this.x + p.getX()) / 2;
    double midPointY = (this.y + p.getY()) / 2;
    return new Point(midPointX, midPointY);
  }

  public double angleTo(Point p) {
    double xDist = p.getX() - this.x;
    double yDist = p.getY() - this.y;

    // Points are the same
    if (xDist == 0 && yDist == 0) {
      return 0;
    } else if (xDist == 0) {
      // Point is directly above
      if (p.getY() > this.y) {
        return Math.PI / 2;
      // Point is directly below
      } else {
        return - Math.PI / 2;
      }
    } else if (yDist == 0) {
      // Point is on the right
      if (p.getX() > this.x) {
        return 0;
      // Point is on the left
      } else {
        return Math.PI; 
      }
    }

    double refAngle =  Math.atan(xDist/yDist);

    // Q1
    if (p.getX() >= this.x && p.getY() >= this.y) {
      return refAngle;
    // Q2
    } else if (p.getX() < this.x && p.getY() >= this.y) {
      return Math.PI + refAngle;
    // Q3
    } else if (p.getX() < this.x && p.getY() < this.y) {
      return refAngle - Math.PI;
    // Q4
    } else {
      return refAngle;
    }
  }

  public Point moveTo(double angle, double distance) {
    if (distance == 0) {
      return this;
    }
    
    return new Point(this.x + distance * Math.cos(angle), this.y + distance * Math.sin(angle));
  }

  public String toString() {
    return "point (" + String.format("%.3f", this.x) + ", " + String.format("%.3f", this.y) + ")";
  }
}