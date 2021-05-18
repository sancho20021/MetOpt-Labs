package lab3;

import lab3.models.MutableSquareMatrix;

public class LU {

    static void applyLU(final MutableSquareMatrix matrix) {
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
}
