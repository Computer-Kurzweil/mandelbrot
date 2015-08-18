package org.woehlke.simulation.mandelbrot.control;

import org.woehlke.simulation.mandelbrot.model.MandelbrotTuringMachine;
import org.woehlke.simulation.mandelbrot.view.ComplexNumberPlaneCanvas;

/**
 * (C) 2006 - 2015 Thomas Woehlke.
 * http://thomas-woehlke.de/p/mandelbrot/
 * @author Thomas Woehlke
 * Date: 05.02.2006
 * Time: 00:36:20
 */
public class ControllerThread extends Thread
        implements Runnable {

    private MandelbrotTuringMachine mandelbrotTuringMachine;
    private ComplexNumberPlaneCanvas canvas;

    private int THREAD_SLEEP_TIME = 20;
    private Boolean goOn;

    public ControllerThread(
            ComplexNumberPlaneCanvas canvas,
            MandelbrotTuringMachine mandelbrotTuringMachine) {
        goOn = Boolean.TRUE;
        this.canvas=canvas;
        this.mandelbrotTuringMachine = mandelbrotTuringMachine;
    }

    public void run() {
        boolean doIt;
        do {
            synchronized (goOn) {
                doIt = goOn.booleanValue();
            }
            mandelbrotTuringMachine.step();
            canvas.repaint();
            try { sleep(THREAD_SLEEP_TIME); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        while (doIt);
    }

    public void exit() {
        synchronized (goOn) {
            goOn = Boolean.FALSE;
        }
    }

}
