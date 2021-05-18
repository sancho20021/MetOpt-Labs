package lab3.models;

import org.junit.Test;

import java.util.Arrays;

import static lab3.utils.generators.MatrixGenerators.generateMatrix;
import static lab3.utils.generators.MatrixGenerators.generateSparseMatrix;
import static org.junit.Assert.assertTrue;

public class ProfileFormatMatrixTest {

    private boolean equal(final ProfileFormatMatrix profileFormatMatrix, final double[][] matrix) {
        int n = profileFormatMatrix.size();
        if (n != matrix.length) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i].length != n || matrix[i][j] != profileFormatMatrix.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void profileCorrectnessTest() {
        final int SAMPLES = 10;
        final int MAX_N = 100;

        for (int n = 1; n < MAX_N; n++) {
            System.out.println("Testing for n=" + n);
            for (int k = 0; k < SAMPLES; k++) {
                final double[][] matrix = generateMatrix(n);
                try {
                    assertTrue(equal(new ProfileFormatMatrix(n, matrix), matrix));
                } catch (final Exception e) {
                    System.err.println("Exception is thrown on " + matrixToString(matrix));
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    /**
     * На самом деле я понял что этот тест мало чего показывает, т.к. нулей может быть много, то толку от них мало,
     * если ненулевые элементы будут располагаться далеко от диагонали. В таком случае профиль будет получаться широким
     */
    @Test
    public void testProfileMatrixEfficiency() {
        final int samples = 5;
        final int n = 100;
        final double step = 0.1;
        final int maxSize = n * n;
        for (double pr = 0; pr <= 1; pr += step) {
            System.out.println("Testing for " + pr + " proportion of nonzero elements");
            for (int i = 0; i < samples; i++) {
                final double[][] m = generateSparseMatrix(n, -100, 100, pr);
                // System.out.println(Arrays.deepToString(m));
                final int optimizedSize = new ProfileFormatMatrix(m).getDataSize();
                System.out.println(optimizedSize + "/" + maxSize + " elements array created");
            }
            System.out.println();
        }
    }

    private static String matrixToString(final double[][] matrix) {
        return new FullMatrix(Arrays.stream(matrix)
                .flatMapToDouble(Arrays::stream)
                .toArray()).toString();
    }
}