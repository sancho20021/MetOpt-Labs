package methods.multidimensional;

/**
 * @author Yaroslav Ilin
 */

public class ConjugateGradientsMinimizer extends MultiMinimizer {
    private Vector x;
    private Vector gradient;
    private Vector p;
    private final double eps;

    public ConjugateGradientsMinimizer(QuadraticFunction fun, Vector x, double eps) {
        super(fun, x, eps);
        this.x = x;
        this.gradient = fun.getGradient(x);
        this.eps = eps;
        p = gradient.multiply(-1.0);
    }

    @Override
    public boolean hasNext() {
        return !(fun.getGradient(x).getEuclideanNorm() < eps);
    }

    public Vector nextIteration() {
        double gradientNorm = gradient.getEuclideanNorm();
        Vector Ap = fun.getA().multiply(p);
        double alpha = (gradientNorm * gradientNorm) / (Ap.scalarProduct(p));
        x = x.add(p.multiply(alpha));
        Vector gradientk = gradient;
        gradient = gradient.add(Ap.multiply(alpha));
        double gradientkNorm = gradient.getEuclideanNorm();
        double betta = (gradientkNorm * gradientkNorm) / (gradientNorm * gradientNorm);
        p = gradientk.multiply(-1.0).add(p.multiply(betta));
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        gradient = fun.getGradient(x);
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }
}
