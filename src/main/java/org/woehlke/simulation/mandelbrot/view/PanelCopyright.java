package org.woehlke.simulation.mandelbrot.view;

import javax.swing.*;
import java.awt.*;

/**
 * TODO write doc.
 */
public class PanelCopyright extends JPanel {

  private final JLabel subtitleLabel;

  public PanelCopyright(String subtitle) {
    this.subtitleLabel = new JLabel(subtitle);
    FlowLayout layout = new FlowLayout();
    this.setLayout(layout);
    this.add(subtitleLabel);
  }

}
