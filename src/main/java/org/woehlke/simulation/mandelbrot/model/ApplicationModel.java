package org.woehlke.simulation.mandelbrot.model;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus;
import org.woehlke.simulation.mandelbrot.model.fractal.GaussianNumberPlane;
import org.woehlke.simulation.mandelbrot.model.helper.Point;
import org.woehlke.simulation.mandelbrot.model.turing.MandelbrotTuringMachine;

import static org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus.*;

public class ApplicationModel {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile MandelbrotTuringMachine mandelbrotTuringMachine;
    private volatile ApplicationStatus applicationStatus;

    public ApplicationModel(Config config) {
        int width = config.getWidth();
        int height = config.getHeight();
        Point worldDimensions = new Point(width,height);
        this.gaussianNumberPlane = new GaussianNumberPlane(worldDimensions);
        this.mandelbrotTuringMachine = new MandelbrotTuringMachine(gaussianNumberPlane);
        this.setApplicationStatus(MANDELBROT);
    }

    public synchronized boolean click(Point c) {
        boolean repaint=false;
        ApplicationStatus nextApplicationStatus = null;
        switch (applicationStatus){
            case MANDELBROT:
                nextApplicationStatus = JULIA_SET;
                gaussianNumberPlane.computeTheJuliaSetFor(c);
                repaint=true;
                break;
            case JULIA_SET:
                nextApplicationStatus = MANDELBROT;
                mandelbrotTuringMachine.computeTheMandelbrotSet();
                break;
            case MANDELBROT_ZOOM:
                nextApplicationStatus = MANDELBROT_ZOOM;
                gaussianNumberPlane.zoomIntoTheMandelbrotSet(c);
                break;
            case JULIA_SET_ZOOM:
                nextApplicationStatus = JULIA_SET_ZOOM;
                gaussianNumberPlane.zoomIntoTheJuliaSetFor(c);
                break;
        }
        this.setApplicationStatus(nextApplicationStatus);
        return repaint;
    }

    public synchronized boolean step() {
        boolean repaint=false;
        switch (applicationStatus){
            case MANDELBROT:
                repaint = mandelbrotTuringMachine.step();
                break;
            case JULIA_SET:
                break;
            case MANDELBROT_ZOOM:
                break;
            case JULIA_SET_ZOOM:
                break;
        }
        return repaint;
    }

    public synchronized int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x,y);
    }

    public Point getWorldDimensions(){
        return gaussianNumberPlane.getWorldDimensions();
    }

    public synchronized ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public synchronized void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public void setModeSwitch() {
        ApplicationStatus nextApplicationStatus = this.applicationStatus;
        switch (applicationStatus){
            case MANDELBROT:
            case JULIA_SET:
                break;
            case MANDELBROT_ZOOM:
                nextApplicationStatus = MANDELBROT;
                break;
            case JULIA_SET_ZOOM:
                nextApplicationStatus = JULIA_SET;
                break;
        }
        this.setApplicationStatus(nextApplicationStatus);
    }

    public void setModeZoom() {
        ApplicationStatus nextApplicationStatus = this.applicationStatus;
        switch (applicationStatus){
            case MANDELBROT:
                nextApplicationStatus = MANDELBROT_ZOOM;
                break;
            case JULIA_SET:
                nextApplicationStatus = JULIA_SET_ZOOM;
                break;
            case MANDELBROT_ZOOM:
            case JULIA_SET_ZOOM:
                break;
        }
        this.setApplicationStatus(nextApplicationStatus);
    }
}
