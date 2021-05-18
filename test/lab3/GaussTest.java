package lab3;

import lab3.models.FullMatrix;
import lab3.models.SquareMatrix;
import lab3.models.Vector;
import lab3.utils.generators.MatrixGenerators;
import lab3.utils.generators.VectorGenerators;
import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class GaussTest {
    @Test
    public void testSimpleGauss() {
        final int n = 10;
        final int samples = 10;
        final int lo = -10, hi = 10;
        final double eps = 0.0000001;
        final Stream<SLAU> tasks = Stream.generate(SLAU.getSLAU(
                () -> MatrixGenerators.generateIntegerMatrix(n, lo, hi),
                () -> VectorGenerators.generateIntegerVector(n, lo, hi)
        ));
        tasks.limit(samples).forEach(slau -> {
            final var solution = Gauss.solve(slau.a, slau.b.getElementsArrayCopy()).get();
            final var diff = slau.diff(solution);
            System.out.println(diff);
        });
    }

    public static class SLAU {
        final SquareMatrix a;
        final Vector b;

        public SLAU(final SquareMatrix a, final Vector b) {
            this.a = a;
            this.b = b;
        }

        public SLAU(final double[][] a, final double[] b) {
            this(new FullMatrix(a), new Vector(b));
        }

        public static Supplier<SLAU> getSLAU(final Supplier<double[][]> getA, final Supplier<double[]> getB) {
            return () -> new SLAU(getA.get(), getB.get());
        }

        public Vector diff(final double[] x) {
            return a.multiply(new Vector(x)).subtract(b);
        }
    }

}