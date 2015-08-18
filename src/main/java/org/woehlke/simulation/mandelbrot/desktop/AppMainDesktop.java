package org.woehlke.simulation.mandelbrot.desktop;

import org.woehlke.simulation.mandelbrot.view.AppMainFrame;

/**
 * (C) 2006 - 2015 Thomas Woehlke.
 * http://thomas-woehlke.de/p/mandelbrot/
 * @author Thomas Woehlke
 */
public class AppMainDesktop {

    private AppMainDesktop() { }

    /**
     * Starting the App.
     * @param args CLI Parameter
     */
    public static void main(String[] args) {
        AppMainFrame simGen = new AppMainFrame();
    }
}
