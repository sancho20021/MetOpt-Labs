package methods.multidimensional.newton.methods;

import methods.multidimensional.newton.lssolvers.Gauss;
import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class NewtonMethod extends NewtonMinimizer {
    private Vector x;
    private Vector s;

    public NewtonMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
        restart();
    }


    @Override
    public boolean hasNext() {
        return s.getEuclideanNorm() > eps;
    }

    @Override
    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        AdvancedMatrix h = fun.getHessian(x);
        s = new Vector(Gauss.solveOptimized(h, g.multiply(-1).getElementsArrayCopy()));
        x = x.add(s);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }
}
