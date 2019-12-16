package org.woehlke.simulation.mandelbrot.model.turing;

import org.junit.Assert;
import org.junit.Test;

import static org.woehlke.simulation.mandelbrot.model.turing.TuringPhase.*;

public class TuringTuringPhaseStateEnumTest {

    private TuringPhaseState turingPhaseState = new TuringPhaseState();

    @Test
    public void startTest(){
        turingPhaseState = new TuringPhaseState();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),SEARCH_THE_SET);
        turingPhaseState.start();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),SEARCH_THE_SET);
    }

    @Test
    public void finishGoToSetTest(){
        turingPhaseState = new TuringPhaseState();
        turingPhaseState.start();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),SEARCH_THE_SET);
        turingPhaseState.finishGoToSet();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),WALK_AROUND_THE_SET);
    }

    @Test
    public void finishWalkAroundTest() {
        turingPhaseState = new TuringPhaseState();
        turingPhaseState.start();
        turingPhaseState.finishGoToSet();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),WALK_AROUND_THE_SET);
        turingPhaseState.finishWalkAround();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),COLOR_THE_OUTSIDE);
    }

    @Test
    public void finishFillTheOutsideWithColorsTest() {
        turingPhaseState = new TuringPhaseState();
        turingPhaseState.start();
        turingPhaseState.finishGoToSet();
        turingPhaseState.finishWalkAround();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),COLOR_THE_OUTSIDE);
        turingPhaseState.finishFillTheOutsideWithColors();
        Assert.assertEquals(turingPhaseState.getTuringTuringPhase(),ALL_DONE);
    }
}
