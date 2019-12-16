package org.woehlke.simulation.mandelbrot.model;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus;
import org.woehlke.simulation.mandelbrot.model.fractal.GaussianNumberPlane;
import org.woehlke.simulation.mandelbrot.model.helper.Point;
import org.woehlke.simulation.mandelbrot.model.turing.MandelbrotTuringMachine;

import static org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus.JULIA_SET;
import static org.woehlke.simulation.mandelbrot.model.constant.ApplicationStatus.MANDELBROT;

public class ApplicationModel {

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile MandelbrotTuringMachine mandelbrotTuringMachine;
    private volatile ApplicationStatus applicationStatus;

    public ApplicationModel(Config config) {
        int width = config.getWidth();
        int height = config.getHeight();
        Point worldDimensions = new Point(width,height);
        gaussianNumberPlane = new GaussianNumberPlane(worldDimensions);
        mandelbrotTuringMachine = new MandelbrotTuringMachine(gaussianNumberPlane);
        applicationStatus = MANDELBROT;
    }

    public synchronized boolean click(Point c) {
        boolean repaint=false;
        ApplicationStatus nextApplicationStatus = MANDELBROT;
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
                gaussianNumberPlane.zoomInTheMandelbrotSet(c);
                break;
            case JULIA_SET_ZOOM:
                gaussianNumberPlane.zoomInTheJuliaSetFor(c);
                break;
        }
        this.applicationStatus = nextApplicationStatus;
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
}
