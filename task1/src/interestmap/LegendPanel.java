package interestmap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class LegendPanel extends JPanel {
  private Map<String, Color> colors;

  public LegendPanel(Map<String, Color> colors) {
    this.updateColors(colors);
  }

  public BufferedImage getImage() throws IOException {
    BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    Graphics g = bi.createGraphics();

    this.print(g);
    g.dispose();
    return bi;
  }

  public void update() {
    this.removeAll();
    for (Map.Entry<String, Color> entry : this.colors.entrySet()) {
      JLabel label = new JLabel(entry.getKey());
      Border border = BorderFactory.createLineBorder(entry.getValue(), 2);
      label.setBorder(border);
      label.setForeground(entry.getValue());
      this.add(label);
    }
  }

  public void updateColors(Map<String, Color> colors) {
    this.colors = colors;
    this.update();
  }
}
