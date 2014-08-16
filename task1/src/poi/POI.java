package poi;

import java.awt.geom.Point2D;
import java.util.Comparator;

@SuppressWarnings("serial")
public class POI extends Point2D.Float {
  /*
   * Sort a collection of Point2D by X, then by Y
   */
  public static Comparator<Point2D> byX = new Comparator<Point2D>() {
    @Override
    public int compare(Point2D a, Point2D b) {
      if (a.getX() < b.getX()) {
        return -1;
      } else if (a.getX() > b.getX()) {
        return 1;
      } else {
        if (a.getY() < b.getY()) {
          return -1;
        } else if (a.getY() > b.getY()) {
          return 1;
        } else {
          return 0;
        }
      }
    }
  };

  /*
   * Sort a collection of Point2D by Y, then by X
   */
  public static Comparator<Point2D> byY = new Comparator<Point2D>() {
    @Override
    public int compare(Point2D a, Point2D b) {
      if (a.getY() < b.getY()) {
        return -1;
      } else if (a.getY() > b.getY()) {
        return 1;
      } else {
        if (a.getX() < b.getX()) {
          return -1;
        } else if (a.getX() > b.getX()) {
          return 1;
        } else {
          return 0;
        }
      }
    }
  };

  private String type;

  public POI(String type, float x, float y) {
    this.type = type;
    this.x = x;
    this.y = y;
  }

  public String getType() {
    return this.type;
  }

  @Override
  public String toString() {
    return String.format("%s, %f, %f", type, x, y);
  }

}
