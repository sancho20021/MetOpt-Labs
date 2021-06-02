package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.MultiMinimizer;
import models.Vector;
import models.functions.QuadraticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class NewtonMethod extends MultiMinimizer {
    private Vector x;
    private Vector s;

    public NewtonMethod(Vector startX, QuadraticFunction f, double eps) {
        super(f, startX, eps);
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
        // :TODO: Решить СЛАУ (H * s = -g)
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
