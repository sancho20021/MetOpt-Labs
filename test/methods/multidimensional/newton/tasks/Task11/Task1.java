package methods.multidimensional.newton.tasks.Task11;

import methods.multidimensional.newton.methods.*;
import methods.multidimensional.newton.methods.MinimizationTester.MinimizationTask;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Test;

public class Task1 {
    final public static MinimizationTask f1 = new MinimizationTask(
            NewtonWithDDTest.f1,
            new Vector(9.96063, -10.0394),
            new Vector(5, 100));

    final public static MinimizationTask fourthPower = new MinimizationTask(
            NewtonMethodTest.fourthPower,
            new Vector(2, 0),
            new Vector(-1337, 322));

    final public static MinimizationTask shiftedParaboloid = new MinimizationTask(
            NewtonMethodTest.shiftedParaboloid,
            new Vector(2, 3),
            new Vector(5, 100));

    public static void test(
            final AnalyticMinimizer.AnalyticMinimizerCons cons,
            final double eps,
            final String methodName,
            final boolean showPoints,
            final MinimizationTester.MinimizationTask task
    ) {
        final var tester = new MinimizationTester(cons, eps, showPoints, methodName);
        tester.test(task);
    }

    private static void test(final AnalyticMinimizer.AnalyticMinimizerCons cons, final double eps, final double delta, final String methodName) {
        final var tester = new MinimizationTester(cons, eps, true, methodName);
        //tester.test(f1, delta);
        tester.test(fourthPower, delta);
        //tester.test(shiftedParaboloid, delta);
    }

    private static void testNewton(final AnalyticMinimizer.AnalyticMinimizerCons cons, final double eps, final String methodName) {
        test(cons, eps, 2*eps, methodName);
    }

    @Test
    public void testClassicNewton() {
        testNewton(NewtonMethod::new, 1e-2, "Метод Ньютона");
    }

    @Test
    public void testNewtonWithFind() {
        testNewton(NewtonWithFind::new, 1e-2, "Ньютон с одномерной минимизацией");
    }

    @Test
    public void testNewtonWithDD() {
        testNewton(NewtonWithDD::new, 1e-2, "Ньютон с выбором направления спуска");
    }
}
