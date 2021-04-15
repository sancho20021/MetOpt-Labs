import methods.multidimensional.*;
import org.junit.Assert;
import org.junit.Test;

public class MultidimensionalTester {
    QuadraticFunction f1 = new QuadraticFunction(
            new FullMatrix(
                    128, 126,
                    126, 128),
            new Vector(-10, 30),
            13
    );
    double minEigen1 = 2;
    double maxEigen1 = 254;


    QuadraticFunction f2 = new QuadraticFunction(
            new FullMatrix(
                    254, 506,
                    506, 254),
            new Vector(50, 130),
            -111
    );
    double minEigen2 = 2;
    double maxEigen2 = 1014;

    QuadraticFunction f3 = new QuadraticFunction(
            new FullMatrix(
                    422, -420,
                    -420, 422),
            new Vector(-192, 50),
            -25
    );
    double minEigen3 = 2;
    double maxEigen3 = 842;

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

    private void all_equal(QuadraticFunction f, double minEigen, double maxEigen) {
        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
                var startX = new Vector(i, j);
                double eps = 1e-2;
                Vector xmin1 = new GradientDescentMinimizer(f, 2 / (minEigen + maxEigen), startX, eps).findMinimum();
                double v1 = f.get(xmin1);
                Vector xmin2 = new ConjugateGradientsMinimizer(f, startX, eps).findMinimum();
                double v2 = f.get(xmin2);
                Vector xmin3 = new FastestDescent(f, startX, eps, 2 / (minEigen + maxEigen)).findMinimum();
                double v3 = f.get(xmin3);
                System.err.println("Testing (i, j) = " + i + " " + j);
                Assert.assertTrue(
                        "Methods differ (GDM, CGM): (" + v1 + " " + v2 + ")\n   X differs:\n        GDM    " + xmin1.toString() + "\n       CGM    " + xmin2.toString(),
                        Math.abs(v1 - v2) < 2 * eps);
                Assert.assertTrue(
                        "Methods differ (GDM, FD): (" + v1 + " " + v3 + ")\n    X differs:\n        GDM    " + xmin1.toString() + "\n       CGM    " + xmin3.toString(),
                        Math.abs(v1 - v3) < 2 * eps);
            }
        }
    }
}
