package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class Marquardt extends NewtonMinimizer {
    private final double startTau;
    private double tau0;
    private double tau;
    private double fx;
    private final double betta;
    private Vector p;

    public Marquardt(AnalyticFunction fun, Vector startX, double startTau, double betta, double eps) {
        super(fun, startX, eps);
        this.startTau = startTau;
        this.betta = betta;
        restart();
    }

    @Override
    public boolean hasNext() {
        return p == null || p.getEuclideanNorm() > eps;
    }

    @Override
    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        AdvancedMatrix h = fun.getHessian(x);
        tau = tau0;
        Vector y;
        double fy;
        while (true) {
            p = new Vector(0, 0);
            // :TODO: СЛАУ (H(x) + tau * I) * p = -g
            y = x.add(p);
            fy = fun.get(y);
            if (fy >= fx) {
                tau = tau / betta;
            } else {
                break;
            }
        }
        x = y;
        fx = fy;
        tau0 = tau0 * betta;
        return x;
    }

    @Override
    public void restart() {
        tau0 = startTau;
        x = startX;
        fx = fun.get(x);
        p = null;
    }
}
