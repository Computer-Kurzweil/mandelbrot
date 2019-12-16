package org.woehlke.simulation.mandelbrot.model.turing;

public class TuringPhaseState {

    private volatile TuringPhase turingTuringPhase;

    public TuringPhaseState() {
        start();
    }

    public void start(){
        this.turingTuringPhase = TuringPhase.SEARCH_THE_SET;
    }

    public void finishSearchTheSet(){
        turingTuringPhase = TuringPhase.WALK_AROUND_THE_SET;
    }

    public void finishWalkAround() {
        turingTuringPhase = TuringPhase.FILL_THE_OUTSIDE_WITH_COLOR;
    }

    public void finishFillTheOutsideWithColors() {
        turingTuringPhase = TuringPhase.FINISHED;
    }

    public TuringPhase getTuringTuringPhase() {
        return turingTuringPhase;
    }
}
