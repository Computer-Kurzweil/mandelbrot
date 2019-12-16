package org.woehlke.simulation.mandelbrot.view;

import javax.swing.*;
import java.awt.*;

/**
 * TODO write doc.
 */
public class PanelSubtitle extends JPanel {

  public PanelSubtitle(String subtitle) {
      this.setLayout(new FlowLayout());
      this.add(new JLabel(subtitle));
  }

}
