package org.woehlke.simulation.mandelbrot.model.fractal;

import org.woehlke.simulation.mandelbrot.model.Point;

public class GaussianNumberPlane {

    private volatile int[][] lattice;

    public final static int YET_UNCOMPUTED = -1;

    private final Point worldDimensions;

    public GaussianNumberPlane(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        this.lattice = new int[worldDimensions.getX()][worldDimensions.getY()];
        start();
    }

    public synchronized void start(){
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public synchronized int getCellStatusFor(int x,int y){
        return lattice[x][y]<0?0:lattice[x][y];
    }

    private ComplexNumber getComplexNumberFromLatticeCoordsForMandelbrot(Point turingPosition) {
        float realX = -2.2f + (3.2f*turingPosition.getX())/worldDimensions.getX();
        float imgY = -1.17f + (2.34f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    private ComplexNumber getComplexNumberFromLatticeCoordsForJulia(Point turingPosition) {
        float realX = -1.6f + (3.2f*turingPosition.getX())/worldDimensions.getX();
        float imgY = -1.17f + (2.34f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    public synchronized boolean isInMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForMandelbrot(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public synchronized void fillTheOutsideWithColors(){
        System.out.print(";");
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                if(lattice[x][y] == YET_UNCOMPUTED){
                    isInMandelbrotSet(new Point(x, y));
                }
            }
        }
        System.out.print(";");
    }

    public synchronized void computeTheJuliaSetFor(Point point) {
        ComplexNumber c = getComplexNumberFromLatticeCoordsForMandelbrot(point);
        for(int y = 0; y < worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                Point zPoint = new Point(x, y);
                ComplexNumber z = getComplexNumberFromLatticeCoordsForJulia(zPoint);
                lattice[x][y] = z.computeJuliaSet(c);
            }
        }
    }

    public Point getWorldDimensions() {
        return worldDimensions;
    }
}
