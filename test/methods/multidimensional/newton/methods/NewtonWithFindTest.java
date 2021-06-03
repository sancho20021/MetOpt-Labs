package methods.multidimensional.newton.methods;

import models.Vector;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static methods.multidimensional.newton.methods.NewtonMethodTest.EPS;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewtonWithFindTest {

    @After
    public void after() {
        System.out.println();
    }

    @Test
    public void test01_paraboloid() {
        final Vector x0 = new Vector(4, 4);
        final NewtonMethod m = new NewtonMethod(NewtonMethodTest.paraboloid, x0, EPS);
        m.points().forEachOrdered(System.out::println);
    }

    @Test
    public void test02_shifted_paraboloid() {
        final Vector x0 = new Vector(0, 0);
        final NewtonMethod m = new NewtonMethod(NewtonMethodTest.shiftedParaboloid, x0, EPS);
        m.points().forEachOrdered(System.out::println);
    }

    @Test
    public void test03_fourthPower() {
        new NewtonWithFind(NewtonMethodTest.fourthPower, new Vector(3, 3), EPS).points().forEachOrdered(System.out::println);
    }
}