package itc313_ass1;

import java.awt.Point;
import java.util.Comparator;

public class byX implements Comparator<Point> {
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
}
