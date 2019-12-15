package org.woehlke.simulation.mandelbrot.control;

import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.Point;
import org.woehlke.simulation.mandelbrot.view.ComplexNumberPlaneCanvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Date: 05.02.2006
 * Time: 00:36:20
 */
public class ControllerThread extends Thread implements Runnable {

    private volatile ApplicationModel applicationModel;
    private volatile ComplexNumberPlaneCanvas canvas;

    private final int THREAD_SLEEP_TIME = 10;
    private Boolean goOn;

    public ControllerThread(ComplexNumberPlaneCanvas canvas) {
        goOn = Boolean.TRUE;
        this.canvas = canvas;
        this.applicationModel = canvas.getApp();
    }

    public void run() {
        boolean doIt;
        do {
            synchronized (goOn) {
                doIt = goOn.booleanValue();
            }
            this.applicationModel.step();
            canvas.repaint();
            try { sleep(THREAD_SLEEP_TIME); }
            catch (InterruptedException e) { System.out.println(e.getMessage()); }
        }
        while (doIt);
    }

    public void exit() {
        synchronized (goOn) {
            goOn = Boolean.FALSE;
        }
    }

}
