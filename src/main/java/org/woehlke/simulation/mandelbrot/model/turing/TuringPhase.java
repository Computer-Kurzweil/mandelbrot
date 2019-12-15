package org.woehlke.simulation.mandelbrot.model.turing;

import org.woehlke.simulation.mandelbrot.model.cost.Phase;

public class TuringPhase {

    private volatile Phase turingPhase;

    public TuringPhase() {
        start();
    }

    public void start(){
        this.turingPhase = Phase.SEARCH_THE_SET;
    }

    public void finishGoToSet(){
        //System.out.println("===");
        turingPhase=Phase.WALK_AROUND_THE_SET;
    }

    public void finishWalkAround() {
        turingPhase=Phase.COLOR_THE_OUTSIDE;
        //System.out.println("###");
    }

    public void finishFillTheOutsideWithColors() {
        turingPhase=Phase.ALL_DONE;
        //System.out.println("---");
    }

    public Phase getTuringPhase() {
        return turingPhase;
    }
}
