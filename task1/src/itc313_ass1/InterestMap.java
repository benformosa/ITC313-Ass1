package itc313_ass1;

import itc313_ass1.MapPanel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InterestMap extends JFrame implements ActionListener {
  JMenuItem menuLoadPOI;
  JFileChooser fc;
  MapPanel mapPanel;
  JPanel toolBar;
  JButton panNButton, panSButton, panEButton, panWButton, zoomInButton, zoomOutButton;

  public InterestMap() {
    try {
      mapPanel = new MapPanel("1.poi");
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
            System.out.println(file.getName());
          } else {
            System.out.println("File access cancelled by user.");
          }
        }
      });

      menuFile.add(menuLoadPOI);
      menuBar.add(menuFile);

      fc = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("POI Files", "poi", "csv");
      fc.setFileFilter(filter);

      panNButton = new JButton("↑");
      panSButton = new JButton("↓");
      panEButton = new JButton("→");
      panWButton = new JButton("←");
      zoomInButton = new JButton("+");
      zoomOutButton = new JButton("-");
      panNButton.addActionListener(this);
      panSButton.addActionListener(this);
      panEButton.addActionListener(this);
      panWButton.addActionListener(this);
      zoomInButton.addActionListener(this);
      zoomOutButton.addActionListener(this);

      toolBar.add(panNButton);
      toolBar.add(panSButton);
      toolBar.add(panEButton);
      toolBar.add(panWButton);
      toolBar.add(zoomInButton);
      toolBar.add(zoomOutButton);

      this.setJMenuBar(menuBar);
      this.add(toolBar, BorderLayout.NORTH);
      this.add(mapPanel, BorderLayout.CENTER);

      this.pack();
    } catch(IOException ex){
    }
  }

  public static void main(String[] args) {
    JFrame frame = new InterestMap();
    frame.setTitle("Interest Map");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setSize(600, 400);
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == panNButton) {
      mapPanel.panN();
    } else if(e.getSource() == panSButton) {
      mapPanel.panS();
    } else if(e.getSource() == panEButton) {
      mapPanel.panE();
    } else if(e.getSource() == panWButton) {
      mapPanel.panW();
    } else if(e.getSource() == zoomInButton) {
      mapPanel.zoomIn();
    } else if(e.getSource() == zoomOutButton) {
      mapPanel.zoomOut();
    }
    mapPanel.repaint();
  }
}
