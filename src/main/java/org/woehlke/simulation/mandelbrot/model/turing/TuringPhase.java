package org.woehlke.simulation.mandelbrot.model.turing;

public class TuringPhase {

    private volatile Phase turingPhase;

    public TuringPhase() {
        start();
    }

    public void start(){
        this.turingPhase = Phase.GO_TO_SET;
    }

    public void finishGoToSet(){
        System.out.println("===");
        turingPhase=Phase.WALK_AROUND;
    }

    public void finishWalkAround() {
        turingPhase=Phase.COLOR_THE_OUTSIDE;
        System.out.println("###");
    }

    public void finishFillTheOutsideWithColors() {
        turingPhase=Phase.ALL_DONE;
        System.out.println("---");
    }

    public Phase getTuringPhase() {
        return turingPhase;
    }
}
