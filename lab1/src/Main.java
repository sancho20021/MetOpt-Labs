import methods.multidimensional.*;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

public class Main {

    public static void main(String[] args) {
        QuadraticFunction f = new QuadraticFunction(
                new FullMatrix(
                        128, 126,
                        126, 128),
                new Vector(-10, 30),
                13
        );
        double minEigen = 2;
        double maxEigen = 254;
//        var gradientMinimizer = new GradientDescentMinimizer(
//                f, 2 / (minEigen + maxEigen), new Vector(0, 0), 1e-2);
//        for (int i = 0; i < 10000 && gradientMinimizer.hasNext(); i++) {
//            var x = gradientMinimizer.getCurrentXMin();
//            System.out.printf("%d-th iteration: x={%.4f,%.4f}, f(x)=%.4f",
//                    i, x.getIth(0), x.getIth(1), gradientMinimizer.getFun().get(x));
//            System.out.println();
//            gradientMinimizer.next();
//        }
        var conjMinimizer = new ConjugateGradientsMinimizer(f, new Vector(0, 0), 1e-2);
        for (int i = 0; i < 1000 && conjMinimizer.hasNext(); i++) {
            var x = conjMinimizer.getCurrentXMin();
            System.out.printf("%d-th iteration: x={%.4f,%.4f}, f(x)=%.4f",
                    i, x.getIth(0), x.getIth(1), conjMinimizer.getFun().get(x));
            System.out.println();
            conjMinimizer.next();
        }
    }
}
