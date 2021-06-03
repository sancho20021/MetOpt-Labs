package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewtonMethodTest {
    public final static double EPS = 1e-7;
    final AnalyticFunction paraboloid = new AnalyticFunction(2, "x_{0}^2 + x_{1}^2");
    final AnalyticFunction shiftedParaboloid = new AnalyticFunction(2, "(x_{0} - 2)^2 + (x_{1} - 3)^2");

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
}