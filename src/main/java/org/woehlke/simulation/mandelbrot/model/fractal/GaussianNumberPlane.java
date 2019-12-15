package org.woehlke.simulation.mandelbrot.model.fractal;

import org.woehlke.simulation.mandelbrot.model.Point;

import java.util.Stack;

public class GaussianNumberPlane {

    private int[][] lattice;

    public final static int YET_UNCOMPUTED = -1;

    private final Point worldDimensions;

    public GaussianNumberPlane(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        this.lattice = new int[worldDimensions.getX()][worldDimensions.getY()];
        start();
    }

    public int getCellStatusFor(int x,int y){
        return lattice[x][y]<0?0:lattice[x][y];
    }

    public void start(){
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public ComplexNumber getComplexNumberFromLatticeCoordsForMandelbrot(Point turingPosition) {
        float realX = -2.2f + (3.2f*turingPosition.getX())/worldDimensions.getX();
        float imgY = -1.17f + (2.34f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    private ComplexNumber getComplexNumberFromLatticeCoordsForJulia(Point turingPosition) {
        float realX = -1.6f + (3.2f*turingPosition.getX())/worldDimensions.getX();
        float imgY = -1.17f + (2.34f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    public boolean isInMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForMandelbrot(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public void fillTheInside(Point firstSetPosition){
        /*
        Point start = firstSetPosition.copy();
        start.move10Left();
        Stack<Point> pointStack = new Stack<Point>();
        pointStack.push(start);
        while(!pointStack.empty()){
            Point p = pointStack.pop();
            if(lattice[p.getX()][p.getY()]==YET_UNCOMPUTED){
                lattice[p.getX()][p.getY()]=0;
                pointStack.push(new Point(p.getX()-1,p.getY()));
                pointStack.push(new Point(p.getX()+1,p.getY()));
                pointStack.push(new Point(p.getX(),p.getY()-1));
                pointStack.push(new Point(p.getX(),p.getY()+1));
            }
        }*/
    }

    public void fillTheOutsideWithColors(){
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                if(lattice[x][y] == YET_UNCOMPUTED){
                    isInMandelbrotSet(new Point(x, y));
                }
            }
        }
    }

    public void computeTheJuliaSetFor(Point point) {
        ComplexNumber c = getComplexNumberFromLatticeCoordsForMandelbrot(point);
        for(int y=0;y<worldDimensions.getY();y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                ComplexNumber z = getComplexNumberFromLatticeCoordsForJulia(new Point(x, y));
                int iterations = z.computeJuliaSet(c);
                lattice[x][y]=iterations;
            }
        }
    }

    public Point getWorldDimensions() {
        return worldDimensions;
    }
}
