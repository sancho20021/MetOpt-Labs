package ru.irmo.metopt.metopt.domain.methods;

import java.util.function.Function;

/**
 * @author Yaroslav Ilin
 */

public class CombinationMinimizer extends Minimizer {
    private static final double K = (3 - Math.sqrt(5)) / 2;  // Коэффициент золотого сечения
    private double x, w, v;  // x - точка соответствующая решению, w - точка, соответветствующая второму снизу значению функции, v – предыдущее значение w
    private double fx, fw, fv;  // значения функции в точках
    private double d, e; // длина текущего и предыдущего шагов
    private double l, r;  // Интервал поиска на решения

    public CombinationMinimizer(Function<Double, Double> fun, double a, double b, double eps) {
        super(fun, a, b, eps);
        restart();
    }

    @Override
    public boolean hasNext() {
        double tol = eps * Math.abs(x) + eps / 10;
        return Math.abs(x - (l + r) / 2) + (r - l) / 2 > 2 * tol;
    }

    @Override
    protected Section nextIteration() {
        double g = e;
        e = d;
        double tol = eps * Math.abs(x) + eps / 10;
        double u = 0;
        boolean tu = false;
        if (!(x == w || w == v || x == v) && !(fx == fw || fw == fv || fx == fv)) {  // если точки различны строим аппроксимирующую параболу
            u = minimParabol(w, fw, x, fx, v, fv);
            if (l < u && u < r && Math.abs(u - x) < g / 2) {
                tu = true;
                if (u - l < 2 * tol || r - u < 2 * tol) {
                    u = x - Math.signum(x - (l + r) / 2) * tol;
                }
            }
        }
        if (!tu) {  // если точка u не принялась на предыдущем шаге, находим ее с помощью метода золотого сечения.
            if (x < (r - l) / 2) {
                u = x + K * (r - x);
                e = r - x;
            } else {
                u = x - K * (x - l);
                e = x - l;
            }
        }
        if (Math.abs(u - x) < tol) {
            u = x + Math.signum(u - x) * tol; // Задаем минимальную близость между u и x
        }
        d = Math.abs(u - x);
        double fu = fun.apply(u);
        if (fu <= fx) { // вычисления новых значений функции и точек.
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
        return x2 - ((x2 - x1) * (x2 - x1) * (y2 - y3) - (x2 - x3) * (x2 - x3) * (y2 - y1)) / (2 * ((x2 - x1) * (y2 - y3) - (x2 - x3) * (y2 - y1))); // нахождение точки минимума параболы по 3 точкам.
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
