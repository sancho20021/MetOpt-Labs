package methods.multidimensional.newton.tasks;

import methods.multidimensional.newton.methods.MinimizationTester;
import methods.multidimensional.newton.methods.NewtonWithDDTest;
import methods.multidimensional.newton.methods.PowellMethod;
import methods.multidimensional.newton.methods.QuasiNewtonianDFP;
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
            new Vector(-1.2, 2)
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
            PowellMethod::new,
            MinimizationTester.STANDARD_EPS,
            true,
            "Дэвидон-Флетчер-Пауэл"
    );

    @Test
    public void testSimple() {
        tester.test(simple);
    }

    @Test
    public void test1() {
        tester.test(changeX0(f1, new Vector(3, 4)));
    }

    @Test
    public void test2() {
        tester.test(f2);
    }

    @Test
    public void test3() {
        tester.test(f3, 1e-6*2);
    }

    @Test
    public void test4() {
        tester.test(f4, 1e-6*5);
    }
}
