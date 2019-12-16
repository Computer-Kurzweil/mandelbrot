package org.woehlke.simulation.mandelbrot.model.state;

import static org.woehlke.simulation.mandelbrot.model.state.ApplicationState.*;
import static org.woehlke.simulation.mandelbrot.model.state.ApplicationState.JULIA_SET_ZOOM;

public class ApplicationStateMachine {

    private volatile ApplicationState applicationState;

    public ApplicationStateMachine() {
        this.applicationState = ApplicationState.MANDELBROT;
    }

    public void click(){
        ApplicationState nextApplicationState = null;
        switch (applicationState){
            case MANDELBROT:
                nextApplicationState = JULIA_SET;
                break;
            case JULIA_SET:
                nextApplicationState = MANDELBROT;
                break;
            case MANDELBROT_ZOOM:
                nextApplicationState = MANDELBROT_ZOOM;
                break;
            case JULIA_SET_ZOOM:
                nextApplicationState = JULIA_SET_ZOOM;
                break;
        }
        this.setApplicationState(nextApplicationState);
    }

    public void setModeSwitch() {
        ApplicationState nextApplicationState = this.applicationState;
        switch (applicationState){
            case MANDELBROT:
            case JULIA_SET:
                break;
            case MANDELBROT_ZOOM:
                nextApplicationState = MANDELBROT;
                break;
            case JULIA_SET_ZOOM:
                nextApplicationState = JULIA_SET;
                break;
        }
        this.setApplicationState(nextApplicationState);
    }

    public void setModeZoom() {
        ApplicationState nextApplicationState = this.applicationState;
        switch (applicationState){
            case MANDELBROT:
                nextApplicationState = MANDELBROT_ZOOM;
                break;
            case JULIA_SET:
                nextApplicationState = JULIA_SET_ZOOM;
                break;
            case MANDELBROT_ZOOM:
            case JULIA_SET_ZOOM:
                break;
        }
        this.setApplicationState(nextApplicationState);
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public boolean isModeSwitch() {
        return (applicationState == JULIA_SET) || (applicationState == MANDELBROT);
    }
}
