package methods.multidimensional.newton.methods;

import methods.unidimensional.GoldenMinimizer;
import models.Vector;
import models.functions.AnalyticFunction;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class NewtonMinimizer implements AnalyticMinimizer {
    public static final int MAX_ITERATIONS = 10_000_000;

    protected final AnalyticFunction fun;
    protected Vector startX;
    protected final double eps;
    protected Vector x;
    protected Vector dx;

    public NewtonMinimizer(final AnalyticFunction fun, Vector startX, double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.fun = fun;
        this.startX = startX;
        this.eps = eps;
        restart();
    }

    public abstract boolean hasNext();

    protected abstract Vector nextIteration();

    public Vector next() {
        if (!hasNext()) {
            return null;
        }
        return nextIteration();
    }

    public abstract void restart();

    public Optional<Vector> findMinimum() {
        restart();
        int i;
        for (i = 0; i < MAX_ITERATIONS && hasNext(); i++) {
            nextIteration();
        }
        return i == MAX_ITERATIONS ? Optional.empty() : Optional.of(getCurrentXMin());
    }

    public Vector getCurrentXMin() {
        return x;
    }

    public AnalyticFunction getFun() {
        return fun;
    }

    public Stream<Vector> points() {
        restart();
        return Stream.concat(Stream.of(startX), Stream.iterate(next(), Objects::nonNull, prev -> next()));
    }

    protected double getArgMin(final Vector vector, final double lo, final double hi) {
        final double unEps = Math.min(eps, 1e-9);
        final Function<Double, Double> uniFunction = alpha -> fun.get(x.add(vector.multiply(alpha)));
        double alpha = hi / 10000;
        while (alpha < hi) {
            final var uniMinimizer = new GoldenMinimizer(uniFunction, lo, alpha, unEps);
            final double minimum = uniMinimizer.findMinimum();
            if (alpha - minimum <= unEps) {
                alpha *= 2;
            } else {
                return minimum;
            }
        }
        return alpha;
//        return new GoldenMinimizer(alpha -> fun.get(x.add(vector.multiply(alpha))), lo, hi, Math.min(1e-9, eps)).findMinimum();
    }
}
