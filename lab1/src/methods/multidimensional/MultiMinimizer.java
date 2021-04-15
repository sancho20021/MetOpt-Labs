package methods.multidimensional;

public abstract class MultiMinimizer {
    protected final QuadraticFunction fun;
    protected Vector startX;
    protected final double startAlpha, eps;

    public MultiMinimizer(final QuadraticFunction fun, double startAlpha, Vector startX, double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.fun = fun;
        this.startAlpha = startAlpha;
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
        while (hasNext()) {
            nextIteration();
        }
        return getCurrentXMin();
    }

    public abstract Vector getCurrentXMin();

    public QuadraticFunction getFun() {
        return fun;
    }

}
