package models.functions;

import methods.multidimensional.newton.expressions.Expression;
import models.Vector;
import models.matrices.AdvancedMatrix;
import models.matrices.FullMatrix;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

public class AnalyticFunction {
    private final int arity;
    private final Expression expression;
    private final Expression[] gradient;
    private final Expression[][] hessian;

    public AnalyticFunction(final int arity, final Expression expression) {
        this.arity = arity;
        this.expression = expression;
        gradient = IntStream.range(0, arity).mapToObj(expression::differentiate).toArray(Expression[]::new);
        hessian = new Expression[arity][arity];
        for (int i = 0; i < arity; i++) {
            hessian[i] = IntStream.range(0, arity).mapToObj(gradient[i]::differentiate).toArray(Expression[]::new);
        }
    }

    public int getArity() {
        return arity;
    }

    public double get(final Vector x) {
        return expression.evaluate(x);
    }

    public Vector getGradient(final Vector x) {
        return new Vector(Arrays.stream(gradient).mapToDouble(du -> du.evaluate(x)).toArray());
    }

    public Expression[] getGradient() {
        return gradient;
    }

    public AdvancedMatrix getHessian(final Vector x) {
        return new FullMatrix(mapToDouble(hessian, exp -> exp.evaluate(x)));
    }

    public Expression[][] getHessian() {
        return hessian;
    }

    private static <T> double[][] mapToDouble(final T[][] a, final Function<T, Double> f) {
        double[][] result = new double[a.length][a.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = f.apply(a[i][j]);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
