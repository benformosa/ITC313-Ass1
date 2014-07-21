package itc313_ass1;

import itc313_ass1.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;
import au.com.bytecode.opencsv.*;
import java.util.*;

/*
 * A map panel represents a section of a map, including a scale and a reference for its origin.
 * Co-ordinates are Geographic lattitude and longitude.
 */
public class MapPanel extends JPanel {
  File file;
  Set<PointOfInterest> poi;

  //TODO
  //set scale and origin with calculated defaults
  //implement zoom and pan based on scale and origin
  //wrap the view around like asteroids

  private static int PAN_DISTANCE = 1; // pan this many arcseconds by default
  private static int ZOOM_DISTANCE = 100; // change scale by this many arcseconds by default
  private static int MAX_ZOOM_DISTANCE = 1080; // scale should not be greater than this.

  int scale = MAX_ZOOM_DISTANCE; /* scale is number of seconds per pixel */
  /* origin is the co-ordinates of the origin of the view which is represented by MapPanel. */
  int xorigin = 200; /* This panel's origin is xorigin number of pixels west of the prime meridian. */
  int yorigin = -200; /* This panel's origin is yorigin number of pixels north of the equator. */

  public MapPanel() {
    super();
  }

  public MapPanel(String filename) throws IOException {
    this(new File(filename));
  }

  public MapPanel(File file) throws IOException {
    this.file = file;
    this.poi = readPOI(file);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for(PointOfInterest p: poi) {
      // System.out.println((p.x/scale+xorigin) + ", " + (p.y/scale+yorigin));
      g.fillOval(
          p.x/scale - xorigin,
          p.y/scale - yorigin,
          10,
          10);
    }
  }
  
  /*
   * Open a csv file and convert into a Set of PointOfInterests.
   * Columns in the csv are name, type, lattitude, longitude.
   * Note that lattitude corresponds with the y attribute of PointOfInterest.
   */
  public static Set<PointOfInterest> readPOI(File file) throws IOException {
    Set<PointOfInterest> poi = new HashSet<PointOfInterest>();;
    try {
      CSVReader reader = new CSVReader(new FileReader(file));
      String[] nextLine;
      while((nextLine = reader.readNext()) != null) {
        // Skip this line if it starts with # or is blank.
        if(nextLine[0].startsWith("#") || nextLine[0].trim().length() <= 0) {
          continue;
        }

        poi.add(new PointOfInterest(nextLine[0], nextLine[1], coordToSeconds(nextLine[3]), coordToSeconds(nextLine[2])));
      }
    } catch(IOException ex) {
      System.err.println("Error reading file.");
      return null;
    }
    return poi;
  }

  /*
   * Convert the friendly numeric co-ordinates to a single number of seconds.
   * EG 0351700S represents 35° 17' 0" South, or -(35*60*60)+(17*60)+0 seconds.
   * Co-ordinates should be 8 digits followed by a single character, any of "NnSsEeWw". Method returns null if the input doesn't follow this format.
   */
  public static Integer coordToSeconds(String coord) {
    // check format
    coord = coord.toLowerCase();

    if(coord.length() != 8) {
      System.err.println("incorrect length");
      return null;
    }
    if(!(coord.matches("\\d+[nsew]$"))) {
      System.err.println("incorrect format");
      return null;
    }

    // Split into cardinality and units
    String cardinality = coord.substring(coord.length() - 1);
    String seconds = coord.substring(5, 7);
    String minutes = coord.substring(3, 5);
    String degrees = coord.substring(0, 3);

    // convert cardinal directions to positive or negative
    int direction = 0;
    if(cardinality.equals("n") || cardinality.equals("e")) {
      direction = 1;
    } else {
      direction = -1;
    }

    // return calulated co-ordinate
    return direction * ((Integer.parseInt(seconds)) + (Integer.parseInt(minutes) * 60 ) + (Integer.parseInt(degrees) * 60 * 60));
  }

  /*
   * Convert seconds to friendly numeric co-ordinates. This is roughly the inverse of coordToSeconds.
   * Cardinality should be a single character, any of "NnSsEeWw" which designates whether this co-ordinate is longditude or lattitude. Note that cardinality is the dimension only, the input -127020, "n" returns "0351700S".
   * Cardinality not matching any compass direction will be ignored and cardinality will be ommitted from the output. 
   */
  public static String secondsToCoords(int seconds, String cardinality) {

    boolean positive = true;
    if(seconds < 0) {
      positive = false;
    }
    seconds = Math.abs(seconds);

    int d = seconds / (60 * 60);
    seconds -= (d * 60 * 60);
    int m = seconds / 60;
    seconds -= (m * 60);
    int s = seconds;

    String c = "";
    cardinality = cardinality.toLowerCase();
    if(cardinality.equals("n") || cardinality.equals("s")) {
      if(positive) {
        c = "N";
      } else {
        c = "S";
      }
    } else if(cardinality.equals("e") || cardinality.equals("s")) {
      if(positive) {
        c = "E";
      } else {
        c = "W";
      }
    }

    String coords = String.format("%03d%02d%02d%s", d, m, s, c);

    return coords;

  }

  public int toPixels(int seconds) {
    return seconds / scale;
  }

  public int toSeconds(int pixels) {
    return pixels * scale;
  }

  private void zoom(boolean in, int distance) {
    if(in) {
      if((scale - distance) >= 0) {
        scale -= distance;
      }
    } else {
      if((scale + distance) < MAX_ZOOM_DISTANCE) {
        scale += distance;
      }
    }
  }

  private void zoom(boolean in) {
    zoom(in, ZOOM_DISTANCE);
  }

  public void zoomIn() {
    zoom(true);
  }

  public void zoomOut() {
    zoom(false);
  }

  private void pan(char direction, int distance) {
    switch(direction) {
      case 'n': this.yorigin += distance; break;
      case 's': this.yorigin -= distance; break;
      case 'e': this.xorigin += distance; break;
      case 'w': this.xorigin -= distance; break;
    }
  }

  private void pan(char direction) {
    pan(direction, scale * PAN_DISTANCE);
  }

  public void panN() {
    pan('n');
  }

  public void panS() {
    pan('s');
  }

  public void panE() {
    pan('e');
  }

  public void panW() {
    pan('w');
  }

}
