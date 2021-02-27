package methods;

public class ParabolicMinimizer extends Minimizer {
    private double x1, x2, x3;
    private double f1, f2, f3;
    private double curXMin, prevXMin;

    public ParabolicMinimizer(Function fun, double a, double b, double eps) {
        super(fun, a, b, eps);
        restart();
    }

    @Override
    public boolean hasNext() {
        return Math.abs(curXMin - prevXMin) > eps;
    }

    @Override
    protected Section nextIteration() {
        prevXMin = curXMin;
        updateXMin();
        double fMin = fun.evaluate(curXMin);

        if (curXMin < x2) {
            if (fMin >= f2) {
                x1 = curXMin;
                f1 = fMin;
            } else {
                x3 = x2;
                f3 = f2;
                x2 = curXMin;
                f2 = fMin;
            }
        } else {
            if (f2 >= fMin) {
                x1 = x2;
                f1 = f2;
                x2 = curXMin;
                f2 = fMin;
            } else {
                x3 = curXMin;
                f3 = fMin;
            }
        }
        return new Section(x1, x3);
    }

    @Override
    public void restart() {
        x1 = a;
        x2 = (a + b) / 2;
        x3 = b;
        f1 = fun.evaluate(x1);
        f2 = fun.evaluate(x2);
        f3 = fun.evaluate(x3);

        curXMin = Double.MAX_VALUE / 10;
        prevXMin = 0;
    }

    private void updateXMin() {
        double[] a = getParabolaParams();
        curXMin = 1.0 / 2 * (x1 + x2 - a[1] / a[2]);
    }

    @Override
    protected double getCurrentXMin() {
        return curXMin;
    }

    public Function getCurrentParabola() {
        double[] a = getParabolaParams();
        return x -> a[0] + a[1] * (x - x1) + a[2] * (x - x1) * (x - x2);
    }

    private double[] getParabolaParams() {
        double a0 = f1;
        double a1 = (f2 - f1) / (x2 - x1);
        double a2 = 1 / (x3 - x2) * ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1));
        return new double[]{a0, a1, a2};
    }
}
