package lab3.methods;

import lab3.methods.Gauss;
import lab3.models.LinearSystem;
import lab3.models.Vector;
import lab3.utils.generators.MatrixGenerators;
import lab3.utils.generators.VectorGenerators;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

public class GaussTest {
    public static void testSLAUSolver(final int n, final int samples, final int lo, final int hi, final Function<LinearSystem, double[]> solver) {
        final Stream<LinearSystem> tasks = Stream.generate(LinearSystem.newLSGenerator(
                () -> MatrixGenerators.generateIntegerMatrix(n, lo, hi),
                () -> VectorGenerators.generateIntegerVector(n, lo, hi)
        ));
        tasks.limit(samples).forEach(slau -> {
            final var solution = solver.apply(slau);
            final var diff = slau.getResidual(new Vector(solution));
            System.out.println("diff with zero: " + diff);
        });
    }

    @Test
    public void testOptimizedGauss() {
        final double eps = 0.0000001;
        testSLAUSolver(10, 10, -10, 10, slau -> Gauss.solveOptimized(slau.getA(), slau.getB().getElementsArrayCopy(), eps).get());
    }

    @Test
    public void testSimpleGauss() {
        testSLAUSolver(10, 10, -10, 10, slau -> Gauss.solve(slau.getA(), slau.getB().getElementsArrayCopy()).get());
    }

}