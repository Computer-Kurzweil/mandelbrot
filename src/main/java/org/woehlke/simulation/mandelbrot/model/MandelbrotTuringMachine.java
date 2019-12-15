package org.woehlke.simulation.mandelbrot.model;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Date: 28.08.13
 * Time: 12:39
 */
public class MandelbrotTuringMachine {

    private TuringPhase turingPhase;
    private TuringPositions turingPositions;
    private GaussianNumberPlane gaussianNumberPlane;

    public MandelbrotTuringMachine(GaussianNumberPlane gaussianNumberPlane) {
        this.gaussianNumberPlane = gaussianNumberPlane;
        this.turingPhase = new TuringPhase();
        this.turingPositions = new TuringPositions(gaussianNumberPlane.getWorldDimensions());
    }

    public void computeTheMandelbrotSet() {
        turingPhase.start();
    }

    public void step() {
        switch(turingPhase.getTuringPhase()){
            case GO_TO_SET: stepGoToSet(); break;
            case WALK_AROUND: stepWalkAround(); break;
            case FILL_THE_INSIDE: fillTheInside(); break;
            case COLOR_THE_OUTSIDE: fillTheOutsideWithColors(); break;
            case ALL_DONE: break;
            default: break;
        }
    }

    private void stepGoToSet(){
        if(gaussianNumberPlane.isInMandelbrotSet(turingPositions.getTuringPosition())){
            turingPositions.markFirstSetPosition();
            turingPhase.finishGoToSet();
        } else {
            this.turingPositions.goForward();
        }
    }

    private void stepWalkAround(){
        if(gaussianNumberPlane.isInMandelbrotSet(turingPositions.getTuringPosition())){
            this.turingPositions.turnRight();
        } else {
            this.turingPositions.turnLeft();
        }
        this.turingPositions.goForward();
        if(turingPositions.isFinishedWalkAround()){
            turingPhase.finishWalkAround();
        }
    }

    private void fillTheInside(){
        gaussianNumberPlane.fillTheInside(turingPositions.getFirstSetPosition());
        turingPhase.finishFillTheInside();
    }

    private void fillTheOutsideWithColors(){
        gaussianNumberPlane.fillTheOutsideWithColors();
        turingPhase.finishFillTheOutsideWithColors();
    }
}
