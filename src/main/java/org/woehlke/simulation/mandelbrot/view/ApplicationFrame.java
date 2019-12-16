package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.control.ControllerThread;
import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.helper.Point;
import org.woehlke.simulation.mandelbrot.model.state.ApplicationState;

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
public class ApplicationFrame extends JFrame implements ImageObserver,
        MenuContainer,
        Serializable,
        Accessible,
        WindowListener,
        MouseListener {

    private volatile ControllerThread controllerThread;
    private volatile ApplicationCanvas canvas;
    private volatile ApplicationModel applicationModel;
    private volatile Config config;
    private volatile Rectangle rectangleBounds;
    private volatile Dimension dimensionSize;

    public ApplicationFrame(Config config) {
        super(config.getTitle());
        this.config = config;
        this.applicationModel = new ApplicationModel(config);
        BoxLayout layout = new BoxLayout(rootPane, BoxLayout.PAGE_AXIS);
        this.canvas = new ApplicationCanvas(applicationModel);
        this.controllerThread = new ControllerThread(applicationModel, this.canvas);
        PanelButtons panelButtons = new PanelButtons(this);
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
        showMeInit();
        this.controllerThread.start();
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

    public void showMeInit() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = this.rootPane.getWidth();
        double height  = this.canvas.getHeight() + 180;
        double startX = (screenSize.getWidth() - width) / 2d;
        double startY = (screenSize.getHeight() - height) / 2d;
        int myheight = Double.valueOf(height).intValue();
        int mywidth = Double.valueOf(width).intValue();
        int mystartX = Double.valueOf(startX).intValue();
        int mystartY = Double.valueOf(startY).intValue();
        this.rectangleBounds = new Rectangle(mystartX, mystartY, mywidth, myheight);
        this.dimensionSize = new Dimension(mywidth, myheight);
        this.setBounds(this.rectangleBounds);
        this.setSize(this.dimensionSize);
        this.setPreferredSize(this.dimensionSize);
        setVisible(true);
        toFront();
    }

    /**
     * TODO write doc.
     */
    public void showMe() {
        pack();
        this.setBounds(this.rectangleBounds);
        this.setSize(this.dimensionSize);
        this.setPreferredSize(this.dimensionSize);
        setVisible(true);
        toFront();
    }

    public ControllerThread getControllerThread() {
        return controllerThread;
    }

    public ApplicationCanvas getCanvas() {
        return canvas;
    }

    public ApplicationModel getApplicationModel() {
        return applicationModel;
    }

    public void setModeSwitch() {
        this.applicationModel.setModeSwitch();
    }

    public void setModeZoom() {
        this.applicationModel.setModeZoom();
    }
}