package lab3.utils.generators;

import lab3.models.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LSGenerators {
    // Это пока что хер знает зачем
    public static Stream<LinearSystem> generateSystems(final int n, final BiFunction<Integer, Integer, LinearSystem> getSystem) {
        return IntStream.iterate(0, k -> k + 1).mapToObj(k -> getSystem.apply(n, k));
    }

    public static LinearSystem getProfilFormatLS(final double[][] matrix) {
        return getLS(matrix, ProfileFormatMatrix::new);
    }

    public static LinearSystem getDenseFormatLS(final double[][] matrix) {
        return getLS(matrix, FullMatrix::new);
    }

    public static LinearSystem getLS(final double[][] matrix, Function<double[][], MutableSquareMatrix> matrixConstructor) {
        final var xStar = getXStar(matrix.length);
        return new LinearSystem(
                matrixConstructor.apply(matrix),
                new FullMatrix(matrix).multiply(xStar),
                xStar
        );
    }

    private static Vector getXStar(final int n) {
        return new Vector(IntStream.range(1, n + 1).mapToDouble(x -> x).toArray());
    }

}
