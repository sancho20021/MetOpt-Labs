package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.Constants;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewtonMethodTest {
    public final static double EPS = 1e-7;
    final static AnalyticFunction paraboloid = new AnalyticFunction(2, "x_{0}^2 + x_{1}^2");
    final static AnalyticFunction shiftedParaboloid = new AnalyticFunction(2, "(x_{0} - 2)^2 + (x_{1} - 3)^2");
    final static AnalyticFunction fourthPower = new AnalyticFunction(2, "(x_{0} - 2)^4 + (x_{1})^4");

    @After
    public void after() {
        System.out.println();
    }

    @Test
    public void test01_paraboloid() {
        final Vector x0 = new Vector(4, 4);
        final NewtonMethod m = new NewtonMethod(paraboloid, x0, EPS);
        m.points().forEachOrdered(System.out::println);
    }

    @Test
    public void test02_shifted_paraboloid() {
        final Vector x0 = new Vector(0, 0);
        final NewtonMethod m = new NewtonMethod(shiftedParaboloid, x0, EPS);
        m.points().forEachOrdered(System.out::println);
    }

    @Test
    public void test03_fourthPower() {
        new NewtonMethod(fourthPower, new Vector(3, 3), EPS).points().forEachOrdered(System.out::println);
    }

    @Test
    public void test12_1() {
        new NewtonMethod(new AnalyticFunction(2, "x_{0}^2 + x_{1}^2 - 1.2*x_{0}*x_{1}"), new Vector(4, 1), EPS)
                .points().forEachOrdered(System.out::println);
    }

    @Test
    public void test12_2() {
        new NewtonMethod(new AnalyticFunction(2, "100*(x_{1}-x_{0}^2)^2 + (1-x_{0})^2"), new Vector(-1.2, 1), EPS)
                .points().forEachOrdered(System.out::println);
    }

    @Test
    public void testTester() {
        new MinimizationTester(PowellMethod::new).testAll();
    }

    private void printData(AnalyticFunction f) {
        Vector[] points = new NewtonMethod(f, distantPoint(f), EPS).points().toArray(Vector[]::new);
        System.out.println("Iterations " + points.length);
    }

    /*
    * for function f: R^n -> R
    * returns vector in R^n equal to (228, 228, ..., 228)
    * */
    private Vector distantPoint(AnalyticFunction f) {
        return new Vector(DoubleStream.generate(() -> 228).limit(f.getArity()).toArray());
    }
}