package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.Point;

import javax.swing.*;
import java.awt.*;


/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2013 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Date: 05.02.2006
 * Time: 00:51:51
 */
public class ComplexNumberPlaneCanvas extends JComponent {

    private ApplicationModel app;
    private org.woehlke.simulation.mandelbrot.model.Point worldDimensions;
    private Dimension preferredSize;

    public ComplexNumberPlaneCanvas(ApplicationModel app) {
        this.app = app;
        this.worldDimensions = app.getWorldDimensions();
        this.preferredSize = new Dimension(this.worldDimensions.getX(), this.worldDimensions.getY());
        this.setPreferredSize(preferredSize);
    }

    public void paint(Graphics g) {
        this.setPreferredSize(preferredSize);
        super.paintComponent(g);
        int red = 0;
        int green = 0;
        for(int y = 0; y < worldDimensions.getY(); y++){
            for(int x = 0; x < worldDimensions.getX(); x++){
                int blue = (((app.getCellStatusFor(x,y))*8)%256);
                Color stateColor = new Color(red, green, blue);
                g.setColor(stateColor);
                g.drawLine(x,y,x,y);
            }
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public ApplicationModel getApp() {
        return app;
    }
}
