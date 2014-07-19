package itc313_ass1;

/*
 * This class represents a single Point of Interest to be displayed on a map.
 */
public class PointOfInterest {
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
}
