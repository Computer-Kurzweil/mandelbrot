package org.woehlke.simulation.mandelbrot.model.fractal;

import org.woehlke.simulation.mandelbrot.model.helper.Point;

public class GaussianNumberPlane {

    private volatile int[][] lattice;

    private final Point worldDimensions;
    public final static int YET_UNCOMPUTED = -1;

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
        return (lattice[x][y])<0?0:lattice[x][y];
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForMandelbrot(Point turingPosition) {
        double realX = -2.2f + (3.2f*turingPosition.getX())/worldDimensions.getX();
        double imgY = -1.17f + (2.34f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForJulia(Point turingPosition) {
        double realX = -1.6f + (3.2f*turingPosition.getX())/worldDimensions.getX();
        double imgY = -1.17f + (2.34f*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    public synchronized boolean isInMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForMandelbrot(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public synchronized void fillTheOutsideWithColors(){
        //System.out.print(";");
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                if(lattice[x][y] == YET_UNCOMPUTED){
                    this.isInMandelbrotSet(new Point(x, y));
                }
            }
        }
        //System.out.print(";");
    }

    private volatile ComplexNumber complexNumberForJuliaSetC;

    public synchronized void computeTheJuliaSetFor(Point pointFromMandelbrotSet) {
        this.complexNumberForJuliaSetC = getComplexNumberFromLatticeCoordsForMandelbrot(pointFromMandelbrotSet);
        for(int y = 0; y < worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                Point zPoint = new Point(x, y);
                ComplexNumber z = this.getComplexNumberFromLatticeCoordsForJulia(zPoint);
                lattice[x][y] = z.computeJuliaSet(complexNumberForJuliaSetC);
            }
        }
    }

    public synchronized Point getWorldDimensions() {
        return worldDimensions;
    }

    public void zoomInTheMandelbrotSet(Point zoomPoint) {
    }

    public void zoomInTheJuliaSetFor(Point zoomPoint) {
    }
}
