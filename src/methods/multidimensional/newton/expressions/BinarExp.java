package methods.multidimensional.newton.expressions;

import expression.exceptions.OverflowException;

import java.util.Map;
import java.util.Objects;

public abstract class BinarExp extends UnarBinarExp {
    protected final Expression a;
    protected final Expression b;

    protected BinarExp(Expression a, Expression b) {
        this.a = a;
        this.b = b;
    }

    protected abstract double calculate(double a, double b);

    @Override
    public double evaluate(final Map<Integer, Double> x) {
        return calculate(a.evaluate(x), b.evaluate(x));
    }

    @Override
    public boolean equals(Object to) {
        if (to == null || getClass() != to.getClass()) {
            return false;
        }
        final BinarExp other = (BinarExp) to;
        return Objects.equals(a, other.a)
                && Objects.equals(b, other.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, getClass());
    }

    @Override
    public String toString() {
        return "(" + a + " " + getSymbol() + " " + b + ")";
    }

    @Override
    public String toMiniString() {
        int aPr = getPriority(a);
        int bPr = getPriority(b);
        boolean hasBEps = b instanceof BinarExp && ((BinarExp) b).hasEps();
        StringBuilder str = new StringBuilder();
        str.append(withBrackets(aPr < firstPriority(), a.toMiniString()));
        str.append(" ").append(getSymbol()).append(" ");
        str.append(withBrackets(
                bPr < firstPriority() || (bPr == firstPriority()) && (hasBEps || isOrderImportant()),
                b.toMiniString()
        ));
        return str.toString();
    }

    protected abstract boolean isOrderImportant();

    protected abstract boolean hasEps();

    protected OverflowException overflow(int a, int b) {
        return new OverflowException(a + " " + getSymbol() + " " + b);
    }
}
