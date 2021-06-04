package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.lssolvers.Gauss;
import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

public abstract class NewtonCommonMethod extends NewtonMinimizer {
    public NewtonCommonMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public boolean hasNext() {
        return dx == null || dx.getEuclideanNorm() > eps;
    }

    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        AdvancedMatrix h = fun.getHessian(x);
        // :NOTE: Gauss(_, _, eps) -> Gauss(_, _, 0)
        final Vector lsSolution = new Vector(Gauss.solveOptimized(h, g.multiply(-1).getElementsArrayCopy(), 0).orElseThrow());
        Vector direction = getDirection(lsSolution, g);
        double alpha = getAlpha(direction);
        dx = direction.multiply(alpha);
        x = x.add(dx);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        dx = null;
    }

    protected abstract Vector getDirection(final Vector lsSolution, final Vector gradient);

    protected abstract double getAlpha(final Vector direction);
}
