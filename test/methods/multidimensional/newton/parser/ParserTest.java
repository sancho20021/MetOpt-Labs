package methods.multidimensional.newton.parser;

import methods.multidimensional.newton.expressions.Add;
import methods.multidimensional.newton.expressions.Const;
import methods.multidimensional.newton.expressions.Expression;
import methods.multidimensional.newton.expressions.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ParserTest {
    @Test
    public void test01_constant() {
        final Expression c = new ExpressionParser().parse("5");
        Assert.assertEquals(c, new Const(5));
    }

    @Test
    public void test02_variables() {
        final Expression ab = new ExpressionParser().parse("x_{0} + x_{1}");
        Assert.assertEquals(ab, new Add(new Variable(0), new Variable(1)));
    }

    @Test
    public void test03() {
        final String f = "x_{0}*x_{0} + x_{1}*x_{1} - 1.2*x_{0}*x_{1}";
        System.out.println(new ExpressionParser().parse(f).toMiniString());
    }

    @Test
    public void test04() {
        final String f = "1^2";
        System.out.println(new ExpressionParser().parse(f).toMiniString());
    }

    @Test
    public void test05() {
        final String f = "x_{1}^2 + x_{2}^2 - 1.2*x_{1}*x_{2}";
        final Expression expression = new ExpressionParser().parse(f);
        System.out.println(expression.toMiniString());
        final Expression diffedBy1 = expression.differentiate(1);
        final Expression diffedBy2 = expression.differentiate(2);
        System.out.println("dx1: " + diffedBy1.toMiniString());
        System.out.println("dx2: " + diffedBy2.toMiniString());
        System.out.println(diffedBy1.evaluate(Map.of(1, 3.0, 2, 2.0)));
    }

    @Test
    public void test06_ln() {
        final String f = "ln x_{1} * 2";
        System.out.println(new ExpressionParser().parse(f).toMiniString());
    }
}