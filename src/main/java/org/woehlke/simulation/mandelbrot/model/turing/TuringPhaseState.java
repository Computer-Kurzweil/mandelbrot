package org.woehlke.simulation.mandelbrot.model.turing;

public class TuringPhaseState {

    private volatile TuringPhase turingTuringPhase;

    public TuringPhaseState() {
        start();
    }

    public void start(){
        this.turingTuringPhase = TuringPhase.SEARCH_THE_SET;
    }

    public void finishGoToSet(){
        //System.out.println("===");
        turingTuringPhase = TuringPhase.WALK_AROUND_THE_SET;
    }

    public void finishWalkAround() {
        turingTuringPhase = TuringPhase.COLOR_THE_OUTSIDE;
        //System.out.println("###");
    }

    public void finishFillTheOutsideWithColors() {
        turingTuringPhase = TuringPhase.ALL_DONE;
        //System.out.println("---");
    }

    public TuringPhase getTuringTuringPhase() {
        return turingTuringPhase;
    }
}
