package methods.multidimensional.newton.expressions;

public class Subtract extends BinarExp {
    public Subtract(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected String getSymbol() {
        return "-";
    }

    @Override
    protected double calculate(double a, double b) {
        return a - b;
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
        return true;
    }

    @Override
    public Expression differentiate(int x) {
        final Expression s1 = a.differentiate(x);
        final Expression s2 = b.differentiate(x);
        if (s1.equals(Const.ZERO)) {
            return new Negate(s2);
        } else if (s2.equals(Const.ZERO)) {
            return s1;
        } else {
            return new Subtract(s1, s2);
        }
    }
}
