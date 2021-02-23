package methods;

public class DichotomyMinimizer extends Minimizer {
    private final double delta;
    private double curA, curB;

    public DichotomyMinimizer(final Function fun, final double a, final double b, final double eps, final double delta) {
        super(fun, a, b, eps);
        if (delta > 2 * eps) {
            throw new IllegalArgumentException("delta should be less then 2*eps");
        }
        this.delta = delta;
        curA = a;
        curB = b;
    }

    @Override
    public boolean hasNext() {
        return (curB - curA) > 2 * eps;
    }

    @Override
    public Section next() {
        if (!hasNext()) {
            return null;
        }
        double x1 = (curA + curB - delta) / 2;
        double x2 = (curA + curB + delta) / 2;
        if (fun.evaluate(x1) <= fun.evaluate(x2)) {
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
    public double findMinimum() {
        while (hasNext()) {
            next();
        }
        return (curA + curB) / 2;
    }
}
