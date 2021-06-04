package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.Constants;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
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
        printData(paraboloid);
    }

    @Test
    public void test02_shifted_paraboloid() {
        printData(shiftedParaboloid);
    }

    @Test
    public void test03_fourthPower() {
        printData(fourthPower);
    }

    @Test
    public void test12_1() {
        printData(new AnalyticFunction(2, "x_{0}^2 + x_{1}^2 - 1.2*x_{0}*x_{1}"));
    }

    @Test
    public void test12_2() {
        printData(new AnalyticFunction(2, "100*(x_{1}-x_{0}^2)^2 + (1-x_{0})^2"));
    }

    @Test
    public void testTester() {
        new MinimizationTester(NewtonMethod::new).testAll();
    }

    private static void printData(AnalyticFunction f) {
        Vector[] points = new NewtonMethod(f, distantPoint(f), EPS).points().toArray(Vector[]::new);
        System.out.println("Iterations " + points.length);
        System.out.println("Points " +
                Arrays.stream(points).map(Vector::toString).collect(Collectors.joining(", ", "[", "]")));
        System.out.println("Gnuplottable points");
        System.out.println(getGnuplottablePoints(points));
        System.out.println();
        System.out.println("Gnuplottable levels");
        System.out.println(getGnuplottableLevels(points, f));
        System.out.println();
        if (points.length != 0) {
            Vector x_min = points[points.length - 1];
            System.out.println("Found solution x_min=" + x_min + " f(x_min)=" + f.get(x_min));
        } else {
            System.out.println("No solution found");
        }
    }

    /*
    * Use this to generate data for gnuplot
    * */
    public static String getGnuplottablePoints(Vector[] points) {
        return new StringBuilder("#x y z")
                .append(System.lineSeparator())
                .append(Arrays.stream(points)
                        .map(x -> " " +
                                String.format(Locale.US, "%.5f", x.get(0)) + " " +
                                String.format(Locale.US, "%.5f", x.get(1)) + " 1")
                        .collect(Collectors.joining(System.lineSeparator()))).toString();
    }

    /**
     * Use this to generate contour levels for gnuplot
     * */
    public static String getGnuplottableLevels(Vector[] points, AnalyticFunction f) {
        return Arrays.stream(points)
                .map(x -> String.format(Locale.US, "%.5f", f.get(x)))
                .collect(Collectors.joining(" "));
    }

    /*
    * for function f: R^n -> R
    * returns vector in R^n equal to (228, 228, ..., 228)
    * */
    private static Vector distantPoint(AnalyticFunction f) {
        return new Vector(DoubleStream.generate(() -> 228).limit(f.getArity()).toArray());
    }
}