package methods.multidimensional;

public abstract class MultiMinimizer {
    protected final int MAX_ITERATIONS = 100500;

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

    public Vector findMinimum() {
        restart();
        for (int i = 0; i < MAX_ITERATIONS && hasNext(); i++) {
            nextIteration();
        }
        return getCurrentXMin();
    }

    public abstract Vector getCurrentXMin();

    public QuadraticFunction getFun() {
        return fun;
    }

}
