package methods.multidimensional.newton.expressions;

import expression.exceptions.CalculationException;

import java.util.Map;

public class Variable implements Expression {
    private final int index;

    public Variable(final int index) {
        this.index = index;
    }

    @Override
    public double evaluate(final Map<Integer, Double> x) {
        if (x.containsKey(index)) {
            return x.get(index);
        } else {
            throw new CalculationException("No value assigned to variable " + this);
        }
    }

    @Override
    public Expression differentiate(int x) {
        return x == index ? Const.ONE : Const.ZERO;
    }

    @Override
    public boolean equals(Object to) {
        if (to == null || to.getClass() != this.getClass()) {
            return false;
        }
        return index == ((Variable) to).index;
    }

    @Override
    public String toString() {
        return String.format("x_{%d}", index);
    }

    @Override
    public String toMiniString() {
        return toString();
    }
}
