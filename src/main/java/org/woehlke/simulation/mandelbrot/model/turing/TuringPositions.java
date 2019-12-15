package org.woehlke.simulation.mandelbrot.model.turing;

import org.woehlke.simulation.mandelbrot.model.Point;

public class TuringPositions {

    private Point turingPosition;
    private Point worldDimensions;
    private Point firstSetPosition;

    private Direction direction;

    private int steps;

    public TuringPositions(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        start();
    }

    public void start() {
        steps = 0;
        this.turingPosition = new Point((worldDimensions.getX()-2),(worldDimensions.getY()/2+11));
        this.direction = Direction.LEFT;
    }

    public void markFirstSetPosition(){
        steps = 0;
        this.firstSetPosition = turingPosition;
    }

    public Point getTuringPosition() {
        return turingPosition;
    }

    public Point getWorldDimensions() {
        return worldDimensions;
    }

    public Point getFirstSetPosition() {
        return firstSetPosition;
    }

    public void goForward() {
        //System.out.println(position.toString()+","+steps);
        steps++;
        switch (direction){
            case UP:
                turingPosition.setY(turingPosition.getY()-1);
                break;
            case RIGHT:
                turingPosition.setX(turingPosition.getX()+1);
                break;
            case DOWN:
                turingPosition.setY(turingPosition.getY()+1);
                break;
            case LEFT:
                turingPosition.setX(turingPosition.getX()-1);
                break;
            default:
                break;
        }
    }

    public void turnRight() {
        Direction newDirection;
        switch (direction){
            case UP: newDirection=Direction.RIGHT; break;
            case RIGHT: newDirection=Direction.DOWN; break;
            case DOWN:newDirection=Direction.LEFT; break;
            case LEFT:newDirection=Direction.UP; break;
            default:newDirection=direction; break;
        }
        direction=newDirection;
    }

    public void turnLeft() {
        Direction newDirection;
        switch (direction){
            case UP: newDirection = Direction.LEFT; break;
            case RIGHT: newDirection = Direction.UP; break;
            case DOWN: newDirection = Direction.RIGHT; break;
            case LEFT: newDirection = Direction.DOWN; break;
            default: newDirection = direction; break;
        }
        direction = newDirection;
    }

    public boolean isFinishedWalkAround() {
        return (turingPosition.equals(firstSetPosition)) && (steps>100);
    }

}
