package lab3.methods;

import lab3.models.*;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class LUTest {
    @Test
    public void testSLAU() {
        GaussTest.testSLAUSolver(
                10,
                3,
                -10,
                10,
                slau -> LU.solveInPlace(new ProfileFormatMatrix(slau.getA()), slau.getB().getElementsArrayCopy())
        );
    }

    @Test
    public void test() {
        final int samples = 3;
        final int n = 4;
        final double eps = 0.0000001;
        for (int k = 0; k < samples; k++) {
            System.out.println("----------sample " + k + " ---------------------");
            final double[][] matrix = MatrixGenerators.generateIntegerMatrix(n, -5, 5);
            if (!testLU(matrix, eps)) {
                Assert.fail();
            }
            System.out.println("OK");
        }
    }

    private static boolean testLU(final double[][] matrix, final double eps) {
        final SimpleSquareMatrix origin = new ProfileFormatMatrix(matrix);
        final MutableSquareMatrix working = new ProfileFormatMatrix(matrix);
        LU.applyLU(working);
        final AdvancedMatrix l = getL(working);
        final AdvancedMatrix u = getU(working);
        System.out.println("Origin: ");
        System.out.println(origin.convertToString());
        System.out.println("L:");
        System.out.println(l.convertToString());
        System.out.println("U:");
        System.out.println(u.convertToString());
        if (!areEqual(origin, l.multiply(u), eps)) {
            System.out.println("Error LU not correct");
            return false;
        }
        return true;
    }

    public static boolean areEqual(final SimpleSquareMatrix m1, final SimpleSquareMatrix m2, final double eps) {
        int n = m1.size();
        if (n != m2.size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.abs(m1.get(i, j) - m2.get(i, j)) >= eps) {
                    return false;
                }
            }
        }
        return true;
    }

    public static AdvancedMatrix getL(final SimpleSquareMatrix matrix) {
        final int n = matrix.size();
        double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                data[i][j] = matrix.get(i, j);
            }
        }
        return new FullMatrix(data);
    }

    public static AdvancedMatrix getU(final SimpleSquareMatrix matrix) {
        final int n = matrix.size();
        double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            data[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                data[i][j] = matrix.get(i, j);
            }
        }
        return new FullMatrix(data);
    }

    public static AdvancedMatrix getD(final SimpleSquareMatrix matrix) {
        return new DiagonalMatrix(IntStream.range(0, matrix.size()).mapToDouble(i -> matrix.get(i, i)).toArray());
    }
}