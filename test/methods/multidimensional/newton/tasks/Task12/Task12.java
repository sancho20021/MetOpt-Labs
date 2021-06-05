package methods.multidimensional.newton.tasks.Task12;

import methods.multidimensional.newton.methods.*;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Test;

public class Task12 {
    final public static MinimizationTester.MinimizationTask f1 = new MinimizationTester.MinimizationTask(
            new AnalyticFunction(2, "x_{0}^2 + x_{1}^2 - 1.2 * x_{0} * x_{1}"),
            new Vector(0, 0),
            new Vector(4, 1)
    );
    final public static MinimizationTester.MinimizationTask f2 = new MinimizationTester.MinimizationTask(
            new AnalyticFunction(2, "100 * (x_{1} - x_{0}^2)^2 + (1 - x_{0})^2"),
            new Vector(1, 1),
            new Vector(-1.2, 1)
    );

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
        System.out.println("Testing " + methodName);
        tester.test(f1, delta);
        tester.test(f2, delta);
    }

    private static void testNewton(final AnalyticMinimizer.AnalyticMinimizerCons cons, final double eps, final String methodName) {
        test(cons, eps, eps, methodName);
    }

    @Test
    public void testFastestDescent() {
        test(FastestDescentV2::new, 1e-2, 1e-1, "Наискорейший спуск");
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
