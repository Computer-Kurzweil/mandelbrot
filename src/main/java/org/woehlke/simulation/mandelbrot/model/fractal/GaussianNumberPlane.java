package org.woehlke.simulation.mandelbrot.model.fractal;

import org.woehlke.simulation.mandelbrot.model.ApplicationModel;
import org.woehlke.simulation.mandelbrot.model.helper.Point;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Created by tw on 16.12.2019.
 */
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

    public volatile int zoomLevel;

    private volatile Deque<ComplexNumber> complexCenterForZoomedMandelbrot = new ArrayDeque<>();

    public GaussianNumberPlane(ApplicationModel model) {
        this.worldDimensions = model.getWorldDimensions();
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

    public void setModeZoom() {
        zoomLevel = 1;
    }

    public synchronized void start(){
        zoomLevel = 1;
        for(int y = 0;y < this.worldDimensions.getY(); y++){
            for(int x=0; x < worldDimensions.getX(); x++){
                lattice[x][y] = YET_UNCOMPUTED;
            }
        }
    }

    public synchronized int getCellStatusFor(int x,int y){
        return (lattice[x][y])<0?0:lattice[x][y];
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForJulia(Point turingPosition) {
        double realX = complexCenterForJulia.getReal()
            + (complexWorldDimensions.getReal()*turingPosition.getX())/worldDimensions.getX();
        double imgY = complexCenterForJulia.getImg()
            + (complexWorldDimensions.getImg()*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForMandelbrot(Point turingPosition) {
        double realX = complexCenterForMandelbrot.getReal()
            + (complexWorldDimensions.getReal()*turingPosition.getX())/worldDimensions.getX();
        double imgY = complexCenterForMandelbrot.getImg()
            + (complexWorldDimensions.getImg()*turingPosition.getY())/worldDimensions.getY();
        return new ComplexNumber(realX,imgY);
    }

    private synchronized ComplexNumber getComplexNumberFromLatticeCoordsForZoomedMandelbrot(Point turingPosition) {
        double realX = zoomCenter.getReal()
            + (complexWorldDimensions.getReal()*turingPosition.getX())/(worldDimensions.getX()*zoomLevel);
        double imgY = zoomCenter.getImg()
            + (complexWorldDimensions.getImg()*turingPosition.getY())/(worldDimensions.getY()*zoomLevel);
        return new ComplexNumber(realX,imgY);
    }

    /*
    private synchronized boolean isInZooomedMandelbrotSet(Point pixelPoint, ComplexNumber zoomCenter) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForZoomedMandelbrot(pixelPoint, zoomCenter);
        lattice[pixelPoint.getX()][pixelPoint.getY()] = position.computeMandelbrotSet();
        boolean isInZooomedMandelbrotSet = position.isInMandelbrotSet();
        if(isInZooomedMandelbrotSet){
            lattice[pixelPoint.getX()][pixelPoint.getY()]=0;
        }
        return isInZooomedMandelbrotSet;
    }
    */


    public synchronized boolean isInZooomedMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForZoomedMandelbrot(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public synchronized boolean isInMandelbrotSet(Point turingPosition) {
        ComplexNumber position = this.getComplexNumberFromLatticeCoordsForMandelbrot(turingPosition);
        lattice[turingPosition.getX()][turingPosition.getY()] = position.computeMandelbrotSet();
        return position.isInMandelbrotSet();
    }

    public synchronized void fillTheOutsideWithColors(){
        for(int y=0;y<worldDimensions.getY();y++){
            for(int x=0;x<worldDimensions.getX();x++){
                if(lattice[x][y] == YET_UNCOMPUTED){
                    this.isInMandelbrotSet(new Point(x, y));
                }
            }
        }
    }

    private synchronized void computeTheJuliaSetForC(ComplexNumber c) {
        for(int y = 0; y < worldDimensions.getY(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                Point zPoint = new Point(x, y);
                ComplexNumber z = this.getComplexNumberFromLatticeCoordsForJulia(zPoint);
                lattice[x][y] = z.computeJuliaSet(c);
            }
        }
    }

    public synchronized void computeTheJuliaSetFor(Point pointFromMandelbrotSet) {
        ComplexNumber c = getComplexNumberFromLatticeCoordsForMandelbrot(pointFromMandelbrotSet);
        this.complexNumberForJuliaSetC = c;
        computeTheJuliaSetForC(c);
    }

    private volatile ComplexNumber zoomCenter;

    public void start(Point zoomPoint) {
        zoomLevel = 1;
        zoomCenter = complexCenterForMandelbrot;
    }

    public void zoomIntoTheMandelbrotSet(Point zoomPoint) {
        if(this.zoomLevel == 1){
            zoomCenter = getComplexNumberFromLatticeCoordsForMandelbrot(zoomPoint);
        } else {
            zoomCenter = getComplexNumberFromLatticeCoordsForZoomedMandelbrot(zoomPoint);
        }
        complexCenterForZoomedMandelbrot.push(zoomCenter);
        this.zoomLevel++;
        System.out.println("zoomIntoTheMandelbrotSet: "+zoomPoint+" - old:  "+zoomCenter);
        System.out.println("zoomCenterNew: "+zoomCenter+" - zoomLevel:  "+ this.zoomLevel);
        for(int y = 0; y < worldDimensions.getY(); y++){
            for(int x = 0; x < worldDimensions.getX(); x++){
                this.isInZooomedMandelbrotSet(new Point(x, y));
            }
        }
    }

    public void zoomIntoTheJuliaSetFor(Point zoomPoint) {
        ComplexNumber c = this.complexNumberForJuliaSetC;
        computeTheJuliaSetForC(c);
    }

}
