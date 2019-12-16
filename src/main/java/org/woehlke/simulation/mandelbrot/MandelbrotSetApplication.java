package org.woehlke.simulation.mandelbrot;

import org.woehlke.simulation.mandelbrot.config.Config;
import org.woehlke.simulation.mandelbrot.view.FrameMandelbrotSetApplication;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 */
public class MandelbrotSetApplication {

    private MandelbrotSetApplication() {
        Config config = new Config();
        FrameMandelbrotSetApplication frame = new FrameMandelbrotSetApplication(config);
    }

    /**
     * Starting the Application.
     * @param args CLI Parameter
     */
    public static void main(String[] args) {
        MandelbrotSetApplication application = new MandelbrotSetApplication();
    }
}
