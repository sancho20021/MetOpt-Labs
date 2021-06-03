package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;
import models.matrices.AdvancedMatrix;
import models.matrices.DiagonalMatrix;

import java.util.stream.DoubleStream;

/**
 * @author Yaroslav Ilin
 */
public class PowellMethod extends QuasiNewtonianMinimizer {

    protected PowellMethod(AnalyticFunction fun, Vector startX, double eps) {
        super(fun, startX, eps);
    }

    @Override
    public AdvancedMatrix getG(Vector v, Vector p, Vector s) {
        Vector y = s.add(v);
        return g.subtract(
                y.multiply(y)
                        .multiply(
                                1 / p.scalarProduct(y)
                        )
        ); // :CHECK: Check correct
    }
}
