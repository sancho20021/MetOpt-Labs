package lab3.models;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class SparseMatrix implements MutableSquareMatrix {
    private final double[] d;
    private final double[] al;
    private final double[] au;
    private final int[] ja;
    private final int[] ia;

    /**
     * Constructs a symmetric sparse matrix with element copies of other matrix
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
                if (ij != 0) {
                    jaList.add(j);
                    alList.add(ij);
                    auList.add(ij);
                    len++;
                }
            }
            ia[i + 1] = ia[i] + len;
        }
        al = alList.stream().mapToDouble(x -> x).toArray();
        au = auList.stream().mapToDouble(x -> x).toArray();
        ja = jaList.stream().mapToInt(x -> x).toArray();
    }

    /**
     * Constructs a matrix from scanner.
     * Format: {@code <n> \n a[1][0] \n a[2][0] a[2][1] \n ... a[n-1][0] ... a[n-1][n-2] \n a[0][0] .. a[n-1][n-1]}
     *
     * @param scn Scanner
     */
    public SparseMatrix(final Scanner scn) {
        final int n = scn.nextInt();
        d = new double[n];
        ia = new int[n + 1];
        final List<Double> alList = new ArrayList<>();
        final List<Double> auList = new ArrayList<>();
        final List<Integer> jaList = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (i % 1000 == 0) {
                System.out.println("Reading another 1000 done");
            }
            int len = 0;
            for (int j = 0; j < i; j++) {
                final double ij = scn.nextDouble();
                if (ij != 0) {
                    jaList.add(j);
                    alList.add(ij);
                    auList.add(ij);
                    len++;
                }
            }
            ia[i + 1] = ia[i] + len;
        }
        for (int i = 0; i < n; i++) {
            d[i] = scn.nextDouble();
        }
        al = alList.stream().mapToDouble(x -> x).toArray();
        au = auList.stream().mapToDouble(x -> x).toArray();
        ja = jaList.stream().mapToInt(x -> x).toArray();
    }

    public SparseMatrix(final double[][] data) {
        this(new FullMatrix(data));
    }

    public SparseMatrix(final DisSymMatrix disSymMatrix) {
        final int n = disSymMatrix.n;
        List<Map<Integer, Double>> rows = new ArrayList<>(List.of(Map.of()));
        for (int i = 1; i < n; i++) {
            final var row = disSymMatrix.getLowRow(i);
            rows.add(row);
        }
        d = IntStream.range(0, n).mapToDouble(disSymMatrix::getAii).toArray();
        ia = new int[n + 1];
        final List<Double> alList = new ArrayList<>();
        final List<Integer> jaList = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            ia[i + 1] = ia[i] + rows.get(i).size();
            for (final var aij : rows.get(i).entrySet()) {
                jaList.add(aij.getKey());
                alList.add(aij.getValue());
            }
        }
        ja = jaList.stream().mapToInt(x -> x).toArray();
        al = alList.stream().mapToDouble(x -> x).toArray();
        au = al;
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

    public static class DisSymMatrix {
        public final static int DIAG_N = 3;
        final int n;
        final List<double[]> diagonals;

        public DisSymMatrix(final int n, final List<double[]> diagonals) {
            this.n = n;
            this.diagonals = diagonals;
        }

        public static double getAiiSum(final int i, final List<double[]> diags, final int n) {
            double x = 0;
            for (final double[] diag : diags) {
                x += f(i, diag, n);
            }
            return x;
        }

        private static double f(final int i, final double[] diag, final int n) {
            final int index = i - (n - diag.length);
            final double left = 0 <= index && index < diag.length ? diag[index] : 0;
            final double right = 0 <= i && i < diag.length ? diag[i] : 0;
            return left + right;
        }

        public void print(final PrintWriter out) {
            out.println(n);
            out.println(diagonals.size());
            for (final var d : diagonals) {
                out.println(d.length);
                for (final double x : d) {
                    out.print(x + " ");
                }
                out.println();
            }
        }

        public static DisSymMatrix read(final Scanner scn) {
            final int n = scn.nextInt();
            final List<double[]> diagonals = new ArrayList<>(Collections.nCopies(scn.nextInt(), null));
            for (int i = 0; i < diagonals.size(); i++) {
                diagonals.set(i, DoubleStream.generate(scn::nextDouble).limit(scn.nextInt()).toArray());
            }
            return new DisSymMatrix(n, diagonals);
        }

        public Map<Integer, Double> getLowRow(final int i) {
            final NavigableMap<Integer, Double> row = new TreeMap<>();
            for (final double[] diag : diagonals) {
                final int index = i - (n - diag.length);
                if (0 <= index && index < diag.length) {
                    row.put(index, diag[index]);
                }
            }
            return row;
        }

        public double getAii(final int i) {
            for (final double[] diag : diagonals) {
                if (diag.length == n) {
                    return diag[i];
                }
            }
            return 0;
        }
    }
}
