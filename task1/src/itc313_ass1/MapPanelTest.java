package itc313_ass1;

import itc313_ass1.MapPanel;
import java.io.*;
import java.util.*;

public class MapPanelTest {
  public static void main(String[] args) {
    /*
    String a = "0351700S";
    String b = "0351700N";
    String c = "031700W";
    String d = "0351700Q";
    System.out.println(a);
    System.out.println(MapPanel.secondsToCoords(MapPanel.coordToSeconds(a), "q"));
    //System.out.println(MapPanel.coordToSeconds(b));
    //System.out.println(MapPanel.coordToSeconds(c));
    //System.out.println(MapPanel.coordToSeconds(d));
    */

    try {
      File file = new File("1.poi");
      Set<PointOfInterest> a = MapPanel.readPOI(file);

      for(PointOfInterest p : a) {
        System.out.println(p);
      }


    } catch(Exception ex){
    }

  }
}
