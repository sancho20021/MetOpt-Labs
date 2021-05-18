package lab3;

import lab3.models.SimpleSquareMatrix;

import java.util.Arrays;
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
    public static Optional<double[]> solve(final SimpleSquareMatrix a, final double[] b) {
        final int n = a.size();
        final var aCopy = a.getDataCopy();
        final var bCopy = Arrays.copyOf(b, b.length);
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                final double t = aCopy[i][k] / aCopy[k][k];
                bCopy[i] = bCopy[i] - t * bCopy[k];
                for (int j = k + 1; j < n; j++) {
                    aCopy[i][j] = aCopy[i][j] - t * aCopy[k][j];
                }
            }
        }
        final double[] x = new double[n];
        x[n - 1] = bCopy[n - 1] / aCopy[n - 1][n - 1];
        for (int kk = n - 2; kk >= 0; kk--) {
            final int k = kk;
            x[k] = (bCopy[k] - IntStream.range(k + 1, n).mapToDouble(j -> aCopy[k][j] * x[j]).sum()) / aCopy[k][k];
        }
        return Optional.of(x);
    }

}
