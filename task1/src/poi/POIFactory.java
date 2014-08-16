package poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

public class POIFactory {
  public static POI createPOI(File typesFile, int t, float x, float y)
      throws IOException {
    Map<Integer, String> types = typesMap(typesFile);
    return createPOI(types, t, x, y);

  }

  public static POI createPOI(Map<Integer, String> types, int t, float x, float y) {
    String type = types.get(t);
    if(type == null) {
      type = "";
    }
    return new POI(type, x, y);
  }

  public static POI createPOI(String filename, int t, float x, float y)
    throws IOException {
    return createPOI(new File(filename), t, x, y);
  }

  /*
   * returns a list of POIs, given a csv of POIs and a csv type mapping. The
   * format of the POI csv is int Type, float X co-ordinate, float Y co-ordinate
   */
  public static List<POI> loadPOI(File poiFile, File typesFile)
    throws FileNotFoundException, IOException {
    Map<Integer, String> types = typesMap(typesFile);
    List<POI> poi = new ArrayList<POI>();

    float x, y;
    int t;

    // Read in each line of the csv
    CSVReader reader = new CSVReader(new FileReader(poiFile));
    String[] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      // Skip this line if it starts with # or is blank.
      if (nextLine[0].startsWith("#") || nextLine[0].trim().length() <= 0) {
        continue;
      }

      // Parse the line and create the POI
      try {
        t = Integer.parseInt(nextLine[0]);
        x = Float.parseFloat(nextLine[1]);
        y = Float.parseFloat(nextLine[2]);

        poi.add(createPOI(types, t, x, y));
      } catch (NumberFormatException ex) {
        System.err.println("Could not read from file");
      }
    }
    reader.close();
    return poi;
  }

  /*
   * returns a list of POIs, given the filenames of a csv of POIs and a csv type
   * mapping
   */
  public static List<POI> loadPOI(String poiFilename, String typesFilename)
    throws FileNotFoundException, IOException {
    return loadPOI(new File(poiFilename), new File(typesFilename));
  }

  /*
   * Return a map of int to String given a csv file. The format of the csv is
   * int Key, String value
   */
  public static Map<Integer, String> typesMap(File typesFile)
    throws IOException {
    HashMap<Integer, String> types = new HashMap<Integer, String>();

    // Read in each line of the csv
    CSVReader reader = new CSVReader(new FileReader(typesFile));
    String[] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      // Skip this line if it starts with # or is blank.
      if (nextLine[0].startsWith("#") || nextLine[0].trim().length() <= 0) {
        continue;
      }

      // Parse the line and add it to the map
      try {
        types.put(Integer.parseInt(nextLine[0]), nextLine[1]);
      } catch (NumberFormatException ex) {
        System.err.println("Could not read type number from file");
      }
    }
    reader.close();
    return types;
  }

  public static Map<Integer, String> typesMap(String filename)
    throws FileNotFoundException, IOException {
    return typesMap(new File(filename));
  }
}
