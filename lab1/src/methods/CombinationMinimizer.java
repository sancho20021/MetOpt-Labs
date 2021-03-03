package methods;

import java.util.function.Function;

/**
 * @author Yaroslav Ilin
 */

public class CombinationMinimizer extends Minimizer {
    private static final double K = (3 - Math.sqrt(5)) / 2;
    private double x, w, v;
    private double fx, fw, fv;
    private double d, e;
    private double l, r;

    public CombinationMinimizer(Function<Double, Double> fun, double a, double b, double eps) {
        super(fun, a, b, eps);
        restart();
    }

    @Override
    public boolean hasNext() {
        return Math.abs(r - l) > eps;
    }

    @Override
    protected Section nextIteration() {
        double g = e;
        e = d;
        double u = 0;
        boolean tu = false;
        if (!(x == w || w == v || x == v) && !(fx == fw || fw == fv || fx == fv)) {
            u = minimParabol(w, fw, x, fx, v, fv);
            if (l + eps <= u && u <= r - eps && Math.abs(u - x) < (g / 2)) {
                d = Math.abs(u - x);
                tu = true;
            }
        }
        if (!tu) {
            if (x < (r - l) / 2) {
                u = x + K * (r - x);
                d = r - x;
            } else {
                u = x - K * (x - l);
                d = x - l;
            }
        }
//        if (Math.abs(u - x) < eps) {
//            u = x + Math.signum(u - x) * eps;
//        }
        double fu = fun.apply(u);
        if (fu <= fx) {
            if (u >= x) {
                l = x;
            } else {
                r = x;
            }
            v = w;
            w = x;
            x = u;
            fv = fw;
            fw = fx;
            fx = fu;
        } else {
            if (u >= x) {
                r = u;
            } else {
                l = u;
            }
            if (fu <= fw || w == x) {
                v = w;
                w = u;
                fv = fw;
                fw = fu;
            } else if (fu <= fv || v == x || v == w) {
                v = u;
                fv = fu;
            }
        }
        return new Section(l, r);
    }

    private double minimParabol(double x1, double y1, double x2, double y2, double x3, double y3) {
        return x2 - ((x2 - x1) * (x2 - x1) * (y2 - y3) - (x2 - x3) * (x2 - x3) * (y2 - y1)) / (2 * ((x2 - x1) * (y2 - y3) - (x2 - x3) * (y2 - y1)));
    }

    @Override
    public void restart() {
        l = a;
        r = b;
        x = (l + r) / 2;
        w = x;
        v = x;
        fx = fun.apply(x);
        fw = fx;
        fv = fx;
        d = r - l;
        e = d;
    }

    @Override
    public double getCurrentXMin() {
        return x;
    }
}
