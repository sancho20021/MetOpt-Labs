package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;
import models.matrices.DiagonalMatrix;
import models.matrices.FullMatrix;

import java.util.stream.DoubleStream;

/**
 * @author Yaroslav Ilin
 */
public class MarqurdtWithCholesky extends NewtonMinimizer {
    private double fx;
    private final double betta;
    private Vector p;
    private final AdvancedMatrix I;

    public MarqurdtWithCholesky(AnalyticFunction fun, Vector startX, double betta, double eps) {
        super(fun, startX, eps);
        this.betta = betta;
        I = new DiagonalMatrix(DoubleStream.generate(() -> 1).limit(startX.size()).toArray());
        restart();
    }

    @Override
    public boolean hasNext() {
        return p == null || p.getEuclideanNorm() > eps;
    }

    @Override
    // :TODO: Check correct, I don't know right it or not
    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        AdvancedMatrix h = fun.getHessian(x);
        double tau = 0;
        Vector y;
        double fy;
        while (true) {
            while (true) {
                if (cholesky(h.add(I.multiply(tau))) != null) {
                    break;
                } else {
                    tau = Math.max(1, 2 * tau);
                }
            }
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
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        fx = fun.get(x);
        p = null;
    }

    // :TODO: Check correct, it's not at all a fact that it's right
    private static AdvancedMatrix cholesky(AdvancedMatrix a) {
        final int m = a.size();
        double epsilon = 0.000001; // Small extra noise value
        double[][] l = new double[m][m];
        try {
            for (int i = 0; i < m; i++) {
                for (int k = 0; k < (i + 1); k++) {
                    double sum = 0;
                    for (int j = 0; j < k; j++) {
                        sum += l[i][j] * l[k][j];
                    }
                    l[i][k] = (i == k) ? Math.sqrt(a.get(i, i) + epsilon - sum) : // Add noise to diagonal values
                            (1.0 / l[k][k] * (a.get(i, k) - sum));
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
        return new FullMatrix(l);
    }
}
