package org.woehlke.simulation.mandelbrot.config;

import static java.io.File.separator;

public interface ConfigProperties {

    String TITLE = "Mandelbrot Set";
    String SUBTITLE = "Mandelbrot Set drawn by a Turing Machine";
    String COPYRIGHT = "(c) 2019 Thomas Woehlke";
    String WIDTH="640";
    String HEIGHT="468";

    String APP_PROPERTIES_FILENAME ="src" + separator
        +"main" + separator + "resources" + separator  + "application.properties";

    String KEY = "org.woehlke.simulation.mandelbrot.config.";

    String KEY_TITLE = KEY + "title";
    String KEY_SUBTITLE = KEY + "subtitle";
    String KEY_COPYRIGHT = KEY + "copyright";
    String KEY_WIDTH = KEY + "width";
    String KEY_HEIGHT = KEY + "height";

}
