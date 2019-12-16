package org.woehlke.simulation.mandelbrot.model.turing;

import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
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
    private volatile TuringPhaseState turingPhaseState;

    public MandelbrotTuringMachine(ApplicationModel model) {
        this.gaussianNumberPlane = model.getGaussianNumberPlane();
        this.turingPhaseState = new TuringPhaseState();
        this.turingPositions = new TuringPositions(model.getWorldDimensions());
        start();
    }

    public void start() {
        //System.out.println("*******");
        this.turingPhaseState.start();
        this.gaussianNumberPlane.start();
        this.turingPositions.start();
        //System.out.println("*******");
    }

    public synchronized boolean step() {
        boolean repaint=true;
        switch(turingPhaseState.getTuringTuringPhase()){
            case SEARCH_THE_SET:
                stepGoToSet();
                repaint=false;
                break;
            case WALK_AROUND_THE_SET:
                stepWalkAround();
                break;
            case COLOR_THE_OUTSIDE:
                fillTheOutsideWithColors();
                break;
            case ALL_DONE:
            default:
                repaint=false;
                break;
        }
        return repaint;
    }

    private void stepGoToSet(){
        //System.out.print(".");
        if(this.gaussianNumberPlane.isInMandelbrotSet(this.turingPositions.getTuringPosition())){
            this.turingPositions.markFirstSetPosition();
            this.turingPhaseState.finishGoToSet();
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
            this.turingPhaseState.finishWalkAround();
        }
        //System.out.print(":");
    }

    private void fillTheOutsideWithColors(){
        //System.out.println("@");
        this.gaussianNumberPlane.fillTheOutsideWithColors();
        this.turingPhaseState.finishFillTheOutsideWithColors();
        //System.out.println("@");
    }
}
