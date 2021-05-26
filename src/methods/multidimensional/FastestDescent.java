package methods.multidimensional;

import methods.unidimensional.FibonacciMinimizer;
import methods.unidimensional.Minimizer;
import methods.unidimensional.ParabolicMinimizer;
import models.Vector;
import models.functions.QuadraticFunction;

import java.util.function.Function;

public class FastestDescent extends MultiMinimizer {
    private Vector x;
    private final double maxA;
    private final Class<? extends Minimizer> uniMinimizer;

    private static double getMaxA(final QuadraticFunction f) {
        return 2.0 / f.getMinEigenValueAbs() * Math.max(1, f.getB().getEuclideanNorm());
    }

    public FastestDescent(final QuadraticFunction fun, final Vector startX, final double eps, final double maxA) {
        this(fun, startX, eps, maxA, /*ParabolicMinimizer.class*/ FibonacciMinimizer.class);
    }

    public FastestDescent(final QuadraticFunction fun, final Vector startX, final double eps) {
        this(fun, startX, eps, getMaxA(fun));
    }

    public FastestDescent(final QuadraticFunction fun, final Vector startX, final double eps, final Class<? extends Minimizer> uniMinimizer) {
        this(fun, startX, eps, getMaxA(fun), uniMinimizer);
    }

    public FastestDescent(final QuadraticFunction fun, final Vector startX, final double eps, final double maxA, final Class<? extends Minimizer> uniMinimizer) {
        super(fun, startX, eps);
        this.maxA = maxA;
        this.uniMinimizer = uniMinimizer;
        restart();
    }

    @Override
    public boolean hasNext() {
        return fun.getGradient(x).getEuclideanNorm() >= eps;
    }

    @Override
    protected Vector nextIteration() {
        return x = x.add(fun.getGradient(x).multiply(-oneDimMin(x)));
    }

    @Override
    public void restart() {
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

    private double oneDimMin(final Vector x0) {
        final Function<Double, Double> uniFunction = a -> fun.get(x0.add(fun.getGradient(x0).multiply(-a)));
        try {
            final var uniMinInstance = uniMinimizer
                    .getConstructor(Function.class, double.class, double.class, double.class)
                    .newInstance(uniFunction, 0.0, maxA, eps);
            return uniMinInstance.findMinimum();
        } catch (final Exception e) {
            System.err.println("Error occurred while trying to use one dimension minimizer");
            throw new IllegalStateException("See log, message: " + e.getMessage(), e);
        }
    }
}
