package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;
import models.matrices.DiagonalMatrix;

import java.util.stream.DoubleStream;

/**
 * @author Yaroslav Ilin
 */
public abstract class QuasiNewtonianMinimizer extends NewtonMinimizer {
    protected Vector w;
    protected AdvancedMatrix g;

    protected QuasiNewtonianMinimizer(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
        restart();
    }

    @Override
    public boolean hasNext() {
        return dx == null || dx.getEuclideanNorm() >= eps;
    }

    @Override
    protected Vector nextIteration() {
        final Vector prevW = w;
        w = fun.getGradient(x).multiply(-1);
        final Vector dw = w.subtract(prevW);
        g = getG(g, dx, dw);
        final Vector p = g.multiply(w);
        final double alpha = findAlpha(p);
        dx = p.multiply(alpha);
        x = x.add(dx);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        w = fun.getGradient(x).multiply(-1);
        g = new DiagonalMatrix(DoubleStream.generate(() -> 1).limit(startX.size()).toArray());
        final double alpha = findAlpha(w);
        dx = w.multiply(alpha);
        x = x.add(dx);
    }

    private double findAlpha(final Vector vector) {
        return getArgMin(vector, -10, 10);
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

    public abstract AdvancedMatrix getG(final AdvancedMatrix prevG, final Vector dxPrev, final Vector dw);

}
