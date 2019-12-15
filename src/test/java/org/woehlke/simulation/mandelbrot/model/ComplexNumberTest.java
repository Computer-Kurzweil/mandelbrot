package org.woehlke.simulation.mandelbrot.model;

import org.junit.Assert;
import org.junit.Test;
import org.woehlke.simulation.mandelbrot.model.fractal.ComplexNumber;

/**
 * (C) 2006 - 2015 Thomas Woehlke.
 * https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html
 * @author Thomas Woehlke
 * Created by tw on 24.08.15.
 */
public class ComplexNumberTest {

    @Test
    public void computeMandelbrotIterationsTest1(){
        ComplexNumber complexNumber1 = new ComplexNumber(0.0d,0.0d);
        int iterations = complexNumber1.computeMandelbrotSet();
        Assert.assertEquals(ComplexNumber.MAX_ITERATIONS, iterations);
    }

    @Test
    public void computeMandelbrotIterationsTest2(){
        ComplexNumber complexNumber2 = new ComplexNumber(1.0d,1.2d);
        int iterations = complexNumber2.computeMandelbrotSet();
        Assert.assertNotEquals(ComplexNumber.MAX_ITERATIONS,iterations);
        Assert.assertTrue(iterations < ComplexNumber.MAX_ITERATIONS);
    }

    @Test
    public void computeJuliaIterationsTest1(){
        ComplexNumber z = new ComplexNumber(0.1d,0.2d);
        ComplexNumber c = new ComplexNumber(0.2d,0.1d);
        int iterations = z.computeJuliaSet(c);
        Assert.assertEquals(ComplexNumber.MAX_ITERATIONS_JULIA, iterations);
    }

    @Test
    public void computeJuliaIterationsTest2(){
        ComplexNumber z = new ComplexNumber(0.1d,0.2d);
        ComplexNumber c = new ComplexNumber(1.0d,1.1d);
        int iterations = z.computeJuliaSet(c);
        Assert.assertNotEquals(ComplexNumber.MAX_ITERATIONS,iterations);
        Assert.assertTrue(iterations < ComplexNumber.MAX_ITERATIONS);
    }
}
