import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterestMap extends JFrame {
  public InterestMap() {
    JPanel panel = new JPanel();
    this.add(panel, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {
    JFrame frame = new InterestMap();
    frame.setTitle("Interest Map");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    menuFile.setMnemonic(KeyEvent.VK_F);

    JMenuItem menuLoadPOI = new JMenuItem("Load Points of Interest", KeyEvent.VK_P);
    menuLoadPOI.addActionListener(this);
    menuFile.add(menuLoadPOI);

    menuBar.add(menuFile);
    frame.setJMenuBar(menuBar);

    frame.setSize(600, 400);
    frame.setVisible(true);
  }
}
