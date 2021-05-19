package lab3.methods;

import lab3.models.MutableSquareMatrix;
import lab3.models.SimpleSquareMatrix;

import java.util.Optional;
import java.util.stream.IntStream;

public class Gauss {
    /**
     * Finds solution to the equation Ax = b
     *
     * @param a matrix A
     * @param b vector b
     * @return solution, vector x, or empty optional if there is not exactly one solution
     */
    public static Optional<double[]> solve(final MutableSquareMatrix a, final double[] b) {
        return solve(a, b, false, 0);
    }

    public static Optional<double[]> solveOptimized(final MutableSquareMatrix a, final double[] b, final double eps) {
        return solve(a, b, true, eps);
    }

    /**
     * Solves system of linear equations in form of Lx = b, where L is lower-triangle shaped matrix
     *
     * @param l matrix L
     * @param b vector b
     * @return solution, x
     */
    public static double[] forwardSolve(final SimpleSquareMatrix l, final double[] b) {
        final int n = l.size();
        final double[] x = new double[n];
        IntStream.range(0, n)
                .forEach(i -> x[i] = (b[i] - IntStream.range(0, i).mapToDouble(j -> x[j] * l.get(i, j)).sum()) / l.get(i, i));
        return x;
    }

    /**
     * Solves system of linear equations in form of Ux = b, where U is upper-triangle shaped matrix
     *
     * @param u matrix U
     * @param b vector b
     * @return solution, x
     */
    public static double[] backwardSolve(final SimpleSquareMatrix u, final double[] b) {
        return backwardSolve(u, b, IntStream.range(0, b.length).toArray());
    }

    private static double[] backwardSolve(final SimpleSquareMatrix u, final double[] b, final int[] perm) {
        final int n = u.size();
        final double[] x = new double[n];
        for (int kk = n - 1; kk >= 0; kk--) {
            final int k = kk;
            x[perm[k]] = (b[perm[k]] - IntStream.range(k + 1, n).mapToDouble(j -> u.get(perm[k], j) * x[j]).sum()) / u.get(perm[k], k);
        }
        return x;
    }

    private static Optional<double[]> solve(final MutableSquareMatrix a, final double[] b, final boolean doSwap, final double eps) {
        final int n = a.size();
        final int[] perm = IntStream.range(0, n).toArray();
        for (int k = 0; k < n - 1; k++) {
            if (doSwap) {
                if (!swapKthWithLargest(a, b, k, perm, eps)) {
                    return Optional.empty();
                }
            }

            for (int i = k + 1; i < n; i++) {
                final double t = a.get(perm[i], k) / a.get(perm[k], k);
                b[perm[i]] = b[perm[i]] - t * b[perm[k]];
                for (int j = k + 1; j < n; j++) {
                    a.set(perm[i], j, a.get(perm[i], j) - t * a.get(perm[k], j));
                }
            }
        }
        return Optional.of(backwardSolve(a, b, perm));
    }

    private static boolean swapKthWithLargest(final MutableSquareMatrix a, final double[] b, final int k, final int[] perm, final double eps) {
        int m = k;
        double amk = Math.abs(a.get(perm[k], k));
        for (int i = m + 1; i < a.size(); i++) {
            final double x = Math.abs(a.get(perm[i], k));
            if (x > amk) {
                amk = x;
                m = i;
            }
        }
        if (amk < eps) {
            return false;
        }
        final int t = perm[k];
        perm[k] = perm[m];
        perm[m] = t;
        return true;
    }

}
