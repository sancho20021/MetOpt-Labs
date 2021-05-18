package lab3.models;

import java.util.stream.IntStream;

/**
 * @author Yaroslav Ilin
 */
public class ProfileFormatMatrix implements MutableSquareMatrix {
    private final int n;
    private final double[] di;
    private final double[] al;
    private final double[] au;
    private final int[] ia;

    public ProfileFormatMatrix(final double... values) {
        this(new FullMatrix(values).getStandardMatrix());
    }

    public ProfileFormatMatrix(final double[][] a) {
        this(a.length, a);
    }

    public ProfileFormatMatrix(final int n, final double[][] a) {
        assert a.length >= n;
        assert a[0].length >= n;
        this.n = n;
        di = new double[n];
        for (int i = 0; i < n; i++) {
            di[i] = a[i][i];
        }
        ia = new int[n + 1];
        ia[0] = 0;
        for (int i = 0; i < n; i++) {
            int firstNotZero = 0;  // first not zero element in i-th row or column
            while (firstNotZero < i && a[i][firstNotZero] == 0 && a[firstNotZero][i] == 0) {
                firstNotZero++;
            }
            ia[i + 1] = ia[i] + i - firstNotZero; // i-th row (column) has (i - firstNotZero) not zero elements
        }
        al = new double[ia[n]];
        au = new double[ia[n]];
        for (int i = 1; i < n; i++) {
            int profileLen = ia[i + 1] - ia[i];
            int firstNotZero = i - profileLen;
            for (int j = 0; j < profileLen; j++) {
                al[ia[i] + j] = a[i][firstNotZero + j];
                au[ia[i] + j] = a[firstNotZero + j][i];
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

    public int getDataSize() {
        return au.length + di.length + al.length;
    }
}
