package methods;

import java.util.function.Function;

public class DichotomyMinimizer extends Minimizer {
    private final double delta;
    private double curA, curB;

    public DichotomyMinimizer(final Function<Double, Double> fun, final double a, final double b, final double eps, final double delta) {
        super(fun, a, b, eps);
        if (delta > 2 * eps) {
            throw new IllegalArgumentException("delta should be less then 2*eps");
        }
        this.delta = delta;
        restart();
    }

    public DichotomyMinimizer(final Function<Double, Double> fun, final double a, final double b, final double eps) {
        this(fun, a, b, eps, eps / 2);
    }

    @Override
    public boolean hasNext() {
        return (curB - curA) > 2 * eps;
    }

    @Override
    protected Section nextIteration() {
        double x1 = (curA + curB - delta) / 2;
        double x2 = (curA + curB + delta) / 2;
        if (fun.apply(x1) <= fun.apply(x2)) {
            curB = x2;
        } else {
            curA = x1;
        }
        return new Section(curA, curB);
    }

    @Override
    public void restart() {
        curA = a;
        curB = b;
    }

    @Override
    public double getCurrentXMin() {
        return (curA + curB) / 2;
    }
}
