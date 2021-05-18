package lab3.models;

import lab3.models.MutableSquareMatrix;
import lab3.models.SimpleSquareMatrix;

/**
 * @author Yaroslav Ilin
 */
public class ProfileFormatMatrix implements MutableSquareMatrix {
    private final int n;
    private final double[] di;
    private final double[] al;
    private final double[] au;
    private final int[] ia;

    public ProfileFormatMatrix(final int n, final double[][] a) {
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
    public double get(final int i, final int j) {
        if (i == j) {
            return di[i];
        } else if (i > j) {
            final int profileLen = ia[i + 1] - ia[i];
            final int firstNotZero = i - profileLen;
            return j < firstNotZero ? 0 : al[ia[i] + j - firstNotZero];
        } else {
            final int profileLen = ia[j + 1] - ia[j];
            final int firstNotZero = j - profileLen;
            return i < firstNotZero ? 0 : au[ia[j] + i - firstNotZero];
        }
    }

    @Override
    public void set(final int i, final int j, final double x) {
        if (i == j) {
            di[i] = x;
        } else if (i > j) {
            setProfile(i, j, x, al);
        } else {
            setProfile(j, i, x, au);
        }
    }

    private void setProfile(final int i, final int j, final double x, final double[] al) {
        final int profileLen = ia[i + 1] - ia[i];
        final int firstNotZero = i - profileLen;
        if (j < firstNotZero) {
            if (x != 0) {
                throw new IllegalArgumentException("Changing the profile of matrix is not allowed");
            } else {
                return;
            }
        }
        al[ia[i] + j - firstNotZero] = x;
    }
}
