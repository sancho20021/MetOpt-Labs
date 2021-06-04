package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.lssolvers.Gauss;
import methods.unidimensional.FibonacciMinimizer;
import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

public abstract class NewtonCommonMethod extends NewtonMinimizer {
    public NewtonCommonMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public boolean hasNext() {
        return s == null || s.getEuclideanNorm() > eps;
    }

    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        AdvancedMatrix h = fun.getHessian(x);
        // :NOTE: Gauss(_, _, eps) -> Gauss(_, _, 0)
        final Vector lsSolution = new Vector(Gauss.solveOptimized(h, g.multiply(-1).getElementsArrayCopy(), 0).orElseThrow());
        Vector direction = getDirection(lsSolution, g);
        double alpha = getAlpha(direction);
        s = direction.multiply(alpha);
        x = x.add(s);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        s = null;
    }

    protected abstract Vector getDirection(final Vector lsSolution, final Vector gradient);

    protected abstract double getAlpha(final Vector direction);
}
