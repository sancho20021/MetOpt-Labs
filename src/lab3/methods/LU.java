package lab3.methods;

import lab3.models.MutableSquareMatrix;


public class LU {
    /**
     * Applies LU-decomposition to matrix.
     * Does n*(n^2 - 1)/3 muls or divs
     * @param matrix given matrix
     */
    public static void applyLU(final MutableSquareMatrix matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < i; j++) {
                double l = matrix.get(i, j);
                for (int k = 0; k < j; k++) {
                    l -= matrix.get(i, k) * matrix.get(k, j);
                }
                matrix.set(i, j, l);
            }
            for (int j = 0; j < i; j++) {
                double u = matrix.get(j, i);
                for (int k = 0; k < j; k++) {
                    u -= matrix.get(j, k) * matrix.get(k, i);
                }
                matrix.set(j, i, u / matrix.get(j, j));
            }
            double l = matrix.get(i, i);
            for (int k = 0; k < i; k++) {
                l -= matrix.get(i, k) * matrix.get(k, i);
            }
            matrix.set(i, i, l);
        }
    }

    /**
     * Solves a system of linear equations in form of Ax = b.
     * Does n^3/3 + n^2 + 2n/3 muls or divs
     *
     * @param a matrix A
     * @param b vector b
     * @return x, solution
     */
    public static double[] solveInPlace(final MutableSquareMatrix a, final double[] b) {
        applyLU(a);
        final double[] y = Gauss.forwardSolve(a, b);  // solving Ly = b
        // We should set u_{i, i} = 1 for every 0 <= i < n
        for (int i = 0; i < a.size(); i++) {
            a.set(i, i, 1);
        }
        return Gauss.backwardSolve(a, y);  // solving Ux = b
    }
}
