package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.control.ControllerThread;
import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.Point;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2013 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Date: 04.02.2006
 * Time: 18:33:14
 */
public class MandelbrotSetApplet extends JApplet implements
    ImageObserver, MenuContainer, Serializable, Accessible {

    private Label title = new Label("Mandelbrot Set drawn by a Turing Machine");
    private ControllerThread controllerThread;
    private ComplexNumberPlaneCanvas canvas;
    private ApplicationModel applicationModel;

    public void init() {
        int scale = 2;
        int width = 320 * scale;
        int height = 234 * scale;
        this.setLayout(new BorderLayout());
        this.add(title, BorderLayout.NORTH);
        Point worldDimensions = new Point(width,height);
        applicationModel = new ApplicationModel(worldDimensions);
        canvas = new ComplexNumberPlaneCanvas(worldDimensions, applicationModel);
        this.add(canvas, BorderLayout.CENTER);
        controllerThread = new ControllerThread(canvas, applicationModel);
        controllerThread.start();
    }

    public void destroy() {
    }

    public void stop() {
    }

    public Point getCanvasDimensions() {
        return canvas.getWorldDimensions();
    }
}
