package utils.generators;

import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

import static utils.generators.MainGenerator.RANDOM;

public class VectorGenerators {
    public static double[] generateVector(final int n, final DoubleSupplier getX) {
        return DoubleStream.generate(getX).limit(n).toArray();
    }

    public static double[] generateIntegerVector(final int n, final int lo, final int hi) {
        return generateVector(n, () -> lo + RANDOM.nextInt(hi - lo));
    }
}