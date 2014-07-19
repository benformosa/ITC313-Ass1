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

  public InterestMap() {
    JPanel panel = new MapPanel();

    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    menuFile.setMnemonic(KeyEvent.VK_F);

    menuLoadPOI = new JMenuItem("Load Points of Interest", KeyEvent.VK_P);
    menuLoadPOI.addActionListener(this);
    menuFile.add(menuLoadPOI);
    menuBar.add(menuFile);

    fc = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("POI Files", "poi", "csv");
    fc.setFileFilter(filter);

    this.setJMenuBar(menuBar);
    this.add(panel, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {
    JFrame frame = new InterestMap();
    frame.setTitle("Interest Map");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setSize(600, 400);
    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == menuLoadPOI) {

      int returnVal = fc.showOpenDialog(InterestMap.this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        System.out.println(file.getName());
      } else {
        System.out.println("File access cancelled by user.");
      }
    }
  }
}
