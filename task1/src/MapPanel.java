import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
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
  private final static double MAX_SCALE = 100;
  private final static int MAX_SIZE = 100;
  private final static double PAN_DISTANCE = 5;
  private final static int POINT_SIZE = 2;
  private final static double ZOOM_DISTANCE = 0.5;

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
  private File poiFile;
  private double scale = 1;
  private File typeFile;
  private Point2D viewPoint = new Point(0, 0);

  public MapPanel() {
    super(true);
    this.setPreferredSize(new Dimension(MAX_SIZE, MAX_SIZE));
  }

  public MapPanel(File poiFile, File typeFile) {
    this();
    setTypeFile(typeFile);
    mapPOI(poiFile);
  }

  public MapPanel(String poiFilename, String typeFilename) {
    this(new File(poiFilename), new File(typeFilename));
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
          rand.nextInt(256));
      String t = pairs.getValue();
      cm.put(t, c);
      i.remove();
    }
    return cm;
  }

  public Map<String, Color> colorMap(String filename) throws IOException {
    return colorMap(new File(filename));
  }

  public Map<String, Color> getColorMap() {
    return this.colors;
  }

  public BufferedImage getImage() throws IOException {
    BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    Graphics g = bi.createGraphics();

    this.print(g);
    g.dispose();
    return bi;
  }

  /*
   * Calculate a scale based on the distribution of points and the MAX_SIZE
   * variable If points exist with x and y around 0 and others with x and y
   * around MAX_SIZE, scale should be around 1.
   */
  public double getScale() {
    Collections.sort(poi, POI.byX);
    float minX = poi.get(0).x;
    float maxX = poi.get(poi.size() - 1).x;
    float rangeX = maxX - minX;

    Collections.sort(poi, POI.byY);
    float minY = poi.get(0).y;
    float maxY = poi.get(poi.size() - 1).y;
    float rangeY = maxY - minY;

    double scale = Math.max(MAX_SIZE, MAX_SIZE) / Math.max(rangeX, rangeY);
    return scale;
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

  public void mapPOI(File poiFile) {
    this.poiFile = poiFile;
    mapPOI();
  }

  public void mapPOI(String poiFilename) {
    this.poiFile = new File(poiFilename);
    mapPOI();
  }

  public void newColors() throws IOException {
    this.colors = colorMap(typeFile);
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

      // g2D.drawString(p.getType(), p.x, (p.y - 1));
      g2D.fill(new Ellipse2D.Double((p.x * scale) + viewPoint.getX(),
          (p.y * scale) + viewPoint.getY(), POINT_SIZE * scale, POINT_SIZE
              * scale));
    }
  }

  private void pan(char direction) {
    pan(direction, scale * PAN_DISTANCE);
  }

  private void pan(char direction, double distance) {
    switch (direction) {
      case 'n':
        viewPoint.setLocation(viewPoint.getX(), viewPoint.getY() + distance);
        break;
      case 's':
        viewPoint.setLocation(viewPoint.getX(), viewPoint.getY() - distance);
        break;
      case 'e':
        viewPoint.setLocation(viewPoint.getX() - distance, viewPoint.getY());
        break;
      case 'w':
        viewPoint.setLocation(viewPoint.getX() + distance, viewPoint.getY());
        break;
    }
    this.revalidate();
    this.repaint();
  }

  public void panE() {
    pan('e');
  }

  public void panN() {
    pan('n');
  }

  public void panS() {
    pan('s');
  }

  public void panW() {
    pan('w');
  }

  public void reset() {
    scale = 1;
    viewPoint.setLocation(0, 0);
    this.revalidate();
    this.repaint();
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

  private void zoom(boolean in) {
    zoom(in, ZOOM_DISTANCE);
  }

  private void zoom(boolean in, double distance) {
    if (in) {
      if ((scale + distance) < MAX_SCALE) {
        scale += distance;
      }
    } else {
      if ((scale - distance) >= 1) {
        scale -= distance;
      } else {
        reset();
      }
    }
    this.revalidate();
    this.repaint();
  }

  public void zoomIn() {
    zoom(true);
  }

  public void zoomOut() {
    zoom(false);
  }
}
