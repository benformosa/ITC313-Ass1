package itc313_ass1;

import itc313_ass1.*;
import java.awt.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;

/*
 * A map panel represents a section of a map, including a scale and a reference for its origin.
 * Co-ordinates are Geographic lattitude and longitude.
 */
public class MapPanel extends JPanel {
  File file;
  int scale; /* scale is number of seconds per pixel */
  int[] origin;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
  
  /*
   * Open a csv file and convert into an array of PointOfInterests.
   */
  public static PointOfInterest[] readPOI(File file) {
    return null;
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
    String seconds = coord.substring(5, 6);
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
}
