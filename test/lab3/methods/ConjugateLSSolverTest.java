package lab3.methods;

import lab3.models.AdvancedMatrix;
import lab3.models.FullMatrix;
import lab3.models.SparseMatrix;
import lab3.models.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ConjugateLSSolverTest {
    private final static double EPS = 1e-7;

    @Test
    public void testWeird() {
        final SparseMatrix.DisSymMatrix m = new SparseMatrix.DisSymMatrix(
                7,
                List.of(
                        new double[]{-1},
                        new double[]{-3, -1},
                        new double[]{-1, -2, -3},
                        new double[]{6, 3, 3, 0, 1, 5, 5}
                )
        );
        final SparseMatrix sparseMatrix = new SparseMatrix(m);
        final Vector expected = new Vector(1, 2, 3, 4, 5, 6, 7);
        final Vector x0 = new Vector(0, 0, 0, 0, 0, 0, 0);
        final ConjugateLSSolver solver = new ConjugateLSSolver(sparseMatrix, expected, x0, EPS);
        final Vector ans = solver.solve().orElseThrow();
        System.out.println(ans);
        final AdvancedMatrix checkMatrix = new FullMatrix(sparseMatrix);
        final ConjugateLSSolver checkSolver = new ConjugateLSSolver(checkMatrix, expected, x0, EPS);
        final Vector checkAns = checkSolver.solve().orElseThrow();
        System.out.println(checkAns);
    }

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