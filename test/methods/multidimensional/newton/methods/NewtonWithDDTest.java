package methods.multidimensional.newton.methods;

import methods.multidimensional.MultidimensionalTester;
import methods.multidimensional.newton.expressions.Add;
import methods.multidimensional.newton.expressions.Const;
import methods.multidimensional.newton.expressions.Divide;
import methods.multidimensional.newton.expressions.Subtract;
import methods.multidimensional.newton.methods.NewtonWithDD;
import methods.multidimensional.quadratic.ConjugateGradientsMinimizer;
import methods.multidimensional.quadratic.Constants;
import models.Vector;
import models.functions.AnalyticFunction;
import models.functions.QuadraticFunction;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class NewtonWithDDTest {
    public static final Vector START2 = new Vector(228, 1337);

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

    public static AnalyticFunction f1 = MultidimensionalTester.f1.toAnalyticFunction();
    public static AnalyticFunction f2 = MultidimensionalTester.f2.toAnalyticFunction();
    public static AnalyticFunction f3 = MultidimensionalTester.f3.toAnalyticFunction();

    /*
    * x_min = (1, 1)
    * f_min = 0
    * */
    public static AnalyticFunction f21 = new AnalyticFunction(2, "100*(x_{1}-x_{0}^2)^2+(1-x_{0})^2");

    /*
    * x_min = (3, 2)
    * f_min = 0
    * */
    public static AnalyticFunction f22 = new AnalyticFunction(2, "(x_{0}^2+x_{1}-11)^2+(x_{0}+x_{1}^2-7)^2");

    /*
    * x_min = (0, 0, 0, 0)
    * f_min = 0
    * */
    public static AnalyticFunction f23 = new AnalyticFunction(4,
            "(x_{0}+10*x_{1})^2+5*(x_{2}-x_{3})^2+(x_{1}-2*x_{2})^4+10*(x_{0}-2*x_{3})^4");

    /*
    * x_min = (1.29164, 1)
    * f_min = 97.1531
    * */
    public static AnalyticFunction f24 = new AnalyticFunction(2,
            "100" +
                    "-2/(1+(x_{0}-1)^2/4+(x_{1}-1)^2/9)" +
                    "-1/(1+(x_{0}-2)^2/4+(x_{1}-1)^2/9)");

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

    @Test
    public void testF1() {
        testQuadratic(MultidimensionalTester.f1);
    }

    @Test
    public void testF2() {
        testQuadratic(MultidimensionalTester.f2);
    }

    @Test
    public void testF3() {
        testQuadratic(MultidimensionalTester.f3);
    }

    private void testQuadratic(QuadraticFunction qf) {
        Optional<Vector> minimum = new NewtonWithDD(
                qf.toAnalyticFunction(),
                START2,
                Constants.STANDARD_EPS).findMinimum();
        try {
            Vector trueMinimum = new ConjugateGradientsMinimizer(
                    qf,
                    START2,
                    Constants.STANDARD_EPS).findMinimum();
            Assert.assertTrue("Solution not found", minimum.isPresent());
            Assert.assertEquals(0, minimum.get().subtract(trueMinimum).getEuclideanNorm(), 0.1);
            System.out.println("Minimizing f(x)=" + qf.toParsableString());
            System.out.println("Correct result: " + trueMinimum.toString());
            System.out.println("NewtonWithDD result: " + minimum.get().toString());
        } catch (TimeoutException e) {
            System.err.println("Timeout on Conjugate Minimizer: " + e.getMessage());
        }
    }

    /*
     * f(x) = x
     * */
    public static AnalyticFunction id = new AnalyticFunction(1, "x_{0}");

    /*
    * f(x) = x - no minimum/maximum
    * */
    @Test
    @Ignore
    public void testId() {
        Optional<Vector> minimum = new NewtonWithDD(id, new Vector(228), Constants.STANDARD_EPS).findMinimum();
        Assert.assertTrue("No solutions should have been founded", minimum.isEmpty());
    }

}
