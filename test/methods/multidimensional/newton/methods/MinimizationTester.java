package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.tasks.Task12;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class MinimizationTester {
    private final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor;
    private final List<MinimizationTask> tasks = new ArrayList<>(List.of(
            new MinimizationTask(new AnalyticFunction(1, "x_{0}^2"), new Vector(0), new Vector(10)),
            Task12.f1,
            Task12.f2
    ));
    public final double eps;
    public final static double STANDARD_EPS = 1e-7;

    public MinimizationTester(final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor, final double eps) {
        this.minimizerConstructor = minimizerConstructor;
        this.eps = eps;
    }

    public MinimizationTester(final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor) {
        this(minimizerConstructor, STANDARD_EPS);
    }


    public void addTask(final MinimizationTask task) {
        tasks.add(new MinimizationTask(task.function, task.expectedAns, task.startX, eps));
    }

    public void addTask(final AnalyticFunction function, final Vector ans, final Vector startX, final double eps) {
        addTask(new MinimizationTask(function, ans, startX, eps));
    }

    public void addTask(final AnalyticFunction function, final Vector ans, final Vector startX) {
        addTask(function, ans, startX, eps);
    }

    public void testAll() {
        testAll(eps);
    }

    public void testAll(final double delta) {
        for (final var task : tasks) {
            test(task, delta);
        }
    }

    public void test(final MinimizationTask task, final double delta) {
        final AnalyticMinimizer minimizer = minimizerConstructor.create(task.function, task.startX, task.eps);
        final Vector actualAns = minimizer.findMinimum().orElseThrow();
        Assert.assertEquals(0, actualAns.subtract(task.expectedAns).getEuclideanNorm(), delta);
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
