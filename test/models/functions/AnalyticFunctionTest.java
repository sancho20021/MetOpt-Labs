package models.functions;

import methods.multidimensional.newton.expressions.Expression;
import methods.multidimensional.newton.parser.ExpressionParser;
import models.Vector;
import org.junit.Assert;
import org.junit.Test;
import utils.generators.VectorGenerators;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class AnalyticFunctionTest {

    @Test
    public void test01() {
        final Expression exp = new ExpressionParser().parse("x_{0}^2 + x_{1}^2");
        final AnalyticFunction f = new AnalyticFunction(2, exp);
        System.out.println(f.getArity());
        System.out.println(f.toString());
        System.out.println(Arrays.toString(f.getGradient()));
        System.out.println(f.getGradient(new Vector(1, 1)));
        System.out.println();
        System.out.println(Arrays.deepToString(f.getHessian()));
        System.out.println(f.getHessian(new Vector(1, 1)));
    }

    @Test
    public void test02() {
        final String fourthPower = "(x_{0} - 1)^4 + (x_{1} - 2)^4";
        final Function<Vector, Double> expected = vector -> Math.pow(vector.get(0) - 1, 4) + Math.pow(vector.get(1) - 2, 4);
        final AnalyticFunction actual = new AnalyticFunction(2, fourthPower);
        final Expression[] actualGradient = actual.getGradient();
        final Function<Vector, Double>[] expectedGradient = new Function[2];
        expectedGradient[0] = vector -> 4 * Math.pow(vector.get(0) - 1, 3);
        expectedGradient[1] = vector -> 4 * Math.pow(vector.get(1) - 2, 3);
        for (int i = 0; i < actualGradient.length; i++) {
            test(2, actualGradient[i], expectedGradient[i]);
        }

    }

    public static void test(final AnalyticFunction actual, final Function<Vector, Double> expected) {
        test(actual.getArity(), actual.getExpression(), expected);
    }

    public static void test(final int arity, final Expression actual, final Function<Vector, Double> expected) {
        final int samples = 100;
        Stream.generate(() -> VectorGenerators.generateIntegerVector(arity, -10, 10)).limit(samples).forEach(data -> {
            final Vector vector = new Vector(data);
            try {
                Assert.assertEquals(actual.evaluate(vector), expected.apply(vector), 1e-15);
            } catch (final AssertionError e) {
                System.out.println(vector);
                throw e;
            }
        });
    }
}