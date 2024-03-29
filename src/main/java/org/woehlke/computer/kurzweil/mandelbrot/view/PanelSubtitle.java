package org.woehlke.computer.kurzweil.mandelbrot.view;

import javax.swing.*;
import java.awt.*;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Created by tw on 16.12.2019.
 */
public class PanelSubtitle extends JPanel {

  private final static long serialVersionUID = 242L;

  public PanelSubtitle(String subtitle) {
      this.setLayout(new FlowLayout());
      this.add(new JLabel(subtitle));
  }

}
