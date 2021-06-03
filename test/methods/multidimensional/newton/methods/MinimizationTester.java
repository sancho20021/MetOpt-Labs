package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class MinimizationTester {
    private final NewtonMinimizer.NewtonMinimizerCons minimizerConstructor;
    private final List<MinimizationTask> tasks = new ArrayList<>(List.of(
            new MinimizationTask(new AnalyticFunction(1, "x_{0}^2"), new Vector(0), new Vector(10))
    ));
    public final static double STANDARD_EPS = 1e-7;

    public MinimizationTester(final NewtonMinimizer.NewtonMinimizerCons minimizerConstructor) {
        this.minimizerConstructor = minimizerConstructor;
    }

    public void addTask(final AnalyticFunction function, final Vector ans, final Vector startX, final double eps) {
        tasks.add(new MinimizationTask(function, ans, startX, eps));
    }

    public void addTask(final AnalyticFunction function, final Vector ans, final Vector startX) {
        addTask(function, ans, startX, STANDARD_EPS);
    }

    public void testAll() {
        for (final var task : tasks) {
            test(task);
        }
    }

    public void test(final MinimizationTask task) {
        final NewtonMinimizer minimizer = minimizerConstructor.create(task.function, task.startX, task.eps);
        final Vector actualAns = minimizer.findMinimum().orElseThrow();
        Assert.assertEquals(0, actualAns.subtract(task.expectedAns).getEuclideanNorm(), task.eps);
    }

    public static class MinimizationTask {
        final AnalyticFunction function;
        final Vector expectedAns;
        final Vector startX;
        final double eps;

        public MinimizationTask(final AnalyticFunction function, final Vector ans, final Vector startX, final double eps) {
            this.function = function;
            this.expectedAns = ans;
            this.startX = startX;
            this.eps = eps;
        }

        public MinimizationTask(final AnalyticFunction function, final Vector ans, final Vector startX) {
            this(function, ans, startX, STANDARD_EPS);
        }
    }
}
