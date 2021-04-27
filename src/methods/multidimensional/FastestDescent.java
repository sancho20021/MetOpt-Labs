package methods.multidimensional;

import methods.unidimensional.FibonacciMinimizer;
import methods.unidimensional.Minimizer;
import methods.unidimensional.ParabolicMinimizer;
import models.Vector;
import models.functions.QuadraticFunction;

import java.util.function.Function;

public class FastestDescent extends MultiMinimizer {
    private Vector x;
    private final double maxA;
    private final Class<? extends Minimizer> uniMinimizer;

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps, double maxA) {
        this(fun, startX, eps, maxA, startX.getDim() < 100 ? ParabolicMinimizer.class : FibonacciMinimizer.class);
    }

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps) {
        this(fun, startX, eps,
                startX.getDim() * Math.max(1, fun.getB().maxElementAbs()) * 2 * fun.getMaxEigenValueAbs());
    }

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps, Class<? extends Minimizer> uniMinimizer) {
        this(fun, startX, eps,
                2.0 / fun.getMinEigenValueAbs()/*startX.getDim() * Math.max(1, fun.getB().maxElementAbs()) * 2 * fun.getMaxEigenValueAbs()*/,
                uniMinimizer
        );
    }

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps, double maxA, Class<? extends Minimizer> uniMinimizer) {
        super(fun, startX, eps);
        this.maxA = maxA;
        this.uniMinimizer = uniMinimizer;
        restart();
    }

    @Override
    public boolean hasNext() {
        return fun.getGradient(x).getEuclideanNorm() >= eps;
    }

    @Override
    protected Vector nextIteration() {
        return x = x.add(fun.getGradient(x).multiply(-oneDimMin(x)));
    }

    @Override
    public void restart() {
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

    private double oneDimMin(Vector x0) {
        Function<Double, Double> uniFunction = a -> fun.get(x0.add(fun.getGradient(x0).multiply(-a)));
        try {
            var uniMinInstance = uniMinimizer
                    .getConstructor(Function.class, double.class, double.class, double.class)
                    .newInstance(uniFunction, 0.0, maxA, eps);
            return uniMinInstance.findMinimum();
        } catch (Exception e) {
            System.err.println("Error occurred while trying to use one dimension minimizer");
            throw new IllegalStateException("See log, message: " + e.getMessage(), e);
        }
    }
}
