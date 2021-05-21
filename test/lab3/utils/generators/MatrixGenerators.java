package lab3.utils.generators;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static lab3.utils.generators.MainGenerator.random;

public class MatrixGenerators {
    private static final Random RANDOM = new Random(228);

    public static double[][] generateMatrix(final int n) {
        return generateSparseMatrix(n, -100, 100, 1);
    }

    /**
     * Generates a random matrix with given proportion of nonzero elements
     *
     * @param n         size of the matrix
     * @param lo        lower bound of elements
     * @param to        higher bound of elements
     * @param notZeroPr probability of each element being not zero
     * @return generated matrix
     */
    public static double[][] generateSparseMatrix(final int n, final double lo, final double to, final double notZeroPr) {
        return generateMatrix(n, () -> randomWithPossibleZero(lo, to, notZeroPr));
    }

    /**
     * Returns a random double with not zero probability of zero in given range
     *
     * @param lo        lower bound
     * @param hi        higher bound
     * @param notZeroPr probability of not zero value
     * @return generated random double
     */
    public static double randomWithPossibleZero(final double lo, final double hi, final double notZeroPr) {
        double x = RANDOM.nextDouble();
        return x <= notZeroPr ? lo + (hi - lo) * RANDOM.nextDouble() : 0;
    }

    /*
    * Generates a matrix that has the nonzero elements in a small neighbourhood of the diagonal
    * */
    public static double[][] generateDiagonalDominanceMatrix(final int n) {
        final double[][] matrix = generateIntegerMatrix(n, -4, 0);
        IntStream.range(0, n).forEach(i -> matrix[i][i] = -Arrays.stream(matrix[i]).sum() + matrix[i][i]);
        matrix[0][0] += 1d;
        return matrix;
    }

    /*
    * a[i][j] = 1 / (i + j + 1)
    *  i, j in [1 : n]
    * */
    public static double[][] generateHilbertMatrix(final int n) {
        final double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 1.0/(i + 1 + j);
            }
        }
        return matrix;
    }

    /*
    * Generates the matrix from the second task
    * */
    public static double[][] generateAkMatrix(final int n, final int k) {
        final double[][] matrix = generateIntegerMatrix(n, -4, 0);
        IntStream.range(0, n).forEach(i -> matrix[i][i] = -Arrays.stream(matrix[i]).sum() + matrix[i][i]);
        matrix[0][0] += Math.pow(10, -k);
        return matrix;
    }

    public static double[][] generateIntegerMatrix(final int n, final int lo, final int hi) {
        return generateMatrix(n, () -> RANDOM.nextInt(hi - lo) + lo);
    }

    public static double[][] generateMatrix(final int n, final DoubleSupplier getElement) {
        double[][] ans = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = getElement.getAsDouble();
            }
        }
        return ans;
    }
}
