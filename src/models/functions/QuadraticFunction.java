package models.functions;

import models.matrices.AdvancedMatrix;
import models.matrices.DiagonalMatrix;
import models.Vector;

public class QuadraticFunction {
    private final AdvancedMatrix a;

    private final Vector b;
    private final double c;

    /**
     * Construct a quadratic function f(x) = (ax, x)/2 + (b, x) + c
     */
    public QuadraticFunction(AdvancedMatrix a, Vector b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Get the value of this function at point x
     *
     * @param x the point
     * @return the value of function in x
     */
    public double get(Vector x) {
        return a.multiply(x).scalarProduct(x) / 2 + x.scalarProduct(b) + c;
    }

    /**
     * Get the value of gradient at point x
     *
     * @param x the point
     * @return the value of gradient in x
     */
    public Vector getGradient(Vector x) {
        return a.multiply(x).add(b);
    }

    /**
     * Get the value of Hessian at point x
     * Note that the quadratic function has a constant Hessian
     *
     * @param x the point
     * @return the value of Hessian in x
     */
    public AdvancedMatrix getHessian(Vector x) {
        return a;
    }

    public AdvancedMatrix getA() {
        return a;
    }

    public Vector getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getMinEigenValueAbs() {
        checkDiagonality();
        return ((DiagonalMatrix) a).getMinEigenValueAbs();
    }

    public double getMaxEigenValueAbs() {
        checkDiagonality();
        return ((DiagonalMatrix) a).getMaxEigenValueAbs();
    }

    private void checkDiagonality() {
        if (!(a instanceof DiagonalMatrix)) {
            throw new IllegalStateException("Performed an operation, that requires matrix diagonality");
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Function arity: %d %nA: %n%s %nB: %n%s %nC: %n%f %n",
                getA().size(),
                getA().toString(),
                getB().toString(),
                getC());
    }

}
