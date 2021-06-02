package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.MultiMinimizer;
import models.SquareMatrix;
import models.Vector;
import models.functions.QuadraticFunction;

/**
 * @author Yaroslav Ilin
 */
public class NewtonMethod extends MultiMinimizer {
    private Vector x;
    private Vector s;

    public NewtonMethod(Vector startX, QuadraticFunction f, double eps) {
        super(f, startX, eps);
        this.x = startX;
    }


    @Override
    public boolean hasNext() {
        return s.getEuclideanNorm() > eps;
    }

    @Override
    protected Vector nextIteration() {
        Vector g = fun.getGradient(x);
        SquareMatrix h = fun.getHessian(x);
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
