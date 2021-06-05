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
    public AdvancedMatrix getG(AdvancedMatrix prevG, Vector dxPrev, Vector dw) {
        final Vector v = prevG.multiply(dw);
        return prevG
                .subtract(dxPrev.multiply(dxPrev).multiply(1.0 / dw.scalarProduct(dxPrev)))
                .subtract(v.multiply(v).multiply(1.0 / v.scalarProduct(dw)));
    }
}
