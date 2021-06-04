package methods.multidimensional.newton.methods;

import methods.unidimensional.FibonacciMinimizer;
import models.Vector;
import models.functions.AnalyticFunction;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class NewtonMinimizer {
    public static final int MAX_ITERATIONS = 10_000_000;

    protected final AnalyticFunction fun;
    protected Vector startX;
    protected final double eps;
    protected Vector x;
    protected Vector s;

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

    public interface NewtonMinimizerCons {
        NewtonMinimizer create(AnalyticFunction function, Vector startX, double eps);
    }

    protected double getArgMin(final Vector vector, final double lo, final double hi) {
        return new FibonacciMinimizer(alpha -> fun.get(x.add(vector.multiply(alpha))), lo, hi, eps).findMinimum();
    }
}
