package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.control.ControllerThread;
import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.Point;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.io.Serializable;

/**
 * (C) 2006 - 2013 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 * Date: 04.02.2006
 * Time: 18:47:46
 */
public class MandelbrotSetFrame extends JFrame implements ImageObserver,
        MenuContainer,
        Serializable,
        Accessible,
        WindowListener {

    private final static String title = "Mandelbrot Set";
    private final static String subtitle = "Mandelbrot Set drawn by a Turing Machine";
    private final ControllerThread controllerThread;
    private final ComplexNumberPlaneCanvas canvas;
    private final ApplicationModel applicationModel;

    public MandelbrotSetFrame() {
        super(title);
        BorderLayout layout = new BorderLayout();
        int width = 640;
        int height = 468;
        JLabel subtitleLabel = new JLabel(subtitle);
        Point worldDimensions = new Point(width,height);
        this.applicationModel = new ApplicationModel(worldDimensions);
        this.canvas = new ComplexNumberPlaneCanvas(applicationModel);
        this.controllerThread = new ControllerThread(canvas);
        this.setLayout(layout);
        this.add(subtitleLabel, BorderLayout.NORTH);
        this.add(canvas, BorderLayout.CENTER);
        pack();
        setVisible(true);
        toFront();
        addWindowListener(this);
        this.canvas.addMouseListener(   this.controllerThread);
        this.controllerThread.start();
    }

    public void windowOpened(WindowEvent e) {
        pack();
        setVisible(true);
        toFront();
    }

    /*
    private Rectangle getMyBounds(){
        int startX = 100;
        int startY = 100;
        int width = applicationModel.getWorldDimensions().getX();
        int height = applicationModel.getWorldDimensions().getY() + 30;
        return new Rectangle(startX, startY, width, height);
    }
    */


    public void windowClosing(WindowEvent e) {
        this.controllerThread.exit();
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
        this.controllerThread.exit();
        System.exit(0);
    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {
        pack();
        setVisible(true);
        toFront();
    }

    public void windowActivated(WindowEvent e) {
        pack();
        setVisible(true);
        toFront();
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
