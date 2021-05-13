package lab3;

/**
 * @author Yaroslav Ilin
 */
public class ProfileFormatMatrix {
    private final int n;
    private double[] di;
    private double[] al;
    private double[] au;
    private int[] ia;

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
        al = new double [ia[n] - 1];
        au = new double [ia[n] - 1];
        int k = 0;
        for (int i = 2; i < n + 1; i++) {
            for (int j = i - (ia[i] - ia[i - 1]); j < i; j++) {
                al[k] = a[i - 1][j];
                au[k++] = a[j][i - 1];
            }
        }
    }
}
