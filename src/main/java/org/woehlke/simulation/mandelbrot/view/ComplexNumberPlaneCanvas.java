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
    private Point worldDimensions;

    public ComplexNumberPlaneCanvas(
            Point worldDimensions,
            ApplicationModel app) {
        this.worldDimensions = worldDimensions;
        this.setSize(this.worldDimensions.getX(), this.worldDimensions.getY());
        this.app = app;
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                int colorState = app.getCellStatusFor(x,y);
                System.out.println(colorState);
                Color stateColor = new Color(0,0, colorState);
                g.setColor(stateColor);
                g.drawLine(x,y,x,y);
            }
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public Point getWorldDimensions() {
        return worldDimensions;
    }

}
