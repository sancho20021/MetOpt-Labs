import methods.multidimensional.*;
import org.junit.Assert;
import org.junit.Test;

public class MultidimensionalTester {

    @Test
    public void test01() {
        QuadraticFunction f = new QuadraticFunction(
                new FullMatrix(
                        128, 126,
                        126, 128),
                new Vector(-10, 30),
                13
        );
        double minEigen = 2;
        double maxEigen = 254;
        var startX = new Vector(0, 0);
        double eps = 1e-2;
        Assert.assertTrue("norm test",
                Math.abs(f.get(new ConjugateGradientsMinimizer(f, startX, eps).findMinimum())
                        - f.get(new GradientDescentMinimizer(f, 2 / (minEigen + maxEigen), startX, eps).findMinimum()))
                        < 2 * eps);
    }
}
