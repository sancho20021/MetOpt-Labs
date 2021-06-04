package methods.multidimensional.newton.methods;

import models.Vector;
import models.functions.AnalyticFunction;

import java.util.Optional;
import java.util.stream.Stream;

public interface AnalyticMinimizer {
    boolean hasNext();

    Vector next();

    void restart();

    Optional<Vector> findMinimum();

    Vector getCurrentXMin();

    AnalyticFunction getFun();

    Stream<Vector> points();

    interface AnalyticMinimizerCons {
        AnalyticMinimizer create(AnalyticFunction function, Vector startX, double eps);
    }
}
