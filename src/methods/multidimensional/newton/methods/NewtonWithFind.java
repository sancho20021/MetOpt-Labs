package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.MultiMinimizer;
import models.Vector;
import models.functions.QuadraticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithFind extends MultiMinimizer {
    private Vector x;
    private Vector s;

    protected NewtonWithFind(QuadraticFunction fun, Vector startX, double eps) {
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
        Vector d = new Vector();
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
