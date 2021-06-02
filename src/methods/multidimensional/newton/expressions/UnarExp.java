package methods.multidimensional.newton.expressions;

import expression.exceptions.OverflowException;

import java.util.Map;
import java.util.Objects;

public abstract class UnarExp extends UnarBinarExp {
    protected final Expression a;

    protected UnarExp(Expression a) {
        this.a = a;
    }

    protected abstract double calculate(double a);

    @Override
    public double evaluate(final Map<Integer, Double> x) {
        return calculate(a.evaluate(x));
    }

    @Override
    public boolean equals(Object to) {
        if (to == null || getClass() != to.getClass()) {
            return false;
        }
        final UnarExp other = (UnarExp) to;
        return Objects.equals(a, other.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, getClass());
    }

    @Override
    public String toString() {
        return "(" + getSymbol() + a + ")";
    }

    @Override
    public String toMiniString() {
        int aPr = getPriority(a);
        return getSymbol() + " " + withBrackets(aPr < firstPriority(), a.toMiniString());
    }

    protected OverflowException overflow(int a) {
        return new OverflowException(getSymbol() + " " + a);
    }
}
