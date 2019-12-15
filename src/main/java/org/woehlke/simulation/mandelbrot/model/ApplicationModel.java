package org.woehlke.simulation.mandelbrot.model;

public class ApplicationModel {

    private ApplicationStatus applicationStatus;

    private GaussianNumberPlane gaussianNumberPlane;

    private MandelbrotTuringMachine mandelbrotTuringMachine;

    public ApplicationModel(Point worldDimensions) {
        applicationStatus = ApplicationStatus.MANDELBROT;
        gaussianNumberPlane = new GaussianNumberPlane(worldDimensions);
        mandelbrotTuringMachine = new MandelbrotTuringMachine(gaussianNumberPlane);
    }

    public void click(int xClick, int yClick) {
        ApplicationStatus nextApplicationStatus = ApplicationStatus.MANDELBROT;
        switch (applicationStatus){
            case MANDELBROT:
                nextApplicationStatus =  ApplicationStatus.JULIA_SET;
                Point c = new Point(xClick, yClick);
                gaussianNumberPlane.computeTheJuliaSetFor(c);
                break;
            case JULIA_SET:
                nextApplicationStatus =  ApplicationStatus.MANDELBROT;
                mandelbrotTuringMachine.computeTheMandelbrotSet();
        }
        this.applicationStatus = nextApplicationStatus;
    }

    private static final int STATE_MULTILPLIER = 256/ComplexNumber.MAX_ITERATIONS;

    public int getCellStatusFor(int x, int y) {
        return gaussianNumberPlane.getCellStatusFor(x,y)*8;
    }

    public void step() {
        mandelbrotTuringMachine.step();
    }
}
