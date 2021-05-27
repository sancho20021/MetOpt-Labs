package lab3.methods;

import lab3.models.FullMatrix;
import lab3.models.SparseMatrix;
import lab3.models.Vector;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConjugateTest {
    private final static double EPS = 1e-7;

    @Test
    public void testRandomMatrices() {
        final int samples = 100;
        final int n = 2;
        final Vector f = new Vector(IntStream.range(0, n).mapToDouble(x -> x + 1).toArray());
        final Stream<SparseMatrix> as =
                Stream.generate(() -> new SparseMatrix(MatrixGenerators.generateIntegerMatrix(n, -10, 10)))
                        .limit(samples);
        as.forEach(a -> checkEqual(a, f));

    }

    private static void checkEqual(final SparseMatrix a, final Vector f) {
        final Vector x0 = new Vector(new double[a.size()]);
        final Vector expected = new Vector(Gauss.solve(new FullMatrix(a), f.getElementsArrayCopy()).orElseThrow());
        final Vector got = new Conjugate(a, f, x0, EPS).solve().orElseThrow();
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