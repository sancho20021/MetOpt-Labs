import methods.multidimensional.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import java.util.Arrays;

public class MultidimensionalTester {
    static QuadraticFunction f1 = new QuadraticFunction(
            new FullMatrix(
                    128, 126,
                    126, 128),
            new Vector(-10, 30),
            13
    );
    static double minEigen1 = 2;
    static double maxEigen1 = 254;


    static QuadraticFunction f2 = new QuadraticFunction(
            new FullMatrix(
                    508, 506,
                    506, 508),
            new Vector(50, 130),
            -111
    );
    static double minEigen2 = 2;
    static double maxEigen2 = 1014;

    static QuadraticFunction f3 = new QuadraticFunction(
            new FullMatrix(
                    422, -420,
                    -420, 422),
            new Vector(-192, 50),
            -25
    );
    static double minEigen3 = 2;
    static double maxEigen3 = 842;

    static QuadraticFunction circle = new QuadraticFunction(
            new DiagonalMatrix(1, 1),
            new Vector(0, 0),
            0
    );
    static double minEigen4 = 1;
    static double maxEigen4 = 1;

    static QuadraticFunction crazyMatrix() {
        int dim = 10000;
        double[] es = new double[dim];
        Arrays.fill(es, 2);
        double[] zeros = new double[dim];
        return new QuadraticFunction(new DiagonalMatrix(es), new Vector(zeros), 0);
    }

    static QuadraticFunction crazy = crazyMatrix();
    static double minEigenCrazy = 2;
    static double maxEigenCrazy = 2;

    private static final Random RANDOM_GENERATOR = new Random();

    @Test
    public void test01_all_equal() {
        all_equal(f1, minEigen1, maxEigen1);
    }

    @Test
    public void test02_all_equal() {
        all_equal(f2, minEigen2, maxEigen2);
    }

    @Test
    public void test03_all_equal() {
        all_equal(f3, minEigen3, maxEigen3);
    }

    @Test
    public void test04_all_equal() {
        all_equal(circle, minEigen4, maxEigen4);
    }

    @Test
    public void test05_crazy_all_equal() {
        all_equal(crazy, minEigenCrazy, maxEigenCrazy);
    }

    private void log(String message) {
        System.out.println(message);
        System.out.flush();
    }

    private void all_equal(QuadraticFunction f, double minEigen, double maxEigen) {
        for (int i = 0; i < 1; i++) {
            var startX = new Vector(RANDOM_GENERATOR.ints(f.getA().size(), -1000, 1000).asDoubleStream().toArray());
            double eps = 1e-2;
            log("Start testing");
            long timeStart = System.currentTimeMillis();
            log("Start testing GDM");
            Vector xmin1 = null;
            try {
                xmin1 = new GradientDescentMinimizer(f, 2 / (minEigen + maxEigen), startX, eps).findMinimum();
            } catch (TimeoutException e) {
                Assert.fail("Failed time out GDM");
            }
            double v1 = f.get(xmin1);
            long timeFinish = System.currentTimeMillis();
            log("GDM was calculated (time: " + (timeFinish - timeStart) + ")");

            timeStart = System.currentTimeMillis();
            log("Start testing CGM");
            Vector xmin2 = null;
            try {
                xmin2 = new ConjugateGradientsMinimizer(f, startX, eps).findMinimum();
            } catch (TimeoutException e) {
                Assert.fail("Failed time out CGM");
            }
            double v2 = f.get(xmin2);
            timeFinish = System.currentTimeMillis();
            log("CGM was calculated (time: " + (timeFinish - timeStart) + ")");

            timeStart = System.currentTimeMillis();
            log("Start testing FD");
            Vector xmin3 = null;
            try {
                xmin3 = new FastestDescent(f, startX, eps, 2 / (minEigen + maxEigen)).findMinimum();
            } catch (TimeoutException e) {
                Assert.fail("Failed time out FD");
            }
            double v3 = f.get(xmin3);
            timeFinish = System.currentTimeMillis();
            log("FD was calculated (time: " + (timeFinish - timeStart) + ")");
            Assert.assertTrue(
                    "Methods differ (GDM, CGM): (" + v1 + " " + v2 + ")\n   X differs:\n        GDM    " + xmin1.toString() + "\n       CGM    " + xmin2.toString(),
                    Math.abs(v1 - v2) < 2 * eps);
            Assert.assertTrue(
                    "Methods differ (GDM, FD): (" + v1 + " " + v3 + ")\n    X differs:\n        GDM    " + xmin1.toString() + "\n       FD   " + xmin3.toString(),
                    Math.abs(v1 - v3) < 2 * eps);
        }
    }


    public static void main(String[] args) throws TimeoutException {
        QuadraticFunction simple = new QuadraticFunction(
                new DiagonalMatrix(1, 1),
                new Vector(0, 0),
                0
        );
        double i = 3, j = 0;
        var startX = new Vector(i, j);
        double eps = 1e-2;
        Vector xmin3 = new FastestDescent(crazyMatrix(), startX, eps, 2).findMinimum();
        double v3 = simple.get(xmin3);
        System.out.println("Testing (i, j) = " + i + " " + j);
        System.out.println(xmin3 + " " + v3);
        System.out.println("-----------------------");
    }
}
