package methods.multidimensional.newton.methods;

import methods.multidimensional.quadratic.MultiMinimizer;
import models.Vector;
import models.functions.QuadraticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithDD extends MultiMinimizer {
    private Vector x;
    private Vector s;

    protected NewtonWithDD(QuadraticFunction fun, Vector startX, double eps) {
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
        // :TODO: Решить СЛАУ (H * s = -g)
        Vector d;
        if (s.scalarProduct(g) < 0) { // :CHECK: Я не уверен что s^T * g = скалярному произведению, но мне кажется это правда
            d = s;
        } else {
            d = g.multiply(-1);
        }
        double r = 0;
        // :TODO: Вычислить r = argmin alpha (f(x + alpha * d))
        s = d.multiply(r);
        x = x.add(s);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        Vector d = fun.getGradient(x).multiply(-1);
        double r = 0;
        // :TODO: r = argmin alpha (f(x + alpha * d))
        s = d.multiply(r);
        x = x.add(s);
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }
}
