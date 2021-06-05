package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import org.junit.Assert;

import java.util.stream.Collectors;

public class MinimizationTester {
    private final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor;
    public final double eps;
    public final static double STANDARD_EPS = 1e-7;
    public final boolean showPoints;
    public final String methodName;

    public MinimizationTester(
            final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor,
            final double eps,
            final boolean showPoints,
            final String methodName
    ) {
        this.minimizerConstructor = minimizerConstructor;
        this.eps = eps;
        this.showPoints = showPoints;
        this.methodName = methodName;
    }

    public MinimizationTester(final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor, final double eps) {
        this(minimizerConstructor, eps, false, "Unnamed method");
    }

    public MinimizationTester(final AnalyticMinimizer.AnalyticMinimizerCons minimizerConstructor) {
        this(minimizerConstructor, STANDARD_EPS);
    }

    public void test(final MinimizationTask task) {
        test(task, eps);
    }

    public void test(final MinimizationTask taskK, final double delta) {
        final var task = new MinimizationTask(taskK.function, taskK.expectedAns, taskK.startX, eps);
        System.out.println(task);
        final AnalyticMinimizer minimizer = minimizerConstructor.create(task.function, task.startX, task.eps);
        final Vector actualAns = minimizer.findMinimum().orElseThrow();
        final var points = minimizer.points().collect(Collectors.toList());
        System.out.println("Actual ans: " + points.get(points.size() - 1));
        System.out.println("Iterations: " + (points.size() - 1));

        if (showPoints) {
            System.out.println("Points:");
            final var gnuPoints = NewtonMethodTest.getGnuplottablePoints(points.toArray(Vector[]::new));
            System.out.println(gnuPoints);
//            points.forEach(System.out::println);
        }
        Assert.assertEquals(0, actualAns.subtract(task.expectedAns).getEuclideanNorm(), delta);
        System.out.println("OK\n");
    }

    public static class MinimizationTask {
        public final AnalyticFunction function;
        public final Vector expectedAns;
        public final Vector startX;
        public final double eps;

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
