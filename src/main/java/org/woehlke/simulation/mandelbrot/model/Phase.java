package org.woehlke.simulation.mandelbrot.model;

/**
 * (C) 2006 - 2015 Thomas Woehlke.
 * http://thomas-woehlke.de/p/mandelbrot/
 * @author Thomas Woehlke
 * Created by tw on 18.08.15.
 */
public enum Phase {
    GO_TO_SET,
    WALK_AROUND,
    FILL_THE_INSIDE,
    COLOR_THE_OUTSIDE,
    ALL_DONE;
}
