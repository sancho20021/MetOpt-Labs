package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;

/**
 * @author Yaroslav Ilin
 */
public class NewtonMethod extends NewtonCommonMethod {

    public NewtonMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    protected Vector getDirection(Vector lsSolution, Vector gradient) {
        return lsSolution;
    }

    @Override
    protected double getAlpha(Vector direction) {
        return 1;
    }
}
