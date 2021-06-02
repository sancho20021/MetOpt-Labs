package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.MultiMinimizer;
import models.SquareMatrix;
import models.Vector;
import models.functions.QuadraticFunction;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithFind extends MultiMinimizer {
    private Vector x;
    private Vector d;
    private Vector s;

    protected NewtonWithFind(QuadraticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
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
        // :TODO: Решить СЛАУ (H * d = -g);
        double r = 0;
        // :TODO: r = argmin alpha f(x + alpha * d);
        s = d.multiply(r);
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
