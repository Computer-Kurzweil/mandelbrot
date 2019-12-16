package org.woehlke.simulation.mandelbrot.model.turing;

import org.woehlke.simulation.mandelbrot.model.helper.Point;

public class TuringPositions {

    private volatile Point turingPosition;
    private volatile Point worldDimensions;
    private volatile Point firstSetPosition;

    private volatile TuringDirection turingDirection;

    private volatile int steps;

    public TuringPositions(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        start();
    }

    public void start() {
        this.steps = 0;
        this.turingPosition = new Point((worldDimensions.getX()-2),(worldDimensions.getY()/2+11));
        this.turingDirection = TuringDirection.LEFT;
    }

    public synchronized void markFirstSetPosition(){
        this.firstSetPosition = turingPosition;
        this.steps = 0;
    }

    public synchronized Point getTuringPosition() {
        return turingPosition;
    }

    public synchronized void goForward() {
        ////System.out.println(position.toString()+","+steps);
        this.steps++;
        switch (this.turingDirection){
            case UP:
                this.turingPosition.moveUp(); //.setY(turingPosition.getY()-1);
                break;
            case RIGHT:
                this.turingPosition.moveRight(); //.setX(turingPosition.getX()+1);
                break;
            case DOWN:
                this.turingPosition.moveDown();//.setY(turingPosition.getY()+1);
                break;
            case LEFT:
                this.turingPosition.moveLeft();//.setX(turingPosition.getX()-1);
                break;
            default:
                break;
        }
    }

    public synchronized void turnRight() {
        TuringDirection newTuringDirection;
        switch (this.turingDirection){
            case UP: newTuringDirection = TuringDirection.RIGHT; break;
            case RIGHT: newTuringDirection = TuringDirection.DOWN; break;
            case DOWN: newTuringDirection = TuringDirection.LEFT; break;
            case LEFT: newTuringDirection = TuringDirection.UP; break;
            default: newTuringDirection = this.turingDirection; break;
        }
        this.turingDirection = newTuringDirection;
    }

    public synchronized void turnLeft() {
        TuringDirection newTuringDirection;
        switch (this.turingDirection){
            case UP: newTuringDirection = TuringDirection.LEFT; break;
            case RIGHT: newTuringDirection = TuringDirection.UP; break;
            case DOWN: newTuringDirection = TuringDirection.RIGHT; break;
            case LEFT: newTuringDirection = TuringDirection.DOWN; break;
            default: newTuringDirection = this.turingDirection; break;
        }
        this.turingDirection = newTuringDirection;
    }

    public synchronized boolean isFinishedWalkAround() {
        return (this.turingPosition.equals(this.firstSetPosition)) && (this.steps>100);
    }

}
