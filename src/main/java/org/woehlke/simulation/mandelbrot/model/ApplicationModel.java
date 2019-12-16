package org.woehlke.simulation.mandelbrot.model;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.model.fractal.GaussianNumberPlane;
import org.woehlke.simulation.mandelbrot.model.helper.Point;
import org.woehlke.simulation.mandelbrot.model.state.ApplicationStateMachine;
import org.woehlke.simulation.mandelbrot.model.turing.MandelbrotTuringMachine;

public class ApplicationModel {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile MandelbrotTuringMachine mandelbrotTuringMachine;
    private volatile ApplicationStateMachine applicationStateMachine;

    private volatile Config config;

    public ApplicationModel(Config config) {
        this.config = config;
        this.gaussianNumberPlane = new GaussianNumberPlane(this);
        this.mandelbrotTuringMachine = new MandelbrotTuringMachine(this);
        this.applicationStateMachine = new ApplicationStateMachine();
    }

    public synchronized boolean click(Point c) {
        applicationStateMachine.click();
        boolean repaint=true;
        switch (applicationStateMachine.getApplicationState()){
            case MANDELBROT:
                mandelbrotTuringMachine.start();
                repaint=false;
                break;
            case JULIA_SET:
                gaussianNumberPlane.computeTheJuliaSetFor(c);
                break;
            case MANDELBROT_ZOOM:
                gaussianNumberPlane.zoomIntoTheMandelbrotSet(c);
                break;
            case JULIA_SET_ZOOM:
                gaussianNumberPlane.zoomIntoTheJuliaSetFor(c);
                break;
        }
        return repaint;
    }

    public synchronized boolean step() {
        boolean repaint=false;
        switch (applicationStateMachine.getApplicationState()){
            case MANDELBROT:
                repaint = mandelbrotTuringMachine.step();
                break;
            case JULIA_SET:
            case MANDELBROT_ZOOM:
            case JULIA_SET_ZOOM:
                break;
        }
        return repaint;
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x,y);
    }

    public Point getWorldDimensions() {
        int width = config.getWidth();
        int height = config.getHeight();
        return new Point(width,height);
    }

    public GaussianNumberPlane getGaussianNumberPlane() {
        return gaussianNumberPlane;
    }

    public void setModeSwitch() {
        this.applicationStateMachine.setModeSwitch();
    }

    public void setModeZoom() {
        this.applicationStateMachine.setModeZoom();
    }

    public Config getConfig() {
        return config;
    }
}
