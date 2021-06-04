package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithDD extends NewtonWithFind {

    public NewtonWithDD(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    protected Vector getDirection(Vector lsSolution, Vector gradient) {
        return lsSolution.scalarProduct(gradient) < 0 ? lsSolution : gradient.multiply(-1);
    }

    @Override
    public void restart() {
        x = startX;
        final Vector d = fun.getGradient(x).multiply(-1);
        double r = 0;
        // :TODO: r = argmin alpha (f(x + alpha * d))
        // :DONE: begin
        r = getAlpha(d);
        // :DONE: end
        dx = d.multiply(r);
        x = x.add(dx);
    }
}
