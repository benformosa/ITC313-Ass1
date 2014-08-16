package poi;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class POITester {
  public static void main(String args[]) {
    try {
      List<POI> poi = POIFactory.loadPOI("poi.csv", "types.csv");

      Collections.sort(poi, POI.byX);
      float minX = poi.get(0).x;
      float maxX = poi.get(poi.size() - 1).x;
      float rangeX = maxX - minX;
      System.out.println("minX: " + minX);
      System.out.println("maxX: " + maxX);
      System.out.println("rangeX: " + rangeX);

      Collections.sort(poi, POI.byY);
      float minY = poi.get(0).y;
      float maxY = poi.get(poi.size() - 1).y;
      float rangeY = maxY - minY;
      System.out.println("minY: " + minY);
      System.out.println("maxY: " + maxY);
      System.out.println("rangeY: " + rangeY);

      System.out.println(400/Math.max(rangeX, rangeY));

    } catch(IOException ex) {
    }
  }
}
