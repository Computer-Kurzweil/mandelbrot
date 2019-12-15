package org.woehlke.simulation.mandelbrot.model.fractal;


import java.util.Objects;

/**
 * Mandelbrot Set drawn by a Turing Machine.
 *
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 *
 * Created by tw on 18.08.15.
 */
public class ComplexNumber {

    private volatile double real;
    private volatile double img;

    private volatile double realZ=0.0d;
    private volatile double imgZ=0.0d;

    private volatile int iterations;

    public final static int MAX_ITERATIONS = 32;
    public final static int MAX_ITERATIONS_JULIA = 31;
    private final static double DIVERGENCE_THRESHOLD = 4.0d;

    public double getReal() {
        return real;
    }

    public double getImg() {
        return img;
    }

    public ComplexNumber(double real, double img) {
        this.img = img;
        this.real = real;
        iterations=0;
    }

    public synchronized int computeMandelbrotSet() {
        iterations=0;
        realZ=0.0f;
        imgZ=0.0f;
        double newRealZ;
        double newImgZ;
        do {
            newRealZ=realZ*realZ-imgZ*imgZ + real;
            newImgZ=2*realZ*imgZ + img;
            realZ=newRealZ;
            imgZ=newImgZ;
            iterations++;
        } while ((iterations < MAX_ITERATIONS) && isNotDivergent());
        return iterations;
    }

    public synchronized int computeJuliaSet(ComplexNumber c) {
        iterations=0;
        realZ = real;
        imgZ = img;
        double newRealZ;
        double newImgZ;
        do {
            newRealZ=realZ*realZ-imgZ*imgZ + c.getReal();
            newImgZ=2*realZ*imgZ + c.getImg();
            realZ=newRealZ;
            imgZ=newImgZ;
            iterations++;
        } while (iterations < MAX_ITERATIONS_JULIA && isNotDivergent());
        return iterations;
    }

    public synchronized boolean isInMandelbrotSet() {
        return (iterations == MAX_ITERATIONS);
    }

    public synchronized boolean isNotDivergent(){
        return (( realZ*realZ + imgZ*imgZ ) < DIVERGENCE_THRESHOLD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexNumber)) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.getReal(), getReal()) == 0 &&
            Double.compare(that.getImg(), getImg()) == 0 &&
            Double.compare(that.realZ, realZ) == 0 &&
            Double.compare(that.imgZ, imgZ) == 0 &&
            iterations == that.iterations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReal(), getImg(), realZ, imgZ, iterations);
    }

    @Override
    public String toString() {
        return "ComplexNumber{" +
            "real=" + real +
            ", img=" + img +
            ", realZ=" + realZ +
            ", imgZ=" + imgZ +
            ", iterations=" + iterations +
            '}';
    }


}
