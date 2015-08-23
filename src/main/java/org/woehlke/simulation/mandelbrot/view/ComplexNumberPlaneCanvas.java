package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.model.Point;
import org.woehlke.simulation.mandelbrot.model.MandelbrotTuringMachine;

import javax.swing.*;
import java.awt.*;


/**
 * (C) 2006 - 2013 Thomas Woehlke.
 * http://thomas-woehlke.de/p/mandelbrot/
 * @author Thomas Woehlke
 * Date: 05.02.2006
 * Time: 00:51:51
 */
public class ComplexNumberPlaneCanvas extends JComponent {

    private MandelbrotTuringMachine mandelbrotTuringMachine;
    private Point worldDimensions;

    private static final int STATE_MULTILPLIER = 256/MandelbrotTuringMachine.MAX_ITERATIONS;

    public ComplexNumberPlaneCanvas(
            Point worldDimensions,
            MandelbrotTuringMachine mandelbrotTuringMachine) {
        this.worldDimensions = worldDimensions;
        this.setSize(this.worldDimensions.getX(), this.worldDimensions.getY());
        this.mandelbrotTuringMachine = mandelbrotTuringMachine;
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                int state = mandelbrotTuringMachine.getCellStatusFor(x,y);
                 Color stateColor = new Color(0,0,state*STATE_MULTILPLIER);
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
