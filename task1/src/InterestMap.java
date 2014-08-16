import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

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
  JMenuItem menuLoadPOI;
  JFileChooser fc;
  MapPanel mapPanel;
  JPanel toolBar;
  JButton panNButton, panSButton, panEButton, panWButton, zoomInButton,
      zoomOutButton, resetButton;

  public InterestMap() {
    mapPanel = new MapPanel("poi.csv", "types.csv");
    toolBar = new JPanel();

    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    menuFile.setMnemonic(KeyEvent.VK_F);

    menuLoadPOI = new JMenuItem("Load Points of Interest", KeyEvent.VK_P);
    menuLoadPOI.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(InterestMap.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          mapPanel.mapPOI(file);
          InterestMap.this.revalidate();
          InterestMap.this.repaint();
        } else {
          System.out.println("File access cancelled by user.");
        }
      }
    });

    menuFile.add(menuLoadPOI);
    menuBar.add(menuFile);

    fc = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("POI Files",
        "poi", "csv");
    fc.setFileFilter(filter);

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

    this.pack();
  }

  public static void main(String[] args) {
    JFrame frame = new InterestMap();
    frame.setTitle("Interest Map");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setSize(400, 400);
    frame.setVisible(true);
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
}
