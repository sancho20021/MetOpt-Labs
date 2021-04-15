package methods.multidimensional;

public class QuadraticFunction {
    private final SquareMatrix a;
    private final Vector b;
    private final double c;

    /**
     * Construct a quadratic function f(x) = (ax, x)/2 + (b, x) + c
    * */
    public QuadraticFunction(SquareMatrix a, Vector b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Get the value of this function at point x
     * @param x the point
     * @return the value of function in x
     * */
    public double get(Vector x) {
        return a.multiply(x).scalarProduct(x)/2 + x.scalarProduct(b) + c;
    }

    /**
     * Get the value of gradient at point x
     * @param x the point
     * @return the value of gradient in x
    * */
    public Vector getGradient(Vector x) {
        return a.multiply(x).add(b);
    }

    /**
     * Get the value of Hessian at point x
     * Note that the quadratic function has a constant Hessian
     * @param x the point
     * @return the value of Hessian in x
     * */
    public SquareMatrix getHessian(Vector x) {
        return a;
    }
}
