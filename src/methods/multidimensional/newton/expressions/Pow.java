package methods.multidimensional.newton.expressions;

public class Pow extends BinarExp {
    public Pow(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected double calculate(double a, double b) {
        return Math.pow(a, b);
    }

    @Override
    protected boolean isOrderImportant() {
        return true;
    }

    @Override
    protected boolean hasEps() {
        return false;
    }

    @Override
    protected String getSymbol() {
        return "^";
    }

    @Override
    protected int firstPriority() {
        return 2;
    }

    @Override
    public Expression differentiate(int x) {
        if (b instanceof Const) {
            if (b.equals(Const.ZERO)) {
                return Const.ONE;
            } else {
                return new Multiply(
                        b,
                        new Multiply(
                                a.differentiate(x),
                                b.equals(Const.TWO) ? a : new Pow(a, ((Const) b).add(-1))
                        )
                );
            }
        }
        final Expression lnATimesB = new Multiply(new Ln(a), b);
        return new Multiply(
                lnATimesB.differentiate(x),
                new Pow(Const.E, lnATimesB)
        );
    }
}
