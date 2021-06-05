package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;

/**
 * @author Yaroslav Ilin
 */
public class PavelMethod extends QuasiNewtonianMinimizer {
    public PavelMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public AdvancedMatrix getG(AdvancedMatrix prevG, Vector dxPrev, Vector dw) {
        final Vector dy = dxPrev.add(prevG.multiply(dw));
        return prevG.subtract(dy.multiply(dy).multiply(1.0 / dw.scalarProduct(dy)));
    }
}
