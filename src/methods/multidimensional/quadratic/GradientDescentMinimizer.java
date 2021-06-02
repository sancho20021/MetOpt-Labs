package methods.multidimensional.quadratic;

import models.Vector;
import models.functions.QuadraticFunction;

public class GradientDescentMinimizer extends MultiMinimizer {
    private final double startAlpha;
    private double alpha;
    private Vector x;

    public GradientDescentMinimizer(final QuadraticFunction fun,
                                    final double startAlpha,
                                    final Vector startX, final double eps) {
        super(fun, startX, eps);
        this.startAlpha = startAlpha;
        restart();
    }

    public GradientDescentMinimizer(final QuadraticFunction fun, final Vector startX, final double eps) {
        this(fun, 2 / (fun.getMinEigenValueAbs() + fun.getMaxEigenValueAbs()), startX, eps);
    }

    public boolean hasNext() {
        return fun.getGradient(x).getEuclideanNorm() >= eps;
    }

    public Vector nextIteration() {
        final Vector next = x.add(fun.getGradient(x).multiply(-alpha));
        if (fun.get(next) < fun.get(x)) {
            return x = next;
        }
        alpha /= 2;
        return x;
    }

    @Override
    public void restart() {
        alpha = startAlpha;
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

}
