package org.woehlke.simulation.mandelbrot.model.turing;

import org.woehlke.simulation.mandelbrot.model.fractal.GaussianNumberPlane;

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

    private volatile GaussianNumberPlane gaussianNumberPlane;
    private volatile TuringPositions turingPositions;
    private volatile TuringPhase turingPhase;

    public MandelbrotTuringMachine(GaussianNumberPlane gaussianNumberPlane) {
        this.gaussianNumberPlane = gaussianNumberPlane;
        this.turingPhase = new TuringPhase();
        this.turingPositions = new TuringPositions(gaussianNumberPlane.getWorldDimensions());
        computeTheMandelbrotSet();
    }

    public void computeTheMandelbrotSet() {
        //System.out.println("*******");
        this.turingPhase.start();
        this.gaussianNumberPlane.start();
        this.turingPositions.start();
        //System.out.println("*******");
    }

    public synchronized void step() {
        switch(turingPhase.getTuringPhase()){
            case SEARCH_THE_SET: stepGoToSet(); break;
            case WALK_AROUND_THE_SET: stepWalkAround(); break;
            case COLOR_THE_OUTSIDE: fillTheOutsideWithColors(); break;
            case ALL_DONE:  //System.out.print("||||"); break;
            default: //System.out.print("////"); break;
        }
    }

    private void stepGoToSet(){
        //System.out.print(".");
        if(this.gaussianNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
            this.turingPositions.markFirstSetPosition();
            this.turingPhase.finishGoToSet();
        } else {
            this.turingPositions.goForward();
        }
        //System.out.print(".");
    }

    private void stepWalkAround(){
        //System.out.print(":");
        if(gaussianNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
            this.turingPositions.turnRight();
        } else {
            this.turingPositions.turnLeft();
        }
        this.turingPositions.goForward();
        if(this.turingPositions.isFinishedWalkAround()){
            this.turingPhase.finishWalkAround();
        }
        //System.out.print(":");
    }

    private void fillTheOutsideWithColors(){
        //System.out.println("@");
        this.gaussianNumberPlane.fillTheOutsideWithColors();
        this.turingPhase.finishFillTheOutsideWithColors();
        //System.out.println("@");
    }
}
