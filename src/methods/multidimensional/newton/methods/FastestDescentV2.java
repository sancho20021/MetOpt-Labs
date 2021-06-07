package methods.multidimensional.newton.methods;

import methods.unidimensional.FibonacciMinimizer;
import methods.unidimensional.GoldenMinimizer;
import methods.unidimensional.Minimizer;
import models.Vector;
import models.functions.AnalyticFunction;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class FastestDescentV2 implements AnalyticMinimizer {
    public static final int MAX_ITERATIONS = 10_000_000;

    protected final AnalyticFunction fun;
    protected Vector startX;
    protected final double eps;
    private Vector x;
    private final double maxA;
    private final Class<? extends Minimizer> uniMinimizer;

    public Vector next() {
        if (!hasNext()) {
            return null;
        }
        return nextIteration();
    }

    public Optional<Vector> findMinimum() {
        restart();
        int i;
        for (i = 0; i < MAX_ITERATIONS && hasNext(); i++) {
            nextIteration();
        }
        if (i == MAX_ITERATIONS) {
            return Optional.empty();
        }
        return Optional.of(getCurrentXMin());
    }

    public AnalyticFunction getFun() {
        return fun;
    }

    public Stream<Vector> points() {
        restart();
        return Stream.concat(Stream.of(startX), Stream.iterate(next(), Objects::nonNull, prev -> next()));
    }

    private static double getMaxA(final AnalyticFunction f) {
//        return 2.0 / f.getMinEigenValueAbs() * Math.max(1, f.getB().getEuclideanNorm());
        return 10;
    }

    public FastestDescentV2(final AnalyticFunction fun, final Vector startX, final double eps, final double maxA) {
        this(fun, startX, eps, maxA, /*ParabolicMinimizer.class*/ GoldenMinimizer.class);
    }

    public FastestDescentV2(final AnalyticFunction fun, final Vector startX, final double eps) {
        this(fun, startX, eps, getMaxA(fun));
    }

    public FastestDescentV2(final AnalyticFunction fun, final Vector startX, final double eps, final Class<? extends Minimizer> uniMinimizer) {
        this(fun, startX, eps, getMaxA(fun), uniMinimizer);
    }

    public FastestDescentV2(final AnalyticFunction fun, final Vector startX, final double eps, final double maxA, final Class<? extends Minimizer> uniMinimizer) {
        this.fun = fun;
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.startX = startX;
        this.eps = eps;

        this.maxA = maxA;
        this.uniMinimizer = uniMinimizer;
        restart();
    }

    @Override
    public boolean hasNext() {
        final double gradNorm = fun.getGradient(x).getEuclideanNorm();
        return gradNorm >= eps;
    }

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
        final double unEps = Math.min(eps, 1e-9);
        final Function<Double, Double> uniFunction = a -> fun.get(x0.add(fun.getGradient(x0).multiply(-a)));
        try {
            double alpha = maxA / 10000;
            while (alpha < maxA) {
                final var uniMinimizer = getMinimizer(uniFunction, alpha, unEps);
                final double minimum = uniMinimizer.findMinimum();
                if (alpha - minimum <= unEps) {
                    alpha *= 2;
                } else {
                    return minimum;
                }
            }
            return alpha;
        } catch (final Exception e) {
            System.err.println("Error occurred while trying to use one dimension minimizer");
            throw new IllegalStateException("See log, message: " + e.getMessage(), e);
        }
    }

    private Minimizer getMinimizer(final Function<Double, Double> f, double hi, final double unEps) {
        try {
            return uniMinimizer
                    .getConstructor(Function.class, double.class, double.class, double.class)
                    .newInstance(f, 0, hi, unEps);
        } catch (final Exception e) {
            System.err.println("Error occurred while trying to use one dimension minimizer");
            throw new IllegalStateException("See log, message: " + e.getMessage(), e);
        }
    }
}
