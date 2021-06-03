package methods.multidimensional.newton.methods;

import expression.exceptions.CalculationException;
import methods.multidimensional.newton.lssolvers.Gauss;
import methods.multidimensional.quadratic.Constants;
import methods.unidimensional.FibonacciMinimizer;
import models.Vector;
import models.functions.AnalyticFunction;
import models.functions.QuadraticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithDD extends NewtonMinimizer {

    public NewtonWithDD(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public boolean hasNext() {
        return s.getEuclideanNorm() > eps;
    }

    @Override
    protected Vector nextIteration() {
        final Vector g = fun.getGradient(x);
        final AdvancedMatrix h = fun.getHessian(x);
        // :TODO: Решить СЛАУ (H * s = -g)
        // :DONE: begin
        final var sol = Gauss.solve(h, g.multiply(-1).getElementsArrayCopy());
        if (sol.isEmpty()) {
            throw new CalculationException("No solution to H * s = -g");
        }
        s = new Vector(sol.get());
        // :DONE: end
        final Vector d;
        if (s.scalarProduct(g) < 0) {
            d = s;
        } else {
            d = g.multiply(-1);
        }
        double r = 0;
        // :TODO: Вычислить r = argmin alpha (f(x + alpha * d))
        // :DONE: begin
        r = getArgmin(d);
        // :DONE: end
        s = d.multiply(r);
        x = x.add(s);
        return x;
    }

    @Override
    public void restart() {
        x = startX;
        final Vector d = fun.getGradient(x).multiply(-1);
        double r = 0;
        // :TODO: r = argmin alpha (f(x + alpha * d))
        // :DONE: begin
        r = getArgmin(d);
        // :DONE: end
        s = d.multiply(r);
        x = x.add(s);
    }

    private double getArgmin(final Vector d) {
        return new FibonacciMinimizer(alpha -> fun.get(x.add(d.multiply(alpha))),
                Constants.STANDARD_LOWER_BOUND,
                Constants.STANDARD_UPPER_BOUND,
                Constants.STANDARD_EPS).findMinimum();
    }
}
