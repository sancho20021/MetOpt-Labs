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
}
