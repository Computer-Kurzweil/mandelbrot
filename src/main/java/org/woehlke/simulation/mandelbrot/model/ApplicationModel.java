package org.woehlke.simulation.mandelbrot.model;

import org.woehlke.simulation.mandelbrot.model.fractal.GaussianNumberPlane;
import org.woehlke.simulation.mandelbrot.model.turing.MandelbrotTuringMachine;

import static org.woehlke.simulation.mandelbrot.model.ApplicationStatus.JULIA_SET;
import static org.woehlke.simulation.mandelbrot.model.ApplicationStatus.MANDELBROT;

public class ApplicationModel {

    private final GaussianNumberPlane gaussianNumberPlane;
    private final MandelbrotTuringMachine mandelbrotTuringMachine;

    private ApplicationStatus applicationStatus;

    public ApplicationModel(Point worldDimensions) {
        gaussianNumberPlane = new GaussianNumberPlane(worldDimensions);
        mandelbrotTuringMachine = new MandelbrotTuringMachine(gaussianNumberPlane);
        applicationStatus = MANDELBROT;
    }

    public void click(Point c) {
        ApplicationStatus nextApplicationStatus = MANDELBROT;
        switch (applicationStatus){
            case MANDELBROT:
                nextApplicationStatus = JULIA_SET;
                gaussianNumberPlane.computeTheJuliaSetFor(c);
                break;
            case JULIA_SET:
                nextApplicationStatus = MANDELBROT;
                mandelbrotTuringMachine.computeTheMandelbrotSet();
                break;
        }
        this.applicationStatus = nextApplicationStatus;
    }

    public void step() {
        if(applicationStatus == MANDELBROT){
            mandelbrotTuringMachine.step();
        }
    }

    public int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x,y);
    }

    public Point getWorldDimensions(){
        return gaussianNumberPlane.getWorldDimensions();
    }
}
