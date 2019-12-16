package org.woehlke.simulation.mandelbrot.model.turing;

import org.woehlke.simulation.mandelbrot.model.helper.Point;
import org.woehlke.simulation.mandelbrot.model.constant.Direction;

public class TuringPositions {

    private volatile Point turingPosition;
    private volatile Point worldDimensions;
    private volatile Point firstSetPosition;

    private volatile Direction direction;

    private volatile int steps;

    public TuringPositions(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        start();
    }

    public void start() {
        this.steps = 0;
        this.turingPosition = new Point((worldDimensions.getX()-2),(worldDimensions.getY()/2+11));
        this.direction = Direction.LEFT;
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
        switch (this.direction){
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
        Direction newDirection;
        switch (this.direction){
            case UP: newDirection = Direction.RIGHT; break;
            case RIGHT: newDirection = Direction.DOWN; break;
            case DOWN: newDirection = Direction.LEFT; break;
            case LEFT: newDirection = Direction.UP; break;
            default: newDirection = this.direction; break;
        }
        this.direction = newDirection;
    }

    public synchronized void turnLeft() {
        Direction newDirection;
        switch (this.direction){
            case UP: newDirection = Direction.LEFT; break;
            case RIGHT: newDirection = Direction.UP; break;
            case DOWN: newDirection = Direction.RIGHT; break;
            case LEFT: newDirection = Direction.DOWN; break;
            default: newDirection = this.direction; break;
        }
        this.direction = newDirection;
    }

    public synchronized boolean isFinishedWalkAround() {
        return (this.turingPosition.equals(this.firstSetPosition)) && (this.steps>100);
    }

}
