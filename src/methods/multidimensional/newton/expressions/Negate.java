package methods.multidimensional.newton.expressions;

public class Negate extends UnarExp {

    public Negate(final Expression a) {
        super(a);
    }

    @Override
    protected double calculate(double a) {
        return -a;
    }

    @Override
    protected String getSymbol() {
        return "-";
    }

    @Override
    protected int firstPriority() {
        return 1;
    }

    @Override
    public Expression differentiate(int x) {
        return new Negate(a.differentiate(x));
    }
}
