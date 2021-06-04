package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class PowellMethod extends QuasiNewtonianMinimizer {

    public PowellMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public AdvancedMatrix getG(Vector gTimesDeltaG, Vector deltaG, Vector dx) {
        Vector y = dx.add(gTimesDeltaG);
        return g.subtract(y.multiply(y).multiply(1 / deltaG.scalarProduct(y))); // :CHECK: Check correct
    }
}
