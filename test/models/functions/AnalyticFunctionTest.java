package models.functions;

import methods.multidimensional.newton.expressions.Expression;
import methods.multidimensional.newton.parser.ExpressionParser;
import models.Vector;
import org.junit.Test;

import java.util.Arrays;

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
}