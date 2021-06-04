package methods.multidimensional.newton.tasks;

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

    private void test(final AnalyticMinimizer.AnalyticMinimizerCons cons, final double eps, final double delta, final String methodName) {
        final var tester = new MinimizationTester(cons, eps, true, false, methodName);
        tester.addTask(f1);
        tester.addTask(f2);
        tester.testAll(delta);
    }

    private void testNewton(final AnalyticMinimizer.AnalyticMinimizerCons cons, final double eps, final String methodName) {
        test(cons, eps, eps, methodName);
    }

    @Test
    public void testFastestDescent() {
        test(FastestDescentV2::new, 1e-7, 1e-6, "Наискорейший спуск");
    }

    @Test
    public void testClassicNewton() {
        testNewton(NewtonMethod::new, 1e-6, "Метод Ньютона");
    }

    @Test
    public void testNewtonWithFind() {
        testNewton(NewtonWithFind::new, 1e-6, "Ньютон с одномерной минимизацией");
    }

    @Test
    public void testNewtonWithDD() {
        testNewton(NewtonWithDD::new, 1e-6, "Ньютон с выбором направления спуска");
    }
}
