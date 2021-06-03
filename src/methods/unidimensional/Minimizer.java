package methods.unidimensional;

import java.util.function.Function;

public abstract class Minimizer {
    protected final Function<Double, Double> fun;
    protected final double a, b, eps;
    private int iterationsCount;

    public Minimizer(final Function<Double, Double> fun, final double a, final double b, final double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.fun = fun;
        this.a = a;
        this.b = b;
        this.eps = eps;
        iterationsCount = 0;
    }

    public abstract boolean hasNext();

    protected abstract Section nextIteration();

    public Section next() {
        if (!hasNext()) {
            return null;
        }
        iterationsCount++;
        return nextIteration();
    }

    public abstract void restart();

    /**
     * Finds argmin of function
     * @return Argmin of function
     */
    public double findMinimum() {
        restart();
        while (hasNext()) {
            nextIteration();
            iterationsCount++;
        }
        return getCurrentXMin();
    }

    public abstract double getCurrentXMin();

    public Function<Double, Double> getFun() {
        return fun;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public int getIterationsCount() {
        return iterationsCount;
    }

    public void resetIterationsCount() {
        iterationsCount = 0;
    }
}
