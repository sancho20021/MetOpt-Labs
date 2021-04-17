package methods.multidimensional;

import models.Vector;
import models.functions.QuadraticFunction;

/**
 * @author Yaroslav Ilin
 */

public class ConjugateGradientsMinimizer extends MultiMinimizer {
    private Vector x;
    private Vector gradient;
    private Vector p;
    private final double eps;
    private int iterationCount;

    public ConjugateGradientsMinimizer(QuadraticFunction fun, Vector x, double eps) {
        super(fun, x, eps);
        this.x = x;
        this.gradient = fun.getGradient(x);
        this.eps = eps;
        p = gradient.multiply(-1.0);
        this.iterationCount = 0;
    }

    @Override
    public boolean hasNext() {
        return !(gradient.getEuclideanNorm() < eps);
    }

    public Vector nextIteration() {
        if (iterationCount == fun.getA().size()) {
            p = gradient.multiply(-1.0);
            iterationCount = 0;
        }
        double gradientNorm = gradient.getEuclideanNorm();
        Vector Ap = fun.getA().multiply(p);
        double alpha = (gradientNorm * gradientNorm) / (Ap.scalarProduct(p));
        x = x.add(p.multiply(alpha));
        double lastGradientNorm = gradient.getEuclideanNorm();
        gradient = gradient.add(Ap.multiply(alpha));
        double betta = (lastGradientNorm * lastGradientNorm) / (gradientNorm * gradientNorm);
        p = gradient.multiply(-1.0).add(p.multiply(betta));
        iterationCount++;
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        gradient = fun.getGradient(x);
        iterationCount = 0;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }
}
