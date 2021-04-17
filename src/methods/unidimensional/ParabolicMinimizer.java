package methods.unidimensional;

import java.util.function.Function;

public class ParabolicMinimizer extends Minimizer {
    private double x1, x2, x3;
    private double f1, f2, f3;
    private double curXMin, prevXMin;

    public ParabolicMinimizer(Function<Double, Double> fun, double a, double b, double eps) {
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
        double fMin = fun.apply(curXMin);

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
        x3 = b;
        f1 = fun.apply(x1);
        f3 = fun.apply(x3);

        setInitialX2();

        prevXMin = Double.MAX_VALUE / 10;
        curXMin = x2;
    }

    private void updateXMin() {
        double[] a = getParabolaParams();
        curXMin = 1.0 / 2 * (x1 + x2 - a[1] / a[2]);
    }

    @Override
    public double getCurrentXMin() {
        return curXMin;
    }

    public Function<Double, Double> getCurrentParabola() {
        double[] a = getParabolaParams();
        return x -> a[0] + a[1] * (x - x1) + a[2] * (x - x1) * (x - x2);
    }

    private double[] getParabolaParams() {
        double a0 = f1;
        double a1 = (f2 - f1) / (x2 - x1);
        double a2 = 1 / (x3 - x2) * ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1));
        return new double[]{a0, a1, a2};
    }

    private void setInitialX2() {
        int count = 100;
        double left = a, right = b;
        while (count > 0) {
            double p1 = left + (right - left) * 1 / 3, p2 = left + (right - left) * 2 / 3;
            double fp1 = fun.apply(p1), fp2 = fun.apply(p2);
            double pMin = fp1 < fp2 ? p1 : p2;
            double fpMin = Math.min(fp1, fp2);
            if (fpMin < f1 && fpMin < f3) {
                x2 = pMin;
                f2 = fpMin;
                return;
            }
            if (fp1 > fp2) {
                left = p1;
            } else {
                right = p2;
            }
            count--;
        }
        throw new IllegalStateException("No suitable x1 x2 x3 found for Parabolic minimizer");
    }
}
