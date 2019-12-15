package org.woehlke.simulation.mandelbrot.model.fractal;


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

    private float real;
    private float img;

    private float realZ=0.0f;
    private float imgZ=0.0f;

    private int iterations;

    public final static int MAX_ITERATIONS = 32;
    public final static int MAX_ITERATIONS_JULIA = 31;
    public final static int IS_INSIDE_FRACTAL_SET = 0;
    private final static float DIVERGENCE_THRESHOLD = 4.0f;

    public float getReal() {
        return real;
    }

    public float getImg() {
        return img;
    }

    public ComplexNumber(float real, float img) {
        this.img = img;
        this.real = real;
        iterations=0;
    }

    public int computeMandelbrotSet() {
        iterations=0;
        realZ=0.0f;
        imgZ=0.0f;
        float newRealZ;
        float newImgZ;
        do {
            newRealZ=realZ*realZ-imgZ*imgZ + real;
            newImgZ=2*realZ*imgZ + img;
            realZ=newRealZ;
            imgZ=newImgZ;
            iterations++;
        } while ((iterations < MAX_ITERATIONS) && isNotDivergent());
        return iterations;
    }

    public int computeJuliaSet(ComplexNumber c) {
        iterations=0;
        realZ = real;
        imgZ = img;
        float newRealZ;
        float newImgZ;
        do {
            newRealZ=realZ*realZ-imgZ*imgZ + c.getReal();
            newImgZ=2*realZ*imgZ + c.getImg();
            realZ=newRealZ;
            imgZ=newImgZ;
            iterations++;
        } while (iterations < MAX_ITERATIONS_JULIA && isNotDivergent());
        return iterations;
    }

    public boolean isInMandelbrotSet() {
        return (iterations == MAX_ITERATIONS);
    }

    public boolean isNotDivergent(){
        return (( realZ*realZ + imgZ*imgZ ) < DIVERGENCE_THRESHOLD);
    }

    @Override
    public String toString() {
        return "ComplexNumber{" +
                "real=" + real +
                ", img=" + img +
                ", realZ=" + realZ +
                ", imgZ=" + imgZ +
                '}';
    }



}
