package methods.multidimensional.newton.expressions;

import java.util.Map;
import java.util.Objects;

public class Const implements Expression {
    public final static Const ZERO = new Const(0);
    public final static Const ONE = new Const(1);
    public final static Const TWO = new Const(2);
    public final static Const E = new Const(Math.E);
    private final double value;

    public Const(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(final Map<Integer, Double> x) {
        return value;
    }

    @Override
    public Expression differentiate(int x) {
        return ZERO;
    }

    @Override
    public boolean equals(Object to) {
        if (to == null || getClass() != to.getClass()) {
            return false;
        }
        return value == ((Const) to).value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toMiniString() {
        return toString();
    }
}
