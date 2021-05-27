package lab3.models;

import java.util.ArrayList;
import java.util.List;

public class SparseMatrix implements MutableSquareMatrix {
    private final double[] d;
    private final double[] al;
    private final double[] au;
    private final int[] ja;
    private final int[] ia;

    /**
     * Constructs a sparse matrix with element copies of other matrix
     *
     * @param other other matrix
     */
    public SparseMatrix(final SimpleSquareMatrix other) {
        final int n = other.size();
        d = new double[n];
        for (int i = 0; i < n; i++) {
            d[i] = other.get(i, i);
        }
        ia = new int[n + 1];
        final List<Double> alList = new ArrayList<>();
        final List<Double> auList = new ArrayList<>();
        final List<Integer> jaList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int len = 0;
            for (int j = 0; j < i; j++) {
                final double ij = other.get(i, j);
                final double ji = other.get(j, i);
                if (ij != 0 || ji != 0) {
                    jaList.add(j);
                    alList.add(ij);
                    auList.add(ji);
                    len++;
                }
            }
            ia[i + 1] = ia[i] + len;
        }
        al = alList.stream().mapToDouble(x -> x).toArray();
        au = auList.stream().mapToDouble(x -> x).toArray();
        ja = jaList.stream().mapToInt(x -> x).toArray();
    }

    public SparseMatrix(final double[][] data) {
        this(new FullMatrix(data));
    }

    @Override
    public void set(int i, int j, double x) {
        throw new RuntimeException("Set not implemented");
    }

    @Override
    public int size() {
        return d.length;
    }

    @Override
    public double get(int i, int j) {
        if (i == j) {
            return d[i];
        } else if (i > j) {
            return get(ia[i], ia[i + 1], j, al);
        } else {
            return get(ia[j], ia[j + 1], i, au);
        }
    }

    private double get(int from, int to, int j, double[] a) {
        if (from == to) {
            return 0;
        }
        if (ja[from] > j) {
            return 0;
        }
        int l = from, r = to;
        while (r - l > 1) {
            final int m = (l + r) / 2;
            if (ja[m] <= j) {
                l = m;
            } else {
                r = m;
            }
        }
        return ja[l] == j ? a[l] : 0;
    }

    public Vector multiply(final Vector v) {
        final int n = size();
        final double[] ans = new double[n];
        for (int i = 0; i < n; i++) {
            for (int k = ia[i]; k < ia[i + 1]; k++) {
                ans[i] += al[k] * v.get(ja[k]);
                ans[ja[k]] += au[k] * v.get(i);
            }
            ans[i] += d[i] * v.get(i);
        }
        return new Vector(ans);
    }
}
