package org.woehlke.simulation.mandelbrot.model.fractal;

import org.woehlke.simulation.mandelbrot.model.helper.Point;

public class GaussianNumberPlane {

    private volatile int[][] lattice;

    private volatile ComplexNumber complexNumberForJuliaSetC;

    private final Point worldDimensions;
    public final static int YET_UNCOMPUTED = -1;

    private final static double complexWorldDimensionRealX = 3.2d;
    private final static double complexWorldDimensionImgY = 2.34d;
    private final static double complexCenterForMandelbrotRealX = -2.2f;
    private final static double complexCenterForMandelbrotImgY = -1.17f;
    private final static double complexCenterForJuliaRealX = -1.6d;
    private final static double complexCenterForJuliaImgY =  -1.17d;

    private volatile ComplexNumber complexWorldDimensions;
    private volatile ComplexNumber complexCenterForMandelbrot;
    private volatile ComplexNumber complexCenterForJulia;

    public GaussianNumberPlane(Point worldDimensions) {
        this.worldDimensions = worldDimensions;
        this.lattice = new int[worldDimensions.getWidth()][worldDimensions.getHeight()];
        this.complexWorldDimensions = new ComplexNumber(
            complexWorldDimensionRealX,
            complexWorldDimensionImgY
        );
        this.complexCenterForMandelbrot = new ComplexNumber(
            complexCenterForMandelbrotRealX,
            complexCenterForMandelbrotImgY
        );
        this.complexCenterForJulia = new ComplexNumber(
            complexCenterForJuliaRealX,
            complexCenterForJuliaImgY
        );
        start();
    }

    public synchronized void start(){
        for(int y = 0;y < this.worldDimensions.getY(); y++){
            for(int x=0; x < worldDimensions.getX(); x++){
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public synchronized int getCellStatusFor(int x,int y){
        return (lattice[x][y])<0?0:lattice[x][y];
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForMandelbrot(Point turingPosition) {
        double realX = complexCenterForMandelbrot.getReal()
            + (complexWorldDimensions.getReal()*turingPosition.getX())/worldDimensions.getX();
        double imgY = complexCenterForMandelbrot.getImg()
            + (complexWorldDimensions.getImg()*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForJulia(Point turingPosition) {
        double realX = complexCenterForJulia.getReal()
            + (complexWorldDimensions.getReal()*turingPosition.getX())/worldDimensions.getX();
        double imgY = complexCenterForJulia.getImg()
            + (complexWorldDimensions.getImg()*turingPosition.getY())/worldDimensions.getY();
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

    public synchronized void computeTheJuliaSetFor(Point pointFromMandelbrotSet) {
        this.complexNumberForJuliaSetC = getComplexNumberFromLatticeCoordsForMandelbrot(pointFromMandelbrotSet);
        for(int y = 0; y < worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                Point zPoint = new Point(x, y);
                ComplexNumber z = this.getComplexNumberFromLatticeCoordsForJulia(zPoint);
                lattice[x][y] = z.computeJuliaSet(this.complexNumberForJuliaSetC);
            }
        }
    }

    public synchronized Point getWorldDimensions() {
        return worldDimensions;
    }

    public void zoomIntoTheMandelbrotSet(Point zoomPoint) {
    }

    public void zoomIntoTheJuliaSetFor(Point zoomPoint) {
    }
}
