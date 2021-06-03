package methods.multidimensional.newton.expressions;

import methods.multidimensional.MultidimensionalTester;
import models.functions.QuadraticFunction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Stream;

public class ExpressionTest {
    @Test
    public void test01_add() {
        final Expression a = new Const(4);
        final Expression b = new Const(5);
        final Expression ab = new Add(a, b);
        Assert.assertEquals(9.0, ab.evaluate(Map.of()), 0.0);
        System.out.println(ab.toMiniString());
    }

    @Test
    public void test02_variables() {
        final Expression x1 = new Variable(0);
        final Expression x2 = new Variable(1);
        final Expression ab = new Multiply(x1, x2);
        Assert.assertEquals(20, ab.evaluate(Map.of(0, 2.0, 1, 10.0)), 0.0);
        System.out.println(ab.toMiniString());
    }

    @Test
    public void test03_pow() {
        final Expression x1 = new Variable(1);
        final Expression c2 = new Const(2);
        final Expression x1square = new Pow(x1, c2);
        Assert.assertEquals(16, x1square.evaluate(Map.of(1, 4.0)), 0);
    }

    @Test
    public void test04_ln() {
        final Expression lnx1 = new Ln(new Variable(1));
        Assert.assertEquals(Math.log(50), lnx1.evaluate(Map.of(1, 50.0)), 0);
    }

    @Test
    public void test05_QuadraticParsableString() {
        Stream.of(MultidimensionalTester.f1, MultidimensionalTester.f2, MultidimensionalTester.f3)
                .sequential()
                .map(QuadraticFunction::toParsableString)
                .forEach(System.out::println);
    }
}