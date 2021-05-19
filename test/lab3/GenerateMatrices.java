package lab3;

import lab3.models.*;
import lab3.utils.generators.MatrixGenerators;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenerateMatrices {
    @Test
    public void testOrdinaryMatrix() {
        testGenerator(MatrixGenerators::generateMatrix);
    }

    @Test
    public void testHilbertMatrix() {
        testGenerator(MatrixGenerators::generateHilbertMatrix);
    }

    @Test
    public void testDiagonalDominanceMatrix() {
        testGenerator(MatrixGenerators::generateDiagonalDominanceMatrix);
    }

    private void testGenerator(final Function<Integer, double[][]> matrixSupplier) {
        for (int n = 1; n < 10; n++) {
            final int finalN = n;
            final Stream<double[][]> matrixStream = Stream.generate(() -> matrixSupplier.apply(finalN)).limit(1);
            matrixStream.map(matrix -> new LinearSystem(matrix, xStar(finalN)))
                    .forEach(ls -> System.out.println(ls.toRawString()));
        }
    }

    private double[] xStar(final int n) {
        return IntStream.range(1, n + 1).mapToDouble(x -> x).toArray();
    }
}
