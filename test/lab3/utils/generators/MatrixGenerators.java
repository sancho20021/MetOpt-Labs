package lab3.utils.generators;

import java.util.function.DoubleSupplier;

import static lab3.utils.generators.MainGenerator.random;

public class MatrixGenerators {

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
        double x = random.nextDouble();
        return x <= notZeroPr ? lo + (hi - lo) * random.nextDouble() : 0;
    }

    public static double[][] generateIntegerMatrix(final int n, final int lo, final int hi) {
        return generateMatrix(n, () -> random.nextInt(hi - lo) + lo);
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
