import methods.multidimensional.FullMatrix;
import methods.multidimensional.QuadraticFunction;
import methods.multidimensional.Vector;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

public class Main {

    public static void main(String[] args) {
        QuadraticFunction f = new QuadraticFunction(
                new FullMatrix(
                        List.of(
                        new Vector(128, 126),
                        new Vector(126, 128))
                ),
                new Vector(-10, 30),
                13
        );
        double minEigen = 2;
        double maxEigen = 254;
        double min = getMinByGradientDecsent(f, 2 / (minEigen + maxEigen), 1e-2);
        System.out.println("The minimum is: " + min);
    }

    static double getMinByGradientDecsent(QuadraticFunction f, double alpha, double eps) {
        Vector x = new Vector(0, 0);
        for (int i = 0; i < 10000; i++) {
            System.out.printf("%d-th iteration: x={%.4f,%.4f}, f(x)=%.4f",
                    i, x.getIth(0), x.getIth(1), f.get(x));
            System.out.println();
            if (f.getGradient(x).getEuclideanNorm() < eps) {
                break;
            }
            Vector p = f.getGradient(x).normalize();
            Vector next = x.add(p.multiply(-alpha));
            if (f.get(next) < f.get(x)) {
                x = next;
                continue;
            }
            alpha /= 2;
        }
        return f.get(x);
    }
}
