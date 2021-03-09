package ru.irmo.metopt.metopt.domain.methods;

import java.util.function.Function;

public abstract class Minimizer {
    protected final Function<Double, Double> fun;
    protected final double a, b, eps;

    public Minimizer(final Function<Double, Double> fun, final double a, final double b, final double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.fun = fun;
        this.a = a;
        this.b = b;
        this.eps = eps;
    }

    public abstract boolean hasNext();

    protected abstract Section nextIteration();

    public Section next() {
        if (!hasNext()) {
            return null;
        }
        return nextIteration();
    }

    public abstract void restart();

    public double findMinimum() {
        restart();
        while (hasNext()) {
            nextIteration();
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
}
