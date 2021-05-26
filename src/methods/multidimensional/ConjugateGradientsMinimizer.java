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

    @Override
    public Vector nextIteration() {
        if (iterationCount == fun.getA().size()) {
            p = gradient.multiply(-1.0);
            iterationCount = 0;
        }

        // finding x_{k+1} = x_k + alpha_k * p_k
        final Vector Ap = fun.getA().multiply(p);  // A * p_k
        double alpha = -gradient.scalarProduct(p) / Ap.scalarProduct(p);  // alpha_k
        x = x.add(p.multiply(alpha));  // found x_{k+1}

        // finding b_k
        gradient = fun.getGradient(x);  // update gradient(x_{k+1})
        double beta = fun.getA().multiply(gradient).scalarProduct(p) / Ap.scalarProduct(p);  // b_k = (A grad(x_k+1), p_k) / (Ap_k, p_k)

        // finding p_{k + 1}
        p = gradient.multiply(-1.0).add(p.multiply(beta));  // p_{k+1} = -grad(x_{k+1}) + beta * p_k
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
