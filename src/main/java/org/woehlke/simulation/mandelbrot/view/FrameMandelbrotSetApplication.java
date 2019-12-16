package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.control.ControllerThread;
import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.Point;
import org.woehlke.simulation.mandelbrot.model.cost.ApplicationStatus;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;

/**
 * (C) 2006 - 2013 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 * Date: 04.02.2006
 * Time: 18:47:46
 */
public class FrameMandelbrotSetApplication extends JFrame implements ImageObserver,
        MenuContainer,
        Serializable,
        Accessible,
        WindowListener, MouseListener {

    private volatile ControllerThread controllerThread;
    private volatile CanvasComplexNumberPlane canvas;
    private volatile PanelButtons panelButtons;
    private volatile ApplicationModel applicationModel;
    private volatile Config config;

    public FrameMandelbrotSetApplication(Config config) {
        super(config.getTitle());
        this.config = config;
        BoxLayout layout = new BoxLayout(rootPane, BoxLayout.PAGE_AXIS);
        this.applicationModel = new ApplicationModel(config);
        this.canvas = new CanvasComplexNumberPlane(applicationModel);
        this.controllerThread = new ControllerThread(canvas);
        this.panelButtons = new PanelButtons(this);
        PanelSubtitle panelSubtitle = new PanelSubtitle(config.getSubtitle());
        PanelCopyright panelCopyright = new PanelCopyright(config.getCopyright());
        JSeparator separator = new JSeparator();
        rootPane.setLayout(layout);
        rootPane.add(panelSubtitle);
        rootPane.add(canvas);
        rootPane.add(panelCopyright);
        rootPane.add(separator);
        rootPane.add(panelButtons);
        addWindowListener(this);
        this.canvas.addMouseListener(   this);
        pack();
        setVisible(true);
        toFront();
        this.controllerThread.start();
    }

    private void showMe(){
        pack();
        setVisible(true);
        toFront();
    }

    public void windowOpened(WindowEvent e) {
        showMe();
    }

    public void windowClosing(WindowEvent e) {
        this.controllerThread.exit();
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
        this.controllerThread.exit();
        System.exit(0);
    }

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {
        showMe();
    }

    public void windowActivated(WindowEvent e) {
        showMe();
    }

    public void windowDeactivated(WindowEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        Point c = new Point(e.getX(), e.getY());
        this.applicationModel.click(c);
        showMe();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public Config getConfig() {
        return config;
    }

    public void setMode(ApplicationStatus mode) {
        applicationModel.setApplicationStatus(mode);
    }
}
