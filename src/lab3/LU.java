package lab3;

import lab3.models.MutableSquareMatrix;

public class LU {
    private static void safeSet(final MutableSquareMatrix matrix, final int i, final int j, final double x, final double eps) {
        matrix.set(i, j, Math.abs(x) < eps ? 0 : x);
    }

    static void applyLU(final MutableSquareMatrix matrix, final double eps) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < i; j++) {
                double l = matrix.get(i, j);
                for (int k = 0; k < j; k++) {
                    l -= matrix.get(i, k) * matrix.get(k, j);
                }
                safeSet(matrix, i, j, l, eps);
            }
            for (int j = 0; j < i; j++) {
                double u = matrix.get(j, i);
                for (int k = 0; k < j; k++) {
                    u -= matrix.get(j, k) * matrix.get(k, i);
                }
                safeSet(matrix, j, i, u / matrix.get(j, j), eps);
            }
            double l = matrix.get(i, i);
            for (int k = 0; k < i; k++) {
                l -= matrix.get(i, k) * matrix.get(k, i);
            }
            safeSet(matrix, i, i, l, eps);
        }
    }
}
