import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class InterestMap extends JFrame implements ActionListener {
  public static void main(String[] args) {
    JFrame frame = new InterestMap();
    frame.setTitle("Interest Map");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setSize(500, 500);
    frame.setVisible(true);
  }

  JFileChooser fc;
  LegendPanel legendPanel;
  MapPanel mapPanel;
  JMenuItem menuLoadPOI, menuSaveImage, menuNewColors;
  JButton panNButton, panSButton, panEButton, panWButton, zoomInButton,
      zoomOutButton, resetButton;
  JPanel toolBar;

  public InterestMap() {
    mapPanel = new MapPanel("poi.csv", "types.csv");
    legendPanel = new LegendPanel(mapPanel.getColorMap());
    toolBar = new JPanel();

    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    menuFile.setMnemonic(KeyEvent.VK_F);
    JMenu menuMap = new JMenu("Map");
    menuFile.setMnemonic(KeyEvent.VK_M);

    menuLoadPOI = new JMenuItem("Load Points of Interest", KeyEvent.VK_P);
    menuLoadPOI.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "POI Files", "poi", "csv");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(InterestMap.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          mapPanel.mapPOI(file);
          legendPanel.updateColors(mapPanel.getColorMap());
          InterestMap.this.revalidate();
          InterestMap.this.repaint();
        } else {
          System.out.println("File access cancelled by user.");
        }
      }
    });

    menuNewColors = new JMenuItem("Change colors", KeyEvent.VK_C);
    menuNewColors.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          mapPanel.newColors();
          legendPanel.updateColors(mapPanel.getColorMap());
        } catch (IOException e1) {
          System.err.println("Error loading type file");
        }
        InterestMap.this.revalidate();
        InterestMap.this.repaint();
      }
    });

    menuSaveImage = new JMenuItem("Save Image", KeyEvent.VK_S);
    menuSaveImage.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "PNG File", "png");
        fc.setFileFilter(filter);
        int returnVal = fc.showSaveDialog(InterestMap.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();

          try {
            ImageIO.write(InterestMap.this.getImage(), "png", file);
          } catch (IOException ex) {
            System.err.println("Failed to write image to file.");
          }
        }
      }
    });

    menuFile.add(menuLoadPOI);
    menuFile.add(menuSaveImage);
    menuMap.add(menuNewColors);
    menuBar.add(menuFile);
    menuBar.add(menuMap);

    fc = new JFileChooser();

    panNButton = new JButton("↑");
    panSButton = new JButton("↓");
    panEButton = new JButton("→");
    panWButton = new JButton("←");
    zoomInButton = new JButton("+");
    zoomOutButton = new JButton("-");
    resetButton = new JButton("↻");
    panNButton.addActionListener(this);
    panSButton.addActionListener(this);
    panEButton.addActionListener(this);
    panWButton.addActionListener(this);
    zoomInButton.addActionListener(this);
    zoomOutButton.addActionListener(this);
    resetButton.addActionListener(this);

    toolBar.add(panNButton);
    toolBar.add(panSButton);
    toolBar.add(panWButton);
    toolBar.add(panEButton);
    toolBar.add(Box.createHorizontalGlue());
    toolBar.add(zoomInButton);
    toolBar.add(zoomOutButton);
    toolBar.add(Box.createHorizontalGlue());
    toolBar.add(resetButton);

    this.setJMenuBar(menuBar);
    this.add(toolBar, BorderLayout.NORTH);
    this.add(mapPanel, BorderLayout.CENTER);
    this.add(legendPanel, BorderLayout.SOUTH);

    this.pack();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == panNButton) {
      mapPanel.panN();
    } else if (e.getSource() == panSButton) {
      mapPanel.panS();
    } else if (e.getSource() == panEButton) {
      mapPanel.panE();
    } else if (e.getSource() == panWButton) {
      mapPanel.panW();
    } else if (e.getSource() == zoomInButton) {
      mapPanel.zoomIn();
    } else if (e.getSource() == zoomOutButton) {
      mapPanel.zoomOut();
    } else if (e.getSource() == resetButton) {
      mapPanel.reset();
    }
    mapPanel.revalidate();
    mapPanel.repaint();
    InterestMap.this.revalidate();
    InterestMap.this.repaint();
  }

  public BufferedImage getImage() throws IOException {
    BufferedImage mapImage = mapPanel.getImage();
    BufferedImage legendImage = legendPanel.getImage();

    BufferedImage image = new BufferedImage(mapImage.getWidth(),
        mapImage.getHeight() + legendImage.getHeight(),
        BufferedImage.TYPE_INT_RGB);

    Graphics g = image.getGraphics();
    g.drawImage(mapImage, 0, 0, this);
    g.drawImage(legendImage, 0, mapImage.getHeight(), this);
    g.dispose();

    return image;
  }
}
