package methods.multidimensional.newton.expressions;

public class Divide extends BinarExp {
    public Divide(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected String getSymbol() {
        return "/";
    }

    @Override
    protected double calculate(double a, double b) {
        return a / b;
    }

    @Override
    protected int firstPriority() {
        return 1;
    }

    @Override
    protected boolean hasEps() {
        return true;
    }

    @Override
    protected boolean isOrderImportant() {
        return true;
    }

    @Override
    public Expression differentiate(int x) {
        return new Divide(
                new Subtract(
                        new Multiply(a.differentiate(x), b),
                        new Multiply(a, b.differentiate(x))
                ),
                new Pow(b, Const.TWO)
        );
    }
}
