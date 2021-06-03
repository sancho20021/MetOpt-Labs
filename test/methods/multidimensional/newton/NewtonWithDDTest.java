package methods.multidimensional.newton;

import methods.multidimensional.newton.methods.NewtonWithDD;
import methods.multidimensional.quadratic.Constants;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class NewtonWithDDTest {
    /*
    * no minimum
    * */
    public static AnalyticFunction id = new AnalyticFunction(1, "x_{0}");

    /*
    * x_min = 0
    * f_min = 0
    * */
    public static AnalyticFunction square = new AnalyticFunction(1, "x_{0}^2");

    /*
    * x_min = (0, 0)
    * f_min = 4
    * */
    public static AnalyticFunction paraboloid = new AnalyticFunction(2, "4 + x_{0}^2 + x_{1}^2");

    /*
     * x_min = (1, 2)
     * f_min = 0
     * */
    public static AnalyticFunction fourthPower = new AnalyticFunction(2, "(x_{0} - 1)^4 + (x_{1} - 2)^4");

    /*
    * paraboloid
    * x_min = ???
    * f_min = ???
    * it's f1 form MultidimensionalTester
    *  */
    public static AnalyticFunction f1 =
            new AnalyticFunction(2,
                    "64*x_{0}^2 + 126*x_{0}*x_{1} + 64*x_{1}^2 - 10*x_{0} + 30*x_{1} + 13");

    /*
     * paraboloid
     * x_min = ???
     * f_min = ???
     * f2 form MultidimensionalTester
     *  */
    public static AnalyticFunction f2 =
            new AnalyticFunction(2,
                    "254*x_{0}^2 + 506*x_{0}*x_{1} + 254*x_{1}^2 + 50*x_{0} + 130*x_{1} - 111");

    /*
     * paraboloid
     * x_min = ???
     * f_min = ???
     * f3 form MultidimensionalTester
     *  */
    public static AnalyticFunction f3 =
            new AnalyticFunction(2,
                    "211*x_{0}^2 - 420*x_{0}*x_{1} + 211*x_{1}^2 - 192*x_{0} + 50*x_{1} - 25");

    @Test
    public void testId() {
        Optional<Vector> minimum = new NewtonWithDD(id, new Vector(228), Constants.STANDARD_EPS).findMinimum();
        Assert.assertTrue("No solutions should have been founded", minimum.isEmpty());
    }

    @Test
    public void testSquare() {
        Optional<Vector> minimum = new NewtonWithDD(square, new Vector(228), Constants.STANDARD_EPS)
                .findMinimum();
        Assert.assertTrue("Solution not found", minimum.isPresent());
        Assert.assertEquals(0, minimum.get().get(0), 0.1);
    }

    @Test
    public void testParaboloid() {
        Optional<Vector> minimum = new NewtonWithDD(paraboloid, new Vector(228, 1337), Constants.STANDARD_EPS).findMinimum();
        Assert.assertTrue("Solution not found", minimum.isPresent());
        Assert.assertEquals(0, minimum.get().subtract(new Vector(0, 0)).getEuclideanNorm(), 0.1);
    }

    @Test
    public void testFourthPower() {
        Optional<Vector> minimum = new NewtonWithDD(fourthPower, new Vector(228, 1337), Constants.STANDARD_EPS).findMinimum();
        Assert.assertTrue("Solution not found", minimum.isPresent());
        Assert.assertEquals(0, minimum.get().subtract(new Vector(1, 2)).getEuclideanNorm(), 0.1);
    }

}
