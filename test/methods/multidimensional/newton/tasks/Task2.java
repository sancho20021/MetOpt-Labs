package methods.multidimensional.newton.tasks;

import methods.multidimensional.newton.methods.*;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Test;

public class Task2 {
    public static MinimizationTester.MinimizationTask changeX0(MinimizationTester.MinimizationTask task, final Vector x0) {
        return new MinimizationTester.MinimizationTask(task.function, task.expectedAns, x0);
    }

    final public static MinimizationTester.MinimizationTask f1 = new MinimizationTester.MinimizationTask(
            NewtonWithDDTest.f21,
            new Vector(1, 1),
            new Vector(-1.2, 1)
    );
    final public static MinimizationTester.MinimizationTask f2 = new MinimizationTester.MinimizationTask(
            NewtonWithDDTest.f22,
            new Vector(3, 2),
            new Vector(4, 1)
    );
    final public static MinimizationTester.MinimizationTask f3 = new MinimizationTester.MinimizationTask(
            NewtonWithDDTest.f23,
            new Vector(0, 0, 0, 0),
            new Vector(4, 1, 3, 4)
    );
    final public static MinimizationTester.MinimizationTask f4 = new MinimizationTester.MinimizationTask(
            NewtonWithDDTest.f24,
            new Vector(1.29164, 1),
            new Vector(4, 1)
    );
    final public static MinimizationTester.MinimizationTask simple = new MinimizationTester.MinimizationTask(
            new AnalyticFunction(2, "x_{0}^2 + x_{1}^2"),
            new Vector(0, 0),
            new Vector(3, 5)
    );
    private final static MinimizationTester tester = new MinimizationTester(
//            PavelMethod::new,
//            QuasiNewtonianDFP::new,
            NewtonWithDD::new,
            1e-7,
            true,
            "Пауэл"
    );

    @Test
    public void testSimple() {
        tester.test(simple);
    }

    @Test
    public void test1() {
        tester.test(f1);
    }

    @Test
    public void test21() {
        tester.test(f2);
    }

    @Test
    public void test22() {
        tester.test(changeX0(f2, new Vector(0, 0)));
    }

    @Test
    public void test3() {
        tester.test(f3, 1e-2 * 3);
    }

    @Test
    public void test4() {
        tester.test(f4, 1e-2 * 5);
    }
    @Test
    public void test42() {
        tester.test(changeX0(f4, new Vector(-2, 7)), 1e-2 * 5);
    }
}
