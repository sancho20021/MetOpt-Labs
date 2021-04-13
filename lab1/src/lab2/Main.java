package lab2;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Yaroslav Ilin
 */

public class Main {

    public static void main(String[] args) {
        Function<Point, Double> f = (Point x) -> {
            double x1 = x.get(0), x2 = x.get(1);
            return 64*x1*x1 + 126*x1*x2 + 64*x2*x2 -10*x1 + 30*x2 + 13;
        };
        Function<Point, Double>[] gradient = new Function[2];
        gradient[0] = x -> x.get(0) * 128 + x.get(1) * 126 - 10;
        gradient[1] = x -> x.get(0) * 126 + 128 * x.get(1) + 30;
        System.out.println("The minimum is: " + getMinByGradientDecsent(new MultiFunction(f, gradient, null)));

    }

    static double getMinByGradientDecsent(MultiFunction f) {
        final double eps = 1e-2;
        double alpha = 1;
        Double[] tmp = {0.0, 0.0};
        Point x = new Point(tmp);
        boolean hasNext = true;
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%d-th iteration: x={%.2f,%.2f}, f(x)=%.4f",
                    i, x.coords[0], x.coords[1], f.getF(x));
            System.out.println();
            if (f.getGradientAbs(x) < eps) {
                break;
            }
            Point next = x.add(new Point(f.getGradient(x)).scale(-alpha));
            if (f.getF(next) < f.getF(x)) {
                x = next;
                continue;
            }
            alpha /= 2;
        }
        return f.getF(x);
    }

    static class MultiFunction {
        final Function<Point, Double> f;
        final Function<Point, Double>[] gradient;
        final Function<Point, Double>[][] gessian;

        MultiFunction(Function<Point, Double> f, Function<Point, Double>[] gradient, Function<Point, Double>[][] gessian) {
            this.f = f;
            this.gradient = gradient;
            this.gessian = gessian;
        }

        double getGradientAbs(Point x) {
            return Math.sqrt(Arrays.stream(getGradient(x)).mapToDouble(y -> y * y).sum());
        }

        Double[] getGradient(Point x) {
            return Arrays.stream(gradient).map(f -> f.apply(x)).toArray(Double[]::new);
        }

        double getF(Point x) {
            return f.apply(x);
        }
    }

    static class Point {
        final Double[] coords;

        Point() {
            this.coords = new Double[]{0.0, 0.0};
        }

        Point(Double[] coords) {
            this.coords = coords;
        }

        Point add(Point other) {
            Double[] res = new Double[coords.length];
            for (int i = 0; i < res.length; i++) {
                res[i] = coords[i] + other.coords[i];
            }
            return new Point(res);
        }

        Point scale(Double a) {
            Double[] res = new Double[coords.length];
            for (int i = 0; i < res.length; i++) {
                res[i] = coords[i] * a;
            }
            return new Point(res);
        }

        Double get(int i) {
            return coords[i];
        }
    }
}
