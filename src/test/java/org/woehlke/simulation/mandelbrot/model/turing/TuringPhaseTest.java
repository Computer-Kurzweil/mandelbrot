package org.woehlke.simulation.mandelbrot.model.turing;

import org.junit.Assert;
import org.junit.Test;

import static org.woehlke.simulation.mandelbrot.model.constant.Phase.*;

public class TuringPhaseTest {

    private TuringPhase turingPhase = new TuringPhase();

    @Test
    public void startTest(){
        turingPhase = new TuringPhase();
        Assert.assertEquals(turingPhase.getTuringPhase(),SEARCH_THE_SET);
        turingPhase.start();
        Assert.assertEquals(turingPhase.getTuringPhase(),SEARCH_THE_SET);
    }

    @Test
    public void finishGoToSetTest(){
        turingPhase = new TuringPhase();
        turingPhase.start();
        Assert.assertEquals(turingPhase.getTuringPhase(),SEARCH_THE_SET);
        turingPhase.finishGoToSet();
        Assert.assertEquals(turingPhase.getTuringPhase(),WALK_AROUND_THE_SET);
    }

    @Test
    public void finishWalkAroundTest() {
        turingPhase = new TuringPhase();
        turingPhase.start();
        turingPhase.finishGoToSet();
        Assert.assertEquals(turingPhase.getTuringPhase(),WALK_AROUND_THE_SET);
        turingPhase.finishWalkAround();
        Assert.assertEquals(turingPhase.getTuringPhase(),COLOR_THE_OUTSIDE);
    }

    @Test
    public void finishFillTheOutsideWithColorsTest() {
        turingPhase = new TuringPhase();
        turingPhase.start();
        turingPhase.finishGoToSet();
        turingPhase.finishWalkAround();
        Assert.assertEquals(turingPhase.getTuringPhase(),COLOR_THE_OUTSIDE);
        turingPhase.finishFillTheOutsideWithColors();
        Assert.assertEquals(turingPhase.getTuringPhase(),ALL_DONE);
    }
}
