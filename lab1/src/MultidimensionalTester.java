import methods.multidimensional.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

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
        int dim = 1000;
        double[] es = new double[dim];
        Arrays.fill(es, 2);
        double[] zeros = new double[dim];
        return new QuadraticFunction(new DiagonalMatrix(es), new Vector(zeros), 0);
    }
    static QuadraticFunction crazy = crazyMatrix();
    static double minEigenCrazy = 2;
    static double maxEigenCrazy = 2;

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

    private void all_equal(QuadraticFunction f, double minEigen, double maxEigen) {
        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
//                System.out.println("Testing (i, j) = " + i + " " + j);
//                System.out.flush();
                var startX = new Vector(i, j);
                double eps = 1e-2;

                Vector xmin1 = null;
                try {
                    xmin1 = new GradientDescentMinimizer(f, 2 / (minEigen + maxEigen), startX, eps).findMinimum();
                } catch (TimeoutException e) {
                    Assert.fail("Failed time out GDM");
                }
                double v1 = f.get(xmin1);

                Vector xmin2 = null;
                try {
                    xmin2 = new ConjugateGradientsMinimizer(f, startX, eps).findMinimum();
                } catch (TimeoutException e) {
                    Assert.fail("Failed time out CGM");
                }
                double v2 = f.get(xmin2);

                Vector xmin3 = null;
                try {
                    xmin3 = new FastestDescent(f, startX, eps, 2 / (minEigen + maxEigen)).findMinimum();
                } catch (TimeoutException e) {
                    Assert.fail("Failed time out FD");
                }
                double v3 = f.get(xmin3);

                Assert.assertTrue(
                        "Methods differ (GDM, CGM): (" + v1 + " " + v2 + ")\n   X differs:\n        GDM    " + xmin1.toString() + "\n       CGM    " + xmin2.toString(),
                        Math.abs(v1 - v2) < 2 * eps);
                Assert.assertTrue(
                        "Methods differ (GDM, FD): (" + v1 + " " + v3 + ")\n    X differs:\n        GDM    " + xmin1.toString() + "\n       FD   " + xmin3.toString(),
                        Math.abs(v1 - v3) < 2 * eps);
            }
        }
    }
}
