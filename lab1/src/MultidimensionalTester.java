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
        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
                var startX = new Vector(i, j);
                double eps = 1e-2;
                double v1 = f1.get(new GradientDescentMinimizer(f1, 2 / (minEigen1 + maxEigen1), startX, eps).findMinimum());
                double v2 = f1.get(new ConjugateGradientsMinimizer(f1, startX, eps).findMinimum());
                Assert.assertTrue("Methods differ: (" + v1 + " " + v2 + ")", Math.abs(v1 - v2) < 2 * eps);
            }
        }
    }


}
