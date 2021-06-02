package methods.multidimensional.newton.expressions;

public class Add extends BinarExp {
    public Add(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected String getSymbol() {
        return "+";
    }

    @Override
    protected double calculate(double a, double b) {
        return a + b;
    }

    @Override
    protected int firstPriority() {
        return 0;
    }

    @Override
    protected boolean hasEps() {
        return false;
    }

    @Override
    protected boolean isOrderImportant() {
        return false;
    }

    @Override
    public Expression differentiate(int x) {
        final Expression s1 = a.differentiate(x);
        final Expression s2 = b.differentiate(x);
        if (s1.equals(Const.ZERO)) {
            return s2;
        } else if (s2.equals(Const.ZERO)) {
            return s1;
        } else {
            return new Add(s1, s2);
        }
    }
}
