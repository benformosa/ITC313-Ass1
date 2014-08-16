import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import poi.POI;
import poi.POIFactory;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {
  private final static int MAX_SIZE = 100;
  private final static int POINT_SIZE = 2;
  private final static int PAN_DISTANCE = 1;
  private final static int ZOOM_DISTANCE = 1;
  private final static int MAX_SCALE = 100;

  public static void main(String args[]) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MapPanel("2.csv", "types.csv"));
        frame.pack();
        frame.setVisible(true);
      }
    });
  }

  private Map<String, Color> colors;
  private List<POI> poi;
  private Point2D viewPoint = new Point(0, 0);
  private int scale = 1;
  private File poiFile;
  private File typeFile;

  public MapPanel(File poiFile, File typeFile) {
    super(true);
    this.setPreferredSize(new Dimension(MAX_SIZE, MAX_SIZE));
    setTypeFile(typeFile);
    mapPOI(poiFile);
  }

  public MapPanel(String poiFilename, String typeFilename) {
    this(new File(poiFilename), new File(typeFilename));
  }

  /*
   * Set the file from which types will be loaded. Will not change the displayed
   * map, call mapPOI after setTypeFile to update.
   */
  public void setTypeFile(File typeFile) {
    this.typeFile = typeFile;
  }

  /*
   * Set the file from which types will be loaded. Will not change the displayed
   * map, call mapPOI after setTypeFile to update.
   */
  public void setTypeFile(String typeFilename) {
    this.typeFile = new File(typeFilename);
  }

  public void mapPOI(String poiFilename) {
    this.poiFile = new File(poiFilename);
    mapPOI();
  }

  public void mapPOI(File poiFile) {
    this.poiFile = poiFile;
    mapPOI();
  }

  public void mapPOI() {
    try {
      poi = POIFactory.loadPOI(poiFile, typeFile);
      colors = colorMap(typeFile);
    } catch (IOException ex) {
      System.err.println("failed reading from file");
    }
    this.revalidate();
    this.repaint();
  }

  /*
   * create a mapping of Type name to a random color
   */
  public Map<String, Color> colorMap(File file) throws IOException {
    Map<Integer, String> tm = POIFactory.typesMap(file);
    Map<String, Color> cm = new HashMap<String, Color>();
    Random rand = new Random();

    Iterator<Map.Entry<Integer, String>> i = tm.entrySet().iterator();
    while (i.hasNext()) {
      Map.Entry<Integer, String> pairs = i.next();
      Color c = new Color(rand.nextInt(256), rand.nextInt(256),
          rand.nextInt(256), rand.nextInt(256));
      String t = pairs.getValue();
      cm.put(t, c);
      i.remove();
    }
    return cm;
  }

  public Map<String, Color> colorMap(String filename) throws IOException {
    return colorMap(new File(filename));
  }

  public float getScale() {
    Collections.sort(poi, POI.byX);
    float minX = poi.get(0).x;
    float maxX = poi.get(poi.size() - 1).x;
    float rangeX = maxX - minX;

    Collections.sort(poi, POI.byY);
    float minY = poi.get(0).y;
    float maxY = poi.get(poi.size() - 1).y;
    float rangeY = maxY - minY;

    float scale = Math.max(MAX_SIZE, MAX_SIZE) / Math.max(rangeX, rangeY);
    return scale;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2D = (Graphics2D) g.create();
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    // float scale = getScale();
    g2D.scale((double) this.getWidth() / MAX_SIZE, (double) this.getHeight()
        / MAX_SIZE);

    for (POI p : poi) {
      g2D.setColor(colors.get(p.getType()));

      g2D.drawString(p.getType(), p.x, (p.y - 1));
      g2D.fill(new Ellipse2D.Float(p.x * scale, p.y * scale, POINT_SIZE,
          POINT_SIZE));
      // g2D.fill(new Ellipse2D.Float(p.x, p.y, POINT_SIZE, POINT_SIZE));
    }
  }

  private void zoom(boolean in, int distance) {
    if (in) {
      if ((scale - distance) >= 1) {
        scale -= distance;
      }
    } else {
      if ((scale + distance) < MAX_SCALE) {
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
    switch (direction) {
      case 'n':
        viewPoint.setLocation(viewPoint.getX(), viewPoint.getY() + distance);
        break;
      case 's':
        viewPoint.setLocation(viewPoint.getX(), viewPoint.getY() - distance);
        break;
      case 'e':
        viewPoint.setLocation(viewPoint.getX() + distance, viewPoint.getY());
        break;
      case 'w':
        viewPoint.setLocation(viewPoint.getX() - distance, viewPoint.getY());
        break;
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
