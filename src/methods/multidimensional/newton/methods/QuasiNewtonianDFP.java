package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class QuasiNewtonianDFP extends QuasiNewtonianMinimizer {

    public QuasiNewtonianDFP(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public AdvancedMatrix getG(Vector v, Vector p, Vector s) {
        return g.add(s.multiply(s).multiply(1 / (s.scalarProduct(p)))
        ).subtract(v.multiply(v).multiply(1 / (v.scalarProduct(p)))); // :TODO: Check correct
    }
}
