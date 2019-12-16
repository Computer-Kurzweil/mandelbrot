package org.woehlke.simulation.mandelbrot.view;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.control.ControllerThread;
import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.helper.Point;
import org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus;

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
    private volatile PanelSubtitle panelSubtitle;
    private volatile PanelCopyright panelCopyright;
    private volatile ApplicationModel applicationModel;
    private volatile JSeparator separator;
    private volatile Config config;

    public FrameMandelbrotSetApplication(Config config) {
        super(config.getTitle());
        this.config = config;
        this.applicationModel = new ApplicationModel(config);
        BoxLayout layout = new BoxLayout(rootPane, BoxLayout.PAGE_AXIS);
        this.canvas = new CanvasComplexNumberPlane(applicationModel);
        this.panelButtons = new PanelButtons(this);
        this.panelSubtitle = new PanelSubtitle(config.getSubtitle());
        this.panelCopyright = new PanelCopyright(config.getCopyright());
        this.separator = new JSeparator();
        rootPane.setLayout(layout);
        rootPane.add(panelSubtitle);
        rootPane.add(canvas);
        rootPane.add(panelCopyright);
        rootPane.add(separator);
        rootPane.add(panelButtons);
        addWindowListener(this);
        this.canvas.addMouseListener(   this);
        showMe();
        this.controllerThread = new ControllerThread(canvas);
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

    public void setMode(ApplicationStatus mode) {
        applicationModel.setApplicationStatus(mode);
    }

    /**
     * TODO write doc.
     */
    public void showMe() {
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
        Rectangle rectangle = new Rectangle(mystartX, mystartY, mywidth, myheight);
        Dimension dimension = new Dimension(mywidth, myheight);
        this.setBounds(rectangle);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        setVisible(true);
        toFront();
    }
}
