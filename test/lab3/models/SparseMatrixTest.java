package lab3.models;

import lab3.utils.generators.MatrixGenerators;
import lab3.utils.generators.VectorGenerators;
import org.junit.Assert;
import org.junit.Test;

public class SparseMatrixTest {
    @Test
    public void testGet() {
        final int samples = 100;
        final int maxN = 10;
        for (int n = 1; n < maxN; n++) {
            double averageMemUse = 0;
            for (int i = 0; i < samples; i++) {
                final FullMatrix original = new FullMatrix(MatrixGenerators.generateSparseMatrix(n, -100, 100, 0.001));
                final SparseMatrix sparse = new SparseMatrix(original);
                Assert.assertTrue(checkEqual(original, sparse));
                averageMemUse += (sparse.al.length * 2 + n);
            }
            System.out.println(averageMemUse / samples + "/" + n * n);
        }
    }

    @Test
    public void testMultiply() {
        final int samples = 100;
        final int maxN = 10;
        for (int n = 1; n < maxN; n++) {
            for (int i = 0; i < samples; i++) {
                final FullMatrix original = new FullMatrix(MatrixGenerators.generateSparseMatrix(n, -100, 100, 0.5));
                final SparseMatrix sparseMatrix = new SparseMatrix(original);
                final Vector v = new Vector(VectorGenerators.generateIntegerVector(n, -100, 100));
                final Vector expected = original.multiply(v);
                final Vector got = AdvancedMatrix.multiply(sparseMatrix, v);
                Assert.assertTrue(checkEqual(expected, got));
            }
        }
    }

    private boolean checkEqual(final SimpleSquareMatrix m1, final SimpleSquareMatrix m2) {
        final int n = m1.size();
        if (n != m2.size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (m1.get(i, j) != m2.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkEqual(final Vector v1, final Vector v2) {
        final int n = v1.size();
        if (n != v2.size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (v1.get(i) != v2.get(i)) {
                return false;
            }
        }
        return true;
    }
}