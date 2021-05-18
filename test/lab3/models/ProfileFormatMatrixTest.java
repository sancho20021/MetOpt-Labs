package lab3.models;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ProfileFormatMatrixTest {
    private static final Random random = new Random(228);

    @Test
    public void profileCorrectnessTest() {
        final int SAMPLES = 10000;
        final int MAX_N = 20;

        for (int n = 1; n < MAX_N; n++) {
            System.out.println("Testing for n=" + n);
            for (int k = 0; k < SAMPLES; k++) {
                final double[][] matrix = generateMatrix(n);
                try{
                    final ProfileFormatMatrix profileFormatMatrix = new ProfileFormatMatrix(n, matrix);
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            assertEquals("failed on " + matrixToString(matrix), matrix[i][j], profileFormatMatrix.get(i, j), 0);
                        }
                    }
                } catch (final Exception e) {
                    System.err.println("Exception is thrown on " + matrixToString(matrix));
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private static String matrixToString(final double[][] matrix) {
        return new FullMatrix(Arrays.stream(matrix)
                .flatMapToDouble(Arrays::stream)
                .toArray()).toString();
    }

    private static double[][] generateMatrix(final int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> random.doubles(-100, 100).limit(n).toArray())
                .toArray(double[][]::new);
    }
}