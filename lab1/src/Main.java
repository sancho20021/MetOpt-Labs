import methods.multidimensional.Vector;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Yaroslav Ilin
 */

public class Main {

    public static void main(String[] args) {
        Function<Vector, Double> f = (Vector x) -> {
            double x1 = x.getIth(0), x2 = x.getIth(1);
            return 64*x1*x1 + 126*x1*x2 + 64*x2*x2 -10*x1 + 30*x2 + 13;
        };
        Function<Vector, Double>[] gradient = new Function[2];
        gradient[0] = x -> x.getIth(0) * 128 + x.getIth(1) * 126 - 10;
        gradient[1] = x -> x.getIth(0) * 126 + 128 * x.getIth(1) + 30;
        double min = getMinByGradientDecsent(new MultiFunction(f, gradient, null));
        System.out.println("The minimum is: " + min);
    }

    static double getMinByGradientDecsent(MultiFunction f) {
        final double eps = 1e-2;
        double alpha = 1;
        Vector x = new Vector(0, 0);
        boolean hasNext = true;
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%d-th iteration: x={%.2f,%.2f}, f(x)=%.4f",
                    i, x.getIth(0), x.getIth(1), f.getF(x));
            System.out.println();
            if (f.getGradientAbs(x) < eps) {
                break;
            }
            Vector next = x.add(f.getGradient(x).multiply(-alpha));
            if (f.getF(next) < f.getF(x)) {
                x = next;
                continue;
            }
            alpha /= 2; // на самом деле надо перейти на третий шаг!
        }
        return f.getF(x);
    }

    static class MultiFunction {
        final Function<Vector, Double> f;
        final Function<Vector, Double>[] gradient;
        final Function<Vector, Double>[][] gessian;

        MultiFunction(Function<Vector, Double> f, Function<Vector, Double>[] gradient, Function<Vector, Double>[][] gessian) {
            this.f = f;
            this.gradient = gradient;
            this.gessian = gessian;
        }

        double getGradientAbs(Vector x) {
            return Math.sqrt(getGradient(x).getEuclideanNorm());
        }

        Vector getGradient(Vector x) {
            return new Vector(Arrays.stream(gradient).mapToDouble(f -> f.apply(x)).toArray());
        }

        double getF(Vector x) {
            return f.apply(x);
        }
    }
}
