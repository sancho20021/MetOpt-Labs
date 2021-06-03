package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;

/**
 * @author Yaroslav Ilin
 */
public class NewtonWithFind extends NewtonMethod {

    public NewtonWithFind(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    protected double getAlpha(Vector direction) {
        return getArgMin(direction, 0, 1000);
    }
}
