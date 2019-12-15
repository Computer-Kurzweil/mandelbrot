package org.woehlke.simulation.mandelbrot.model;

import org.woehlke.simulation.mandelbrot.model.fractal.ComplexNumber;
import org.woehlke.simulation.mandelbrot.model.fractal.GaussianNumberPlane;
import org.woehlke.simulation.mandelbrot.model.turing.MandelbrotTuringMachine;

import static org.woehlke.simulation.mandelbrot.model.ApplicationStatus.MANDELBROT;

public class ApplicationModel {

    private ApplicationStatus applicationStatus;

    private GaussianNumberPlane gaussianNumberPlane;

    private MandelbrotTuringMachine mandelbrotTuringMachine;

    public ApplicationModel(Point worldDimensions) {
        applicationStatus = MANDELBROT;
        gaussianNumberPlane = new GaussianNumberPlane(worldDimensions);
        mandelbrotTuringMachine = new MandelbrotTuringMachine(gaussianNumberPlane);
    }

    public void click(int xClick, int yClick) {
        ApplicationStatus nextApplicationStatus = MANDELBROT;
        switch (applicationStatus){
            case MANDELBROT:
                nextApplicationStatus = ApplicationStatus.JULIA_SET;
                Point c = new Point(xClick, yClick);
                gaussianNumberPlane.computeTheJuliaSetFor(c);
                break;
            case JULIA_SET:
                nextApplicationStatus = MANDELBROT;
                mandelbrotTuringMachine.computeTheMandelbrotSet();
                break;
        }
        this.applicationStatus = nextApplicationStatus;
    }

    private static final int STATE_MULTILPLIER = 256/ ComplexNumber.MAX_ITERATIONS;

    public int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x,y);
    }

    public void step() {
        if(applicationStatus == MANDELBROT){
            mandelbrotTuringMachine.step();
        }
    }
}
