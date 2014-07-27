package itc313_ass1;

import java.util.Comparator;

/*
 * This class represents a single Point of Interest to be displayed on a map.
 */
public class PointOfInterest implements Comparable<PointOfInterest> {
  String name;
  String type;
  int x;
  int y;

  /**
   * Constructs a new PointOfInterest.
   *
   * @param name name of the PointOfInterest.
   * @param type category of this PointOfInterest. Used for grouping similar points.
   * @param x the x co-ordinate of this point, in arcseconds East of the Prime Meridian.
   * @param y the y co-ordinate of this point, in arcseconds North of the Equator.
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

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  /*
   * Points will be ordered by Northness, then by Westness
   */
  public int compareTo(PointOfInterest point) {
    int Y = this.y - point.y;
    // if this is below or at the same y as point
    if(Y <= 0) {
      return this.x - point.x;
    } else {
      return Y;
    }
  }

  /*
   * Points will be ordered by Westness, then by Northness
   */
  public int westCompareTo(PointOfInterest point) {
    int X = this.x - point.x;
    // if this is below or at the same y as point
    if(X <= 0) {
      return this.y - point.y;
    } else {
      return X;
    }
  }

  public static Comparator<PointOfInterest> WestComparator
    = new Comparator<PointOfInterest>() {

      public int compare(PointOfInterest p1, PointOfInterest p2) {

        return p1.westCompareTo(p2);
      }
    };
}
