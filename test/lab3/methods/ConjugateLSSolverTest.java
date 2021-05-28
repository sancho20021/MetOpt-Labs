package lab3.methods;

import lab3.models.FullMatrix;
import lab3.models.SparseMatrix;
import lab3.models.Vector;
import org.junit.Assert;
import org.junit.Test;

public class ConjugateLSSolverTest {
    private final static double EPS = 1e-7;


    @Test
    public void test() {
//        checkEqual(new SparseMatrix(new FullMatrix(8, 1, 1, -3)), new Vector(1, 2));
    }
    private static void checkEqual(final SparseMatrix a, final Vector f) {
        final Vector x0 = new Vector(new double[a.size()]);
        final FullMatrix fullMatrix = new FullMatrix(a);
        System.out.println(fullMatrix);
        System.out.println(f);
        final Vector expected = new Vector(Gauss.solve(fullMatrix, f.getElementsArrayCopy()).orElseThrow());
        final Vector got = new ConjugateLSSolver(a, f, x0, EPS).solve().orElseThrow();
        Assert.assertTrue(checkEqual(expected, got, EPS));
    }

    public static boolean checkEqual(final Vector a, final Vector b, final double eps) {
        final int n = a.size();
        if (n != b.size()) {
            return false;
        }
        double maxDiff = 0;
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            final double diff = Math.abs(a.get(i) - b.get(i));
            System.out.print(diff);
            if (i < n - 1) {
                System.out.print(" ");
            }
            maxDiff = Math.max(maxDiff, diff);
        }
        System.out.println("]");
        return maxDiff < eps;
    }
}