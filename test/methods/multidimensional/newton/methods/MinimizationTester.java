package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.tasks.Task12;
import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MinimizationTester {
    private final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor;
    private final List<MinimizationTask> tasks = new ArrayList<>(List.of(
            new MinimizationTask(new AnalyticFunction(1, "x_{0}^2"), new Vector(0), new Vector(10))
    ));
    public final double eps;
    public final static double STANDARD_EPS = 1e-7;
    public final boolean showIterationsNumber;
    public final boolean showPoints;
    public final String methodName;

    public MinimizationTester(
            final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor,
            final double eps,
            final boolean showIterationsNumber,
            final boolean showPoints,
            final String methodName
    ) {
        this.minimizerConstructor = minimizerConstructor;
        this.eps = eps;
        this.showIterationsNumber = showIterationsNumber;
        this.showPoints = showPoints;
        this.methodName = methodName;
    }

    public MinimizationTester(final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor, final double eps) {
        this(minimizerConstructor, eps, true, false, "Unnamed method");
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
        System.out.println("Testing " + methodName);
        for (final var task : tasks) {
            test(task, delta);
        }
    }

    public void test(final MinimizationTask task, final double delta) {
        System.out.println(task);
        final AnalyticMinimizer minimizer = minimizerConstructor.create(task.function, task.startX, task.eps);
        final Vector actualAns = minimizer.findMinimum().orElseThrow();
        if (showIterationsNumber) {
            final var points = minimizer.points().collect(Collectors.toList());
            System.out.println("Actual ans: " + points.get(points.size() - 1));
            System.out.println("Iterations: " + (points.size() - 1));
            if (showPoints) {
                System.out.println("Points:");
                points.forEach(System.out::println);
            }
        }
        Assert.assertEquals(0, actualAns.subtract(task.expectedAns).getEuclideanNorm(), delta);
        System.out.println("OK\n");
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

        @Override
        public String toString() {
            return "------Minimization-Task--------\n" +
                    "f(x) = " + function + "\n" +
                    "x0 = " + startX + "\n" +
                    "eps = " + eps + "\n" +
                    "expected ans = " + expectedAns;
        }
    }
}
