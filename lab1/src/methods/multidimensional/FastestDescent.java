package methods.multidimensional;

import methods.unidimensional.FibonacciMinimizer;

public class FastestDescent extends MultiMinimizer {
    private Vector x;
    private final double maxA;

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps, double maxA) {
        super(fun, startX, eps);
        this.maxA = maxA;
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

    private double oneDimMin(Vector x0) {
        return new FibonacciMinimizer(a -> fun.get(x0.add(fun.getGradient(x0).multiply(-a))), 0.0, maxA, eps)
                .findMinimum();
    }
}
