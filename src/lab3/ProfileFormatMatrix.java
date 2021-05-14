package lab3;

import lab3.models.MutableSquareMatrix;
import lab3.models.SimpleSquareMatrix;

/**
 * @author Yaroslav Ilin
 */
public class ProfileFormatMatrix extends SimpleSquareMatrix implements MutableSquareMatrix {
    private final int n;
    private final double[] di;
    private final double[] al;
    private final double[] au;
    private final int[] ia;

    public ProfileFormatMatrix(int n, double[][] a) {
        assert a.length >= n;
        assert a[0].length >= n;
        this.n = n;
        di = new double[n];
        for (int i = 0; i < n; i++) {
            di[i] = a[i][i];
        }
        ia = new int[n + 1];
        ia[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            int c = i - 1;
            for (int j = 0; j < i - 1; j++) {
                if (a[i - 1][j] != 0) {
                    c = j;
                    break;
                }
            }
            ia[i] = i - c - 1 + ia[i - 1];
        }
        al = new double[ia[n] - 1];
        au = new double[ia[n] - 1];
        int k = 0;
        for (int i = 2; i < n + 1; i++) {
            for (int j = i - (ia[i] - ia[i - 1]); j < i; j++) {
                al[k] = a[i - 1][j];
                au[k++] = a[j][i - 1];
            }
        }
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public double get(int i, int j) {
        if (i == j) {
            return di[i];
        } else if (i > j) {
            int profileLen = ia[i + 1] - ia[i];
            int firstNotZero = i - profileLen;
            return j < firstNotZero ? 0 : al[ia[i] + j - firstNotZero];
        } else {
            int profileLen = ia[j + 1] - ia[j];
            int firstNotZero = j - profileLen;
            return i < firstNotZero ? 0 : au[ia[j] + i - firstNotZero];
        }
    }

    @Override
    public void set(int i, int j, double x) {
        if (i == j) {
            di[i] = x;
        } else if (i > j) {
            int profileLen = ia[i + 1] - ia[i];
            int firstNotZero = i - profileLen;
            if (j < firstNotZero) {
                throw new IllegalArgumentException("Changing the profile of matrix is not allowed");
            }
            al[ia[i] + j - firstNotZero] = x;
        } else {
            int profileLen = ia[j + 1] - ia[j];
            int firstNotZero = j - profileLen;
            if (i < firstNotZero) {
                throw new IllegalArgumentException("Changing the profile of matrix is not allowed");
            }
            au[ia[j] + i - firstNotZero] = x;
        }
    }
}
