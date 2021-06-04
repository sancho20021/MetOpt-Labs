package methods.multidimensional.newton.tasks;

import methods.multidimensional.newton.methods.AnalyticMinimizer;
import methods.multidimensional.newton.methods.FastestDescentV2;
import methods.multidimensional.newton.methods.MinimizationTester;
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

    @Test
    public void testFastestDescent() {
        final var tester = new MinimizationTester(FastestDescentV2::new, 1e-8);
        tester.addTask(f1);
        tester.addTask(f2);
        tester.testAll(1e-6);
    }
}
