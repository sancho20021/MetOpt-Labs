package methods.multidimensional.newton.expressions;

public class Multiply extends BinarExp {
    public Multiply(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected String getSymbol() {
        return "*";
    }

    @Override
    protected double calculate(double a, double b) {
        return a * b;
    }

    @Override
    protected int firstPriority() {
        return 1;
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
        final Expression aDx = a.differentiate(x);
        final Expression bDx = b.differentiate(x);
        final Expression s1 = aDx.equals(Const.ZERO) || b.equals(Const.ZERO) ? Const.ZERO : new Multiply(aDx, b);
        final Expression s2 = a.equals(Const.ZERO) || bDx.equals(Const.ZERO) ? Const.ZERO : new Multiply(a, bDx);
        if (s1.equals(Const.ZERO)) {
            return s2;
        } else if (s2.equals(Const.ZERO)) {
            return s1;
        } else {
            return new Add(s1, s2);
        }
    }
}
