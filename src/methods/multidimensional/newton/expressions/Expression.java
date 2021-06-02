package methods.multidimensional.newton.expressions;

import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression extends ToMiniString {
    double evaluate(Map<Integer, Double> x);

    Expression differentiate(final int x);
}
