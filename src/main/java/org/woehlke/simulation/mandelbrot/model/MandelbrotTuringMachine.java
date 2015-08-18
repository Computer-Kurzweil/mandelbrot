package org.woehlke.simulation.mandelbrot.model;

/**
 * (C) 2006 - 2015 Thomas Woehlke.
 * http://thomas-woehlke.de/p/mandelbrot/
 * @author Thomas Woehlke
 * Date: 28.08.13
 * Time: 12:39
 */
public class MandelbrotTuringMachine {

    public final static int MAX_ITERATIONS = 16;
    private Point turingPosition,worldDimensions,firstSetPosition;

    private int[][] lattice;
    private Phase turingPhase = Phase.GO_TO_SET;
    private Direction direction = Direction.LEFT;
    private int steps = 0;

    public MandelbrotTuringMachine(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        lattice = new int[worldDimensions.getX()][worldDimensions.getY()];
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                lattice[x][y] = -1;
            }
        }
        turingPosition = new Point(worldDimensions.getX()-1,worldDimensions.getY()/2+11);
    }

    public void step(){
        switch(turingPhase){
            case GO_TO_SET: stepGoToSet(); break;
            case WALK_AROUND: stepWalkAround(); break;
            default: break;
        }
    }

    private void stepGoToSet(){
        ComplexNumber position = getComplexNumberFromLatticeCoords(turingPosition);
        int iterations = position.computeMandelbrotIterations(MAX_ITERATIONS);
        //System.out.println(iterations+","+position.toString());
        boolean isInMandelbrotSet = (iterations==MAX_ITERATIONS);
        if(isInMandelbrotSet){
            lattice[turingPosition.getX()][turingPosition.getY()]=0;
            turingPhase=Phase.WALK_AROUND;
            firstSetPosition = new Point(turingPosition);
        } else {
            lattice[turingPosition.getX()][turingPosition.getY()]=iterations;
            turingPosition.setX(turingPosition.getX() - 1);
        }
    }

    private void stepWalkAround(){
        ComplexNumber position = getComplexNumberFromLatticeCoords(turingPosition);
        int iterations = position.computeMandelbrotIterations(MAX_ITERATIONS);
        boolean isInMandelbrotSet = iterations==MAX_ITERATIONS;
        //System.out.println(iterations+","+position.toString()+","+steps);
        if(isInMandelbrotSet){
            lattice[turingPosition.getX()][turingPosition.getY()]=0;
            turnRight();
        } else {
            lattice[turingPosition.getX()][turingPosition.getY()]=iterations;
            turnLeft();
        }
        goForward();
        steps++;
        if(turingPosition.equals(firstSetPosition) && (steps>100)){
            turingPhase = Phase.FILL_THE_INSIDE;
            System.out.println("####");
        }
    }

    private void goForward() {
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

    private void turnRight() {
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

    private void turnLeft() {
        Direction newDirection;
        switch (direction){
            case UP: newDirection=Direction.LEFT; break;
            case RIGHT: newDirection=Direction.UP; break;
            case DOWN:newDirection=Direction.RIGHT; break;
            case LEFT:newDirection=Direction.DOWN; break;
            default:newDirection=direction; break;
        }
        direction=newDirection;
    }

    private ComplexNumber getComplexNumberFromLatticeCoords(Point turingPosition) {
        float realX = -3.0f + (4.0f*turingPosition.getX())/worldDimensions.getX();
        float imgY = -2.0f + (4.0f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    public int getCellStatusFor(int x,int y){
        return lattice[x][y]<0?0:lattice[x][y];
    }

}
