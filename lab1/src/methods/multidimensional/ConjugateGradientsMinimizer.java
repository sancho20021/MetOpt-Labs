package methods.multidimensional;

/**
 * @author Yaroslav Ilin
 */

public class ConjugateGradientsMinimizer {
    private Vector x;
    private Vector gradient;
    private Vector p;
    private final QuadraticFunction f;
    private final double eps;

    public ConjugateGradientsMinimizer(QuadraticFunction f, Vector x, double eps) {
        this.x = x;
        this.f = f;
        this.gradient = f.getGradient(x);
        this.eps = eps;
        p = gradient.multiply(-1.0);
    }

    public double getMin() {
        while (hasNext()) {
            nextIteration();
        }
        return f.get(x);
    }

    private boolean hasNext() {
        return !(f.getGradient(x).getEuclideanNorm() < eps);
    }

    public void nextIteration() {
        double gradientNorm = gradient.getEuclideanNorm();
        Vector Ap = f.getA().multiply(p);
        double alpha = (gradientNorm * gradientNorm) / (Ap.scalarProduct(p));
        Vector xk = x;
        x = x.add(p.multiply(alpha));
        Vector gradientk = gradient;
        gradient = gradient.add(Ap.multiply(alpha));
        double gradientkNorm = gradient.getEuclideanNorm();
        double betta = (gradientkNorm * gradientkNorm) / (gradientNorm * gradientNorm);
        p = gradientk.multiply(-1.0).add(p.multiply(betta));
    }
}
