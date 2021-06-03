package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.lssolvers.Gauss;
import methods.unidimensional.FibonacciMinimizer;
import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithFind extends NewtonMinimizer {

    public NewtonWithFind(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public boolean hasNext() {
        return s == null || s.getEuclideanNorm() > eps;
    }

    @Override
    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        AdvancedMatrix h = fun.getHessian(x);
        Vector d = new Vector(Gauss.solveOptimized(h, g.multiply(-1).getElementsArrayCopy()));
        double r = new FibonacciMinimizer(alpha -> fun.get(x.add(d.multiply(alpha))), 0, 1000, eps).findMinimum();
        s = d.multiply(r);
        x = x.add(s);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
    }
}
