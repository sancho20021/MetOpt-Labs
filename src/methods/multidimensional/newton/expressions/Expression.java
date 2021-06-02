package methods.multidimensional.newton.expressions;

import models.Vector;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression extends ToMiniString {
    double evaluate(Map<Integer, Double> x);

    Expression differentiate(final int x);

    default double evaluate(final Vector x) {
        return evaluate(IntStream.range(0, x.size()).boxed().collect(Collectors.toMap(i -> i, x::get)));
    }
}
