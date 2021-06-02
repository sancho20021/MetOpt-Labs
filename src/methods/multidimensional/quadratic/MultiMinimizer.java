package methods.multidimensional.quadratic;

import models.Vector;
import models.functions.QuadraticFunction;

import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public abstract class MultiMinimizer {
    public static final int MAX_ITERATIONS = 10_000_000;

    protected final QuadraticFunction fun;
    protected Vector startX;
    protected final double eps;

    protected MultiMinimizer(final QuadraticFunction fun, Vector startX, double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.fun = fun;
        this.startX = startX;
        this.eps = eps;
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

    public Vector findMinimum() throws TimeoutException {
        restart();
        int i;
        for (i = 0; i < MAX_ITERATIONS && hasNext(); i++) {
            nextIteration();
        }
        if (i == MAX_ITERATIONS) {
            throw new TimeoutException("Failed time out");
        }
        return getCurrentXMin();
    }

    public abstract Vector getCurrentXMin();

    public QuadraticFunction getFun() {
        return fun;
    }

    public Stream<Vector> points() {
        restart();
        return Stream.concat(Stream.of(startX), Stream.iterate(next(), Objects::nonNull, prev -> next()));
    }
}
