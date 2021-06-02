package methods.multidimensional.newton.expressions;

public class Ln extends UnarExp {
    public Ln(Expression a) {
        super(a);
    }

    @Override
    public Expression differentiate(int x) {
        return new Divide(a.differentiate(x), a);
    }

    @Override
    protected String getSymbol() {
        return "ln";
    }

    @Override
    protected int firstPriority() {
        return 3;
    }

    @Override
    protected double calculate(double a) {
        return Math.log(a);
    }
}
