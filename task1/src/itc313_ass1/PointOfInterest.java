package itc313_ass1;

import java.awt.Point;
import java.util.Comparator;

/*
 * This class represents a single Point of Interest to be displayed on a map.
 */
public class PointOfInterest extends Point {
  String name;
  String type;
  Point point;

  /**
   * Constructs a new PointOfInterest.
   *
   * @param name name of the PointOfInterest.
   * @param type category of this PointOfInterest. Used for grouping similar points.
   * @param point co-ordinates of this point, in arcseconds north and east of the Eqator and Prime Meridian.
   */
  public PointOfInterest(String name, String type, int x, int y) {
    this.name = name;
    this.type = type;
    this.x = x;
    this.y = y;
  }

  public String toString() {
    return String.format("%s, %s, %d, %d", name, type, x, y);
  }

  public static Comparator<Point> byX = new Comparator<Point>() {
    @Override
    public int compare(Point a, Point b) {
      if(a.x < b.x) {
        return -1;
      } else if(a.x > b.x) {
        return 1;
      } else {
        if(a.y < b.y) {
          return -1;
        } else if(a.y > b.y) {
          return 1;
        } else {
          return 0;
        }
      }
    }
  };

  public static Comparator<Point> byY = new Comparator<Point>() {
    @Override
    public int compare(Point a, Point b) {
      if(a.y < b.y) {
        return -1;
      } else if(a.y > b.y) {
        return 1;
      } else {
        if(a.x < b.x) {
          return -1;
        } else if(a.x > b.x) {
          return 1;
        } else {
          return 0;
        }
      }
    }
  };

}
